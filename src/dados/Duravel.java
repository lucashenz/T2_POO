package dados;

public class Duravel extends Doacao {

    private TipoDuravel tipoDuravel;

    public Duravel(String descricao, double valor, int quantidade, Doador doador, TipoDuravel tipo) {
        super(descricao, valor, quantidade, doador);
        this.tipoDuravel = tipo;
    }

    public TipoDuravel getTipo() {
        return null;
    }

    public String geraResumo() {
        return null;
    }

}