package be.tribersoft.triber.chat.common.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String rawData) {
		return AESCipher.encrypt(rawData);

	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return AESCipher.decrypt(dbData);
	}

}
