package enums;

public enum UnidadeGarrafa {

	v300mL(1), v350mL(2), v600mL(3), v1L(4), v1MEIOL(5), v2L(6);

	private int volume;

	private UnidadeGarrafa() {
	}

	private UnidadeGarrafa(int volume) {
		this.volume = volume;
	}

	public int getVolume() {
		return volume;
	}

	@Override
	public String toString() {
		String sb = "Volume da garrafa: " + volume;
		return sb;
	}

}
