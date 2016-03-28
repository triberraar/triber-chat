package be.tribersoft.triber.chat;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import be.tribersoft.triber.chat.common.jpa.AESCipher;
import be.tribersoft.triber.chat.common.jpa.CryptoSecret;

public class AESCipherTest {

	private static final String TEXT = "text";

	@Test
	public void decryptedTextShouldBeSameAsBeforeEncryption() throws Exception {
		CryptoSecret cryptoSecret = new CryptoSecret();
		Whitebox.setInternalState(cryptoSecret, "secret", "qscftyhnjugtrfde");
		Whitebox.setInternalState(cryptoSecret, "iv", "qscftyhnjugtrfde");
		cryptoSecret.init();

		assertThat(AESCipher.decrypt(AESCipher.encrypt(TEXT))).isEqualTo(TEXT);
	}

}
