package be.tribersoft.triber.chat.configuration;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app") // forwarded to
															// controller

				.enableSimpleBroker("/topic", "/queue");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/questions").setHandshakeHandler(new MyHandshakeHandler()).withSockJS();
	}

	private class MyHandshakeHandler extends DefaultHandshakeHandler {

		@Override
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler websocketHAndler, Map<String, Object> attributes) {
			String userName = "user" + ThreadLocalRandom.current().nextInt(100);
			return new UsernamePasswordAuthenticationToken(userName, null);
		}
	}

}