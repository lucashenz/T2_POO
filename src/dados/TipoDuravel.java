package dados;

public enum TipoDuravel {

	ROUPA("ROUPA"),
	BRINQUEDO("BRINQUEDO"),
	ELETRODOMESTICO("ELETRODOMESTICO"),
	MOVEL("MOVEL");

	private String nome;

	TipoDuravel(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
