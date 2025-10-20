package dados;

public enum TipoPerecivel {

	ALIMENTO("ALIMENTO"),
	MEDICAMENTO("MEDICAMENTO");

	private String nome;

	TipoPerecivel(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
