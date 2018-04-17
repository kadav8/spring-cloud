package com.example.chat;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
public class ChatApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
}

@Controller
class ChatController {
	private final static Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chatmessage")
    @SendTo("/topic/messages")
    public ChatMessage messages(ChatMessage message) throws Exception {
    	log.info("Incoming message: {}", message);
        return new ChatMessage(message.getUsername(), message.getText(), new Date());
    }
}

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chatservice").withSockJS();
    }
}

class ChatMessage {
	private String username;
	private String text;
	private Date date;

	public ChatMessage() {}
	public ChatMessage(String username, String text, Date date) {
		this.username = username;
		this.text = text;
		this.date = date;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ChatMessage [username=" + username + ", text=" + text + ", date=" + date + "]";
	}
}