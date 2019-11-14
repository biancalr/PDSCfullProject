package entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "user", catalog = "user_ms")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(
			name = User.USER_BY_CPF,
			query = "SELECT u FROM User u WHERE u.cpf = ?1"),
	@NamedQuery(
			name = User.ALL_USERS,
			query = "SELECT u FROM User u"),
	@NamedQuery(
			name = User.USER_BY_LOGIN,
			query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password"),
	@NamedQuery(
			name = User.USER_BY_TOKEN,
			query = "Select u From User u Where u.token = :token")
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String USER_BY_CPF = "ClienteByCPF";
	public static final String ALL_USERS = "AllUsers";
	public static final String USER_BY_LOGIN = "UserByLogin";
	public static final String USER_BY_TOKEN = "UserByToken";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Column(name = "name", nullable = false)
	protected String name;

	@Column(name = "password", nullable = false)
	protected String password;

	@Column(name = "cpf", nullable = false)
	protected String cpf;

	@Column(name = "email", nullable = false)
	protected String email;

	@Column(name = "login", nullable = false)
	protected String login;

	@Column(name = "phoneNumber", nullable = false)
	protected String phoneNumber;
	
//	@NotNull
//	@Column
//	@Past
//	@Temporal(TemporalType.DATE)
//	protected Date birthDay;
	
	@Column(name = "token", nullable = true)
	protected String token;

	public User(long id, String name, String password, String cpf, String email, String login, String phoneNumber,
			String token) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.cpf = cpf;
		this.email = email;
		this.login = login;
		this.phoneNumber = phoneNumber;
		this.token = token;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
    
//    public Date getBirthDay() {
//		return birthDay;
//	}
//
//	public void setBirthDay(Date birthDate) {
//		this.birthDay = birthDate;
//	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

//	public void formattedStringDate(String date) throws ParseException {
//    	SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yyyy"); 
//    	birthDay = formatted.parse(date);
//    }
//    
//    public String formattedDateString() {
//    	SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yyyy");
//    	return formatted.format(birthDay);
//    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		return this.id == other.id;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		String representacao = this.name + "\r" + this.email + "\r" /*+ this.birthDay + "\r"*/ + this.login + "\r"
				+ this.cpf + "\r" + this.password + "\r" + this.phoneNumber;
		return representacao;
	}

}
