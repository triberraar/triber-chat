package be.tribersoft.triber.chat.rest.security;

public class JWTJson {

	private String jwtToken;

	public JWTJson(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}
}
