package dados;

public enum TipoPerecivel {

	ALIMENTO("ALIMENTO"),

	MEDICAMENTO("MEDICAMENTO");

	private String nome;

	TipoPerecivel(String medicamento) {
	}

	public String getNome() {
		return nome;
	}
}
