package jsonEntities;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

@JsonbPropertyOrder({ "id", "login", "password", "name", "email", "cpf", "phoneNumber" })
public class UserJson implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonbProperty("id")
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	@Email(message = "UserJson: email not valid")
	protected String email;

//	@NotNull
//	@JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
//	protected Date birthDay;

	@NotBlank
	@Size(min = 4, message = "UserJson: Login minimun size: 4")
	protected String login;

	@NotBlank
	@CPF(message = "UserJson: cpf not valid")
//	@JsonbTransient // Essa annotation impede que o attributo seja inserido na resposta
	protected String cpf;

	@NotBlank
//	@JsonbTransient
	protected String password;

	@NotBlank
	@Size(min = 9, message = "UserJson: phoneNumber minimun size: 9")
	protected String phoneNumber;

//	@NotBlank
	protected String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public Date getBirthDay() {
//		return birthDay;
//	}
//
//	public void setBirthDate(Date birthDay) {
//		this.birthDay = birthDay;
//	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	public Date getBirthDay() {
//		return birthDay;
//	}
//
//	public void setBirthDay(Date birthDay) {
//		this.birthDay = birthDay;
//	}

//	public void formattedStringDate(String date) throws ParseException {
//		SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yyyy");
//		birthDay = formatted.parse(date);
//	}
//
//	public String formattedDateString() {
//		SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yyyy");
//		return formatted.format(birthDay);
//	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		String representacao = this.name + "\r" + this.email + "\r" /*+ this.birthDay + "\r"*/ + this.login + "\r"
				+ this.cpf + "\r" + this.password + "\r" + this.phoneNumber;
		return representacao;
	}

}
