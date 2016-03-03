package be.tribersoft.triber.chat.security;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTSigner.Options;
import com.auth0.jwt.JWTVerifier;

import be.tribersoft.triber.chat.domain.user.api.User;

@Named
public class TokenHandler {

	@Inject
	private UserDetailsService userDetailsService;

	@Value("${security.jwt.expiration}")
	private Integer expiration;
	@Value("${security.jwt.secret}")
	private String secret;

	public SecurityUser fromToken(String token) {
		try {
			String username = (String) new JWTVerifier(secret).verify(token).get("username");
			return userDetailsService.loadUserByUsername(username);
		} catch (Throwable t) {
			throw new InsufficientAuthenticationException("decoding of jwt failed");
		}
	}

	public String toToken(User user) {
		Options options = new Options().setAlgorithm(Algorithm.HS512).setExpirySeconds(expiration * 60 * 60);
		return new JWTSigner(secret).sign(buildClaims(user), options);
	}

	private Map<String, Object> buildClaims(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", user.getUsername());
		claims.put("roles", user.getRoles());
		return claims;
	}

}
