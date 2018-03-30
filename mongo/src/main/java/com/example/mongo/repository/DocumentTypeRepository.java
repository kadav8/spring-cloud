package com.example.mongo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.mongo.domain.DocumentType;

public interface DocumentTypeRepository extends ReactiveCrudRepository<DocumentType, String> {

}
