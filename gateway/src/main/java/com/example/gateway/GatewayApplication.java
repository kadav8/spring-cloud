package com.example.gateway;

import static com.example.EnvironmentSetter.setEnvProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
	private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) {
		setEnvProperties();
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	@Profile("!eureka")
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, Environment env) {
		RouteLocatorBuilder.Builder b = builder.routes();
		if(StringUtils.hasText(env.getProperty("gateway.routes"))) {
			for(String ro : env.getProperty("gateway.routes").split(",")) {
				String url = env.getProperty("gateway.routes." + ro);
				log.info("set: " + url);
				if(StringUtils.hasText(url)) {
					b.route(r -> r
							.path("/" + ro + "/**")
							.filters(f -> f.rewritePath("/" + ro + "/(?<remaining>.*)", "/${remaining}"))
							.uri((url.endsWith("/")) ? url.substring(0, url.length()-1) : url));
				}
			}
		}
		return b.build();
	}
}

@RestController
class GatewayController {
	@Value("${notifications.enabled:true}")
	private boolean isNotificationsEnabled;
	@Value("${gateway.routes.notifications:null}")
	private String url;
	private WebClient wc = null;

	@GetMapping("/notificationsEnabled")
	public Mono<Boolean> isEnabled() {
		if(isNotificationsEnabled && StringUtils.hasText(url)) {
			wc = (wc == null) ? WebClient.create(url) : wc;
			return wc.get().uri("/ping").exchange().flatMap(response -> Mono.just(true)).onErrorReturn(false);
		}
		return Mono.just(false);
	}
}

@Component
class CustomWebFilter implements WebFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    if (exchange.getRequest().getURI().getPath().equals("/") || exchange.getRequest().getURI().getPath().equals("/index")) {
        return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
    }
    return chain.filter(exchange);
  }
}
