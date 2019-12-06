package jsonEntities;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import entities.User;

@JsonbPropertyOrder({ "id", "cep", "state", "city", "district", "publicPlace", "complement", "number", "user"})
public class AddressJson implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonbProperty("id")
	private long id;
	
	@NotBlank
    private String cep;
	
	@NotBlank
	private String state;
	
	@NotBlank
	private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String publicPlace;
    
    private String complement;
    
    @NotNull
    @Min(value = 1)
    @Max(value = 9999)
    private Integer number;
    
    @NotNull
    private User user;

    public AddressJson() {
		// TODO Auto-generated constructor stub
	}
    
	public AddressJson(long id, @NotBlank String cep, @NotBlank String state, @NotBlank String city,
			@NotBlank String district, @NotBlank String publicPlace, String complement,
			@NotNull @Min(1) @Max(9999) Integer number, @NotNull User user) {
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
