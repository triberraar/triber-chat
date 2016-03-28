package be.tribersoft.triber.chat.common.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class CryptoSecretTest {

	private static final String SECRET = "qscftyhnjugtrfde";
	private static final String IV = "!qscftyhnjugtrfd";

	@Test(expected = RuntimeException.class)
	public void failsWhenIvIsShorterThen15() {
		CryptoSecret cryptoSecret = new CryptoSecret();
		Whitebox.setInternalState(cryptoSecret, "secret", SECRET);
		Whitebox.setInternalState(cryptoSecret, "iv", "asdfghjklpoiuny");

		cryptoSecret.init();
	}

	@Test(expected = RuntimeException.class)
	public void failsWhenIvIsLongerThen16() {
		CryptoSecret cryptoSecret = new CryptoSecret();
		Whitebox.setInternalState(cryptoSecret, "secret", SECRET);
		Whitebox.setInternalState(cryptoSecret, "iv", "asdfghjklpoiuny12");

		cryptoSecret.init();
	}

	@Test(expected = RuntimeException.class)
	public void failsWhenSecretIsShorterThen15() {
		CryptoSecret cryptoSecret = new CryptoSecret();
		Whitebox.setInternalState(cryptoSecret, "secret", "asdfghjklpoiuny");
		Whitebox.setInternalState(cryptoSecret, "iv", SECRET);

		cryptoSecret.init();
	}

	@Test(expected = RuntimeException.class)
	public void failsWhenSecretIsLongerThen16() {
		CryptoSecret cryptoSecret = new CryptoSecret();
		Whitebox.setInternalState(cryptoSecret, "secret", "asdfghjklpoiuny12");
		Whitebox.setInternalState(cryptoSecret, "iv", SECRET);

		cryptoSecret.init();
	}

	@Test
	public void setsIVAndSecret() {
		CryptoSecret cryptoSecret = new CryptoSecret();
		Whitebox.setInternalState(cryptoSecret, "secret", SECRET);
		Whitebox.setInternalState(cryptoSecret, "iv", IV);

		cryptoSecret.init();

		assertThat(CryptoSecret.getIv()).isEqualTo(IV);
		assertThat(CryptoSecret.getSecret()).isEqualTo(SECRET);
	}

}
