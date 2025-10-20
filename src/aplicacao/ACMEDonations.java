package aplicacao;

import dados.CatalogoDoacoes;
import dados.CatalogoDoadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class ACMEDonations {

    private final String nomeArquivoSaida = "recursos/relatorio.txt";
    private CatalogoDoadores catalogoDoadores = new CatalogoDoadores();
    private CatalogoDoacoes catalogoDoacoes = new CatalogoDoacoes(catalogoDoadores);

    public void executar() {
        redirecionaSaida();


        //metodos
        catalogoDoadores.cadastrarDoadores();
        catalogoDoacoes.cadastrarDoacoesPereciveis();
        catalogoDoacoes.cadastrarDoacoesDuraveis();
        mostrarDadosDeUmDoador();
    }

    private void mostrarDadosDeUmDoador() {
        Path path = Paths.get("recursos/dadosentrada.txt");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha = bufferedReader.readLine();

            if (linha != null) {
                Scanner tec = new Scanner(linha);
                String emailDoador = tec.next().trim();

                if (catalogoDoadores.buscarPorEmail(emailDoador) != null) {
                    System.out.println("4:" + catalogoDoadores.imprimeDoadorEmail(emailDoador));
                } else {
                    System.out.println("4:ERRO:e-mail inexistente");
                }

                tec.close();
            } else {
                System.out.println("Arquivo vazio");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
