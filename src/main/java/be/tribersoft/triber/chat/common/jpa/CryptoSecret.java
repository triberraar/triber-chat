package be.tribersoft.triber.chat.common.jpa;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;

@Named
public class CryptoSecret {

	private static String SECRET;
	private static String IV;

	@Value("${crypto.secret}")
	private String secret;
	@Value("${crypto.iv}")
	private String iv;

	@PostConstruct
	public void init() {
		SECRET = secret;
		IV = iv;
		if (SECRET.length() != 16 || IV.length() != 16) {
			throw new RuntimeException("Wrong setup in crypto, both secret and iv should be 16 chars long");
		}
	}

	public static String getSecret() {
		return SECRET;
	}

	public static String getIv() {
		return IV;
	}
}
