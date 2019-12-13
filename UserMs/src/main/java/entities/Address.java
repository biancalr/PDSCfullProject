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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "address", catalog = "user_ms")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = Address.ALL_ADDRESSES, query = "SELECT a FROM Address a"),
		@NamedQuery(name = Address.ADDRESS_BY_CEP, query = "SELECT a FROM Address a WHERE a.cep = :cep"),
		@NamedQuery(name = Address.USER_ADDRESS, query = "SELECT a FROM Address a WHERE a.user.id = :userId") })
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String ADDRESS_BY_CEP = "AddressByCep";
	public static final String ALL_ADDRESSES = "AllAddresses";
	public static final String USER_ADDRESS = "UserAddress";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column
	private String cep;

	@NotBlank
	@Column
	private String state;

	@NotBlank
	@Column
	private String city;

	@NotBlank
	@Column
	private String district;

	@NotBlank
	@Column
	private String publicPlace;

	@Column(nullable = true)
	private String complement;

	@NotNull
	@Column
	private Integer number;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = false)
	@JoinColumn(name = "id_user", referencedColumnName = "ID", nullable = false)
	private User user;

	public Address() {

	}

	public Address(long id, String cep,@Valid String state, String city, String district, String publicPlace,
			String complement, Integer number, User user) {
		super();
		this.id = id;
		this.cep = cep;
		this.state = state;
		this.city = city;
		this.district = district;
		this.publicPlace = publicPlace;
		this.complement = complement;
		this.number = number;
		this.user = user;
	}

	public Address(@NotBlank String cep, @NotBlank String state, @NotBlank String city, @NotBlank String district,
			@NotBlank String publicPlace, String complement, @NotNull Integer number, User user) {
		super();
		this.cep = cep;
		this.state = state;
		this.city = city;
		this.district = district;
		this.publicPlace = publicPlace;
		this.complement = complement;
		this.number = number;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPublicPlace() {
		return publicPlace;
	}

	public void setPublicPlace(String publicPlace) {
		this.publicPlace = publicPlace;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
