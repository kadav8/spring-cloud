package com.example.mongo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.mongo.domain.Document;

import reactor.core.publisher.Flux;

public interface DocumentRepository extends ReactiveCrudRepository<Document, String> {

	public Flux<Document> findByVersionSeriesIdOrderByCreationDate(String versionSeriesId);
}
