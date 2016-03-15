package be.tribersoft.triber.chat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		// @formatter:off
		messages
			.simpSubscribeDestMatchers("/topic/notifications/**").hasAnyRole("ADMIN")
			.anyMessage().authenticated();
		// @formatter:on
	}

	@Override
	protected boolean sameOriginDisabled() {
		// disable CSRF for websockets for now...
		return true;
	}
}
