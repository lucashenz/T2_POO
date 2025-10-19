package aplicacao;

import dados.CatalogoDoacoes;
import dados.CatalogoDoadores;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;

public class ACMEDonations {

    private final String nomeArquivoSaida = "recursos/relatorio.txt";
    private CatalogoDoadores catalogoDoadores = new CatalogoDoadores();
    private CatalogoDoacoes catalogoDoacoes = new CatalogoDoacoes(catalogoDoadores);

    public void executar(){
        redirecionaSaida();


        //metodos
        catalogoDoadores.cadastrarDoadores();
        catalogoDoacoes.cadastrarDoacoesPereciveis();
        catalogoDoacoes.cadastrarDoacoesDuraveis();
    }


    private void redirecionaSaida() {
        try {
            PrintStream streamSaida = new PrintStream(new File(nomeArquivoSaida), Charset.forName("UTF-8"));
            System.setOut(streamSaida);
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);
    }

}
