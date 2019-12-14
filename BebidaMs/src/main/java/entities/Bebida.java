package entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

import enums.UnidadeGarrafa;

@Entity
@Table
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Bebida implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    
	@Column
    protected String nome;
    
    @Column
    protected double preco;
    
    @Enumerated(EnumType.ORDINAL)
    protected UnidadeGarrafa unidadeGarrafa;
    
    @Column
    protected String descricao;

	public Bebida() {
		super();
	}

	public Bebida(long id, String nome, @DecimalMin("0.0") double preco, UnidadeGarrafa unidadeGarrafa,
			String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.unidadeGarrafa = unidadeGarrafa;
		this.descricao = descricao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public UnidadeGarrafa getUnidadeGarrafa() {
		return unidadeGarrafa;
	}

	public void setUnidadeGarrafa(UnidadeGarrafa unidadeGarrafa) {
		this.unidadeGarrafa = unidadeGarrafa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
