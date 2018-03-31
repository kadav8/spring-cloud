package com.example.mongo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.mongo.domain.Document;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentRepository extends ReactiveCrudRepository<Document, String> {

	public Flux<Document> findByVersionSeriesIdOrderByCreationDate(String versionSeriesId);

	public Mono<Void> deleteByVersionSeriesId(String versionSeriesId);

}
