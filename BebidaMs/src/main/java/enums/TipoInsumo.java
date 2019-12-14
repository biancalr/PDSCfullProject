package enums;

public enum TipoInsumo {
	FRUTA(1), SUCO(2), BEBIDA(3), EXTRA(4);

	private int tipoItemInsumo;

	private TipoInsumo() {
	}

	TipoInsumo(int tipoItemInsumo) {
		this.tipoItemInsumo = tipoItemInsumo;
	}

	public int getTipoItemInsumo() {
		return tipoItemInsumo;
	}

	public void setTipoItemInsumo(int tipoItemInsumo) {
		this.tipoItemInsumo = tipoItemInsumo;
	}

	@Override
	public String toString() {
		String sb = "Tipo do Item: " + tipoItemInsumo;
		return sb;
	}

}
