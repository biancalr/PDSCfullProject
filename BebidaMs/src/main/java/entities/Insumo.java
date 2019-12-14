package entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import enums.TipoInsumo;

@Entity
@Table
@Access(AccessType.FIELD)
public class Insumo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.ORDINAL)
    private TipoInsumo tipoInsumo;
    
    @NotBlank
    private String nomeInsumo;
    
    private String descricao;
    
    @NotNull
    @DecimalMin(value = "0.0")
    private double preco;

	public Insumo() {
		super();
	}

	public Insumo(Long id, TipoInsumo tipoInsumo, @NotBlank String nomeInsumo, String descricao,
			@NotNull @DecimalMin("0.0") double preco) {
		super();
		this.id = id;
		this.tipoInsumo = tipoInsumo;
		this.nomeInsumo = nomeInsumo;
		this.descricao = descricao;
		this.preco = preco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoInsumo getTipoInsumo() {
		return tipoInsumo;
	}

	public void setTipoInsumo(TipoInsumo tipoInsumo) {
		this.tipoInsumo = tipoInsumo;
	}

	public String getNomeInsumo() {
		return nomeInsumo;
	}

	public void setNomeInsumo(String nomeInsumo) {
		this.nomeInsumo = nomeInsumo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
    
    

}
