package com.example.mongo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DocumentRepository extends ReactiveCrudRepository<Document, String> {

}
