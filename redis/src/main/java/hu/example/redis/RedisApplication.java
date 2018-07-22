package hu.example.redis;

import static com.example.EnvironmentSetter.setEnvProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
@ComponentScan("com.example")
public class RedisApplication {

	public static void main(String[] args) {
		setEnvProperties();
		SpringApplication.run(RedisApplication.class, args);
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	@Bean
	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	@Bean
	public ReactiveRedisConnection reactiveRedisConnection(
			final ReactiveRedisConnectionFactory redisConnectionFactory) {
		return redisConnectionFactory.getReactiveConnection();
	}

	@Bean
    public ReactiveRedisTemplate<String, Document> documentRedisTemplate(
        ReactiveRedisConnectionFactory redisConnectionFactory) {
        RedisSerializationContext<String, Document> serializationContext = RedisSerializationContext
            .<String, Document>newSerializationContext(new StringRedisSerializer())
            .hashKey(new StringRedisSerializer())
            .hashValue(new Jackson2JsonRedisSerializer<>(Document.class))
            .build();
        return new ReactiveRedisTemplate<>(redisConnectionFactory, serializationContext);
    }
}
