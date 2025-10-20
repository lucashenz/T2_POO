package dados;

public class Perecivel extends Doacao {

	private int validade;
	private TipoPerecivel tipoPerecivel;

	public Perecivel(String descricao, double valor, int quantidade, Doador doador, int validade, TipoPerecivel tipoPerecivel) {
		super(descricao, valor, quantidade, doador);
		this.validade = validade;
		this.tipoPerecivel = tipoPerecivel;
	}

	public TipoPerecivel getTipoPerecivel() {
		return tipoPerecivel;
	}

	public int getValidade() {
		return validade;
	}

	public void setValidade(int validade) {
		this.validade = validade;
	}

	public void setTipoPerecivel(TipoPerecivel tipoPerecivel) {
		this.tipoPerecivel = tipoPerecivel;
	}

	@Override
	public String geraResumo() {
		return getDescricao() + "," + getValor() + "," + getQuantidade() + "," +tipoPerecivel.getNome() + "," + validade;
	}
}
