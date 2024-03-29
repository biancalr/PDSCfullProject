package entities;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Table(name = "card", catalog = "user_ms")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = Card.ALL_CARDS, query = "Select c from Card c"),
		@NamedQuery(name = Card.CARD_BY_USER, query = "Select c from Card c where c.user.id=?1") })
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ALL_CARDS = "AllCards";
	public static final String CARD_BY_USER = "CardByUser";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(name = "bandeira", nullable = false)
	private String bandeira;

	@NotBlank
	@Column(name = "dataExpiracao", nullable = false)
	private String dataExpiracao;

	@NotBlank
	@CreditCardNumber
	@Column(name = "numero", nullable = false)
	private String numero;

	@NotBlank
	@Column(name = "senha", nullable = false)
	private String senha;

	@NotBlank
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = false)
	@JoinColumn(name = "id_user", referencedColumnName = "ID", nullable = false)
	private User user;


	public Card() {
		// TODO Auto-generated constructor stub
	}

	public Card(long id, @NotBlank String bandeira, @NotBlank String dataExpiracao,
			@NotBlank @CreditCardNumber String numero, @NotBlank String senha, @NotBlank User user) {
		this.id = id;
		this.bandeira = bandeira;
		this.dataExpiracao = dataExpiracao;
		this.numero = numero;
		this.senha = senha;
		this.user = user;
	}

	public Card(@NotBlank String bandeira, @NotBlank String dataExpiracao, @NotBlank @CreditCardNumber String numero,
			@NotBlank String senha, @NotBlank User user) {
		super();
		this.bandeira = bandeira;
		this.dataExpiracao = dataExpiracao;
		this.numero = numero;
		this.senha = senha;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
		return hash;
	}

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
		final Card other = (Card) obj;
		return this.id == other.id;
	}

}
