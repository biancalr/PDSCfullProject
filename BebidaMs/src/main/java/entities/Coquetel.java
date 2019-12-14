package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import enums.UnidadeGarrafa;

@Entity
@DiscriminatorValue(value = "coquetel")
@Access(AccessType.FIELD)
public class Coquetel extends Bebida implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Informações extras sobre o modo de preparo e/ou os ingredientes
	 */
	@ElementCollection
	@CollectionTable(name = "Comentario", joinColumns = @JoinColumn(name = "id_usuario", nullable = true))
	protected List<String> comentario;

	/**
	 * Depende de quem criou o coquetel. Se o criador é um administrador a variável
	 * recebe true, caso contrário, false.
	 */
	@NotNull
	protected Boolean criacaoInterna;

	/**
	 * Id do usuário criador
	 */
	protected Long criador;

	public Coquetel() {
		super();
	}

	public Coquetel(long id, String nome, @DecimalMin("0.0") double preco, UnidadeGarrafa unidadeGarrafa,
			String descricao, List<String> comentario, @NotNull Boolean criacaoInterna, Long criador) {
		super(id, nome, preco, unidadeGarrafa, descricao);
		this.comentario = comentario;
		this.criacaoInterna = criacaoInterna;
		this.criador = criador;
	}

	public List<String> getComentario() {
		return comentario;
	}

	public void setComentario(List<String> comentario) {
		this.comentario = comentario;
	}

	public Boolean getCriacaoInterna() {
		return criacaoInterna;
	}

	public void setCriacaoInterna(Boolean criacaoInterna) {
		this.criacaoInterna = criacaoInterna;
	}

	public Long getCriador() {
		return criador;
	}

	public void setCriador(Long criador) {
		this.criador = criador;
	}

	public void addComentario(String comentario) {
		if (this.comentario == null) {
			this.comentario = new ArrayList<>();
		}
		this.comentario.add(comentario);
	}

	public boolean removeComentario(String comentario) {
		return this.comentario.remove(comentario);
	}

}
