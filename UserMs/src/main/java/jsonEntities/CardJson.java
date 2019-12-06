package jsonEntities;

import java.io.Serializable;

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
	private Long dataExpiracao;

	@CreditCardNumber
	@NotBlank
	private String numero;
	
	@NotBlank
	private String senha;
	
	public CardJson() {
		// TODO Auto-generated constructor stub
	}

	public CardJson(long id, @NotBlank Long user, @NotBlank String bandeira, @NotNull Long dataExpiracao,
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

	public Long getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Long dataExpiracao) {
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
	
}
