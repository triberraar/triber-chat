package be.tribersoft.triber.chat.common.jpa;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;

@Named
public class CryptoSecret {

	public static String SECRET;

	@Value("${crypto.secret}")
	private String cryptoSecret;

	@PostConstruct
	public void init() {
		SECRET = cryptoSecret;
	}
}
