package be.tribersoft.triber.chat.common.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class CryptoConverterTest {

	private static final String TEXT = "text";

	private CryptoConverter cryptoConverter = new CryptoConverter();

	@Test
	public void decryptedTextShouldBeSameAsBeforeEncryption() throws Exception {
		CryptoSecret cryptoSecret = new CryptoSecret();
		Whitebox.setInternalState(cryptoSecret, "secret", "qscftyhnjugtrfde");
		Whitebox.setInternalState(cryptoSecret, "iv", "qscftyhnjugtrfde");
		cryptoSecret.init();

		assertThat(cryptoConverter.convertToEntityAttribute(cryptoConverter.convertToDatabaseColumn(TEXT))).isEqualTo(TEXT);
	}

}
