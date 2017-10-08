package com.example.admin.web

import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*

@Service
class WebClientTemplate (private val discoveryClient: DiscoveryClient) {

    private val webClient: WebClient = WebClient.create()

    fun<T> get(serviceName: String, requestUri: String, responseType: Class<T>): Mono<T> {
        val uri = getRandomUri(serviceName, requestUri)
        return if (uri == null) Mono.empty()
        else webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
    }

    private fun getRandomUri(serviceName: String, requestUri: String): String? {
        val instances = discoveryClient.getInstances(serviceName)
        if(instances == null || instances.size == 0) return null
        val i = Random().nextInt(instances.size)
        return instances[i].uri.toString() + "/" + requestUri
    }
}