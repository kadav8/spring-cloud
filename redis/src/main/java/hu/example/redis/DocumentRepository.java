package hu.example.redis;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DocumentRepository {
	private final static String mapkey = "documents";

	private ReactiveRedisOperations<String, Document> template;

    public DocumentRepository(ReactiveRedisOperations<String, Document> template) {
        this.template = template;
    }

	public Flux<Document> findAll() {
        return template.<String, Document>opsForHash().values(mapkey);
    }

	public Mono<Document> findById(String id) {
        return template.<String, Document>opsForHash().get(mapkey, id);
    }

	public Mono<Document> save(Document document) {
        return template.<String, Document>opsForHash()
        		.put(mapkey, document.getDocumentId(), document)
        		.map(p -> document);
    }

	public Mono<Long> deleteById(String id) {
        return template.<String, Document>opsForHash().remove(mapkey, id);
    }

	public Mono<Boolean> deleteAll() {
        return template.<String, Document>opsForHash().delete(mapkey);
    }
}
