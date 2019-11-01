package jsonEntities;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

@JsonbPropertyOrder({ "id", "login", "password", "name", "email", "cpf", "phoneNumber", "token" })
public class UserJson implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonbProperty("id")
	private long id;
	@NotBlank
	@Size(min = 4, message = "UserJson: Login minimun size: 4")
	private String login;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	@Email(message = "UserJson: email not valid")
	private String email;
	@NotBlank
	@CPF(message = "UserJson: cpf not valid")
	private String cpf;
	@NotBlank
	@Size(min = 9, message = "UserJson: phoneNumber minimun size: 9")
	private String phoneNumber;
	private String token;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
