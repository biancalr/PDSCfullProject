package jsonEntities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;

import validators.ValidaBandeira;
@JsonbPropertyOrder({ "id", "numero", "bandeira", "dataExpiracao", "user"})
public class CardJson implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonbProperty("id")
	private long id;

	@NotBlank
	private Long user;
	
	@NotBlank
	@ValidaBandeira
	private String bandeira;

	@NotNull
	private String dataExpiracao;

	@CreditCardNumber
	@NotBlank
	private String numero;
	
	@NotBlank
	private String senha;
	
	public CardJson() {
		// TODO Auto-generated constructor stub
	}

	public CardJson(long id, @NotBlank Long user, @NotBlank String bandeira, @NotNull Date dataExpiracao,
			@CreditCardNumber @NotBlank String numero, @NotBlank String senha) {
		super();
		this.id = id;
		this.user = user;
		this.bandeira = bandeira;
		this.dataExpiracao = fromDateToString(dataExpiracao);
		this.numero = numero;
		this.senha = senha;
	}

	public CardJson(long id, @NotBlank Long user, @NotBlank String bandeira, @NotNull String dataExpiracao,
			@CreditCardNumber @NotBlank String numero, @NotBlank String senha) {
		super();
		this.id = id;
		this.user = user;
		this.bandeira = bandeira;
		this.dataExpiracao = dataExpiracao;
		this.numero = numero;
		this.senha = senha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public String getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(String dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String fromDateToString(Date date) {
		return date.toString();
	}
	
	public Date fromStringToDate(String date) throws ParseException {
		return new SimpleDateFormat().parse(date);
	}
	
}
