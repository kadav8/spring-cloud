package com.example.gateway;

import static com.example.EnvironmentSetter.setEnvProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		setEnvProperties();
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
    public DiscoveryClientRouteDefinitionLocator
      discoveryClientRouteLocator(DiscoveryClient discoveryClient) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient);
    }
}

@RestController
class GatewayController {
	@Value("${notifications.enabled:false}")
	private boolean isNotificationsEnabled;

	@GetMapping("/notificationsEnabled")
	public boolean isEnabled() {
		return isNotificationsEnabled;
	}
}

// workaround:

@Component
class CustomWebFilter implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    if (exchange.getRequest().getURI().getPath().equals("/")) {
        return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
    }
    return chain.filter(exchange);
  }
}
