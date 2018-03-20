package com.example.admin.web;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class WebClientTemplate {

	@Autowired
	private DiscoveryClient discoveryClient;
	private WebClient webClient = WebClient.create();

	public <T> Mono<T> get(String serviceName, String requestUri, Class<T> responseType) {
		String uri = getRandomUri(serviceName, requestUri);
		return (uri == null) ? Mono.empty()
				: webClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(responseType);
	}

	private String getRandomUri(String serviceName, String requestUri) {
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
		int i = new Random().nextInt(instances.size());
		return (instances == null || instances.isEmpty()) ? null
				: instances.get(i).getUri().toString() + "/" + requestUri;
	}
}
