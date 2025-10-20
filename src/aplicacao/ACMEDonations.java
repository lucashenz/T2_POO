package aplicacao;

import dados.CatalogoDoacoes;
import dados.CatalogoDoadores;
import dados.Doacao;
import dados.Doador;

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
        catalogoDoadores.cadastrarDoadores(); // 1
        catalogoDoacoes.cadastrarDoacoesPereciveis(); // 2
        catalogoDoacoes.cadastrarDoacoesDuraveis(); // 3
        mostrarDadosDeUmDoador(); // 4
        catalogoDoacoes.mostraTodasDoacoes(); // 5
        catalogoDoacoes.mostrarTodosDoadores(); // 6
        mostrarDoacaoPorNome();// 7
        mostrarDoacoesDeUmTipo(); // 8
        doacaoPerecivelComMaiorQtd(); // 9
        catalogoDoacoes.mostrarDoadorComMaiorSomatorioDeDoacoes(); // ponto bonus
    }

    private void mostrarDadosDeUmDoador() {
        Path path = Paths.get("recursos/dadosentrada.txt");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha = bufferedReader.readLine();

            if (linha != null) {
                Scanner tec = new Scanner(linha);
                String emailDoador = tec.next().trim();

                if (catalogoDoadores.buscarPorEmail(emailDoador) != null) {
                    System.out.println("4:" + catalogoDoadores.buscarPorEmail(emailDoador));
                } else {
                    System.out.println("4:ERRO:e-mail inexistente");
                }
            } else {
                System.out.println("Arquivo vazio");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarDoacaoPorNome() {
        Path path = Paths.get("recursos/dadosentrada.txt");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {

            bufferedReader.readLine();

            String linha = bufferedReader.readLine();

            if (linha != null) {
                Scanner tec = new Scanner(linha);
                String nomeDoador = tec.nextLine().trim();

                Doador doador = catalogoDoadores.buscarPorNome(nomeDoador);
                catalogoDoacoes.mostrarDoacoesDeUmDoador(doador);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarDoacoesDeUmTipo() {
        Path path = Paths.get("recursos/dadosentrada.txt");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {

            bufferedReader.readLine();
            bufferedReader.readLine();

            String linha3 = bufferedReader.readLine();

            if (linha3 != null) {
                Scanner tec = new Scanner(linha3);
                String tipo = tec.nextLine().trim();

                catalogoDoacoes.mostrarDoacoesDeUmTipo(tipo);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doacaoPerecivelComMaiorQtd() {
        Path path = Paths.get("recursos/dadosentrada.txt");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {

            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();
            String linha4 = bufferedReader.readLine();

            if (linha4 != null) {
                Scanner tec = new Scanner(linha4);
                String tipo = tec.nextLine().trim();

                catalogoDoacoes.doacaoPerecivelComMaiorQtd(tipo);
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
