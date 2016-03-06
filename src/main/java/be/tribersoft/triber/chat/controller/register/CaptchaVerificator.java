package be.tribersoft.triber.chat.controller.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Named
public class CaptchaVerificator {

	@Value("${captcha.secret}")
	private String secret;

	public void verify(String captcha) throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("secret", secret));
		params.add(new BasicNameValuePair("response", captcha));
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		CloseableHttpResponse response = client.execute(httpPost);
		CaptchaFromJsonAdapter result = new ObjectMapper().readValue(response.getEntity().getContent(), CaptchaFromJsonAdapter.class);
		if (!result.getSuccess()) {
			throw new CaptchaVerificationFailedException();
		}
	}
}
