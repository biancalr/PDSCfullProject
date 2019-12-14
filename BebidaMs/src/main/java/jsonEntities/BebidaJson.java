package jsonEntities;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.DecimalMin;

import enums.UnidadeGarrafa;
@JsonbPropertyOrder({ "id", "nome", "preco", "unidadeGarrafa", "descricao"})
public class BebidaJson implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonbProperty("id")
    protected long id;
    
    protected String nome;
    
    @DecimalMin(value = "0.0")
    protected double preco;
    
    protected UnidadeGarrafa unidadeGarrafa;
    
    protected String descricao;

	public BebidaJson(long id, String nome, @DecimalMin("0.0") double preco, UnidadeGarrafa unidadeGarrafa,
			String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.unidadeGarrafa = unidadeGarrafa;
		this.descricao = descricao;
	}

	public BebidaJson(String nome, @DecimalMin("0.0") double preco, UnidadeGarrafa unidadeGarrafa, String descricao) {
		super();
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
