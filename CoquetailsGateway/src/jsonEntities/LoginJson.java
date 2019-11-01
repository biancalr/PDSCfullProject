package jsonEntities;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"login", "password", "token", "persistToken"})
public class LoginJson implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	private String token;
	private boolean persistToken;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String senha) {
		this.password = senha;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isPersistToken() {
		return persistToken;
	}
	public void setPersistToken(boolean persistToken) {
		this.persistToken = persistToken;
	}

}
