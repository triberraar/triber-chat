package be.tribersoft.triber.chat.common.jpa;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

public class CryptoSecret {

	public static String SECRET;

	@Value("${crypto.secret}")
	private String cryptoSecret;

	@PostConstruct
	public void init() {
		SECRET = cryptoSecret;
	}
}
