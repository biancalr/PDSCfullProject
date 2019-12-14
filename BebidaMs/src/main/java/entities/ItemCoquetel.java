package entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table
@Access(AccessType.FIELD)
public class ItemCoquetel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Min(value = 1)
    private int quantidade;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "id_coquetel", referencedColumnName = "ID",
            nullable = false)
    private Coquetel coquetel;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = false, optional = false)
    @JoinColumn(name = "id_insumo", referencedColumnName = "ID", nullable = false)
    private Insumo insumo;

	public ItemCoquetel(long id, @Min(1) int quantidade, Coquetel coquetel, Insumo insumo) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.coquetel = coquetel;
		this.insumo = insumo;
	}

	public ItemCoquetel(@Min(1) int quantidade, Coquetel coquetel, Insumo insumo) {
		super();
		this.quantidade = quantidade;
		this.coquetel = coquetel;
		this.insumo = insumo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Coquetel getCoquetel() {
		return coquetel;
	}

	public void setCoquetel(Coquetel coquetel) {
		this.coquetel = coquetel;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}
    
    

}
