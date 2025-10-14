package dados;

public abstract class Doacao {

    private String descricao;

    private double valor;

    private int quantidade;

    private Doador doador;

    public Doador getDoador() {
        return null;
    }

    public abstract String geraResumo();

}
