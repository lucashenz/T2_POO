package dados;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class CatalogoDoacoes {

    private Collection<Doacao> doacoes;
    private CatalogoDoadores catalogoDoadores;

    public CatalogoDoacoes(CatalogoDoadores catalogoDoadores) {
        this.doacoes = new ArrayList<>();
        this.catalogoDoadores = catalogoDoadores;
    }

    public void cadastrarDoacoesPereciveis() {
        Path path = Paths.get("recursos/doacoespereciveis.csv");
        int linhaNum = 0;

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                linhaNum++;

                if (linha.trim().isEmpty()) continue;

                if (linha.toLowerCase().contains("descricao") && linha.toLowerCase().contains("tipo")) continue;

                try (Scanner sc = new Scanner(linha).useDelimiter("[;\t]")) {

                    if (!sc.hasNext()) continue;
                    String descricao = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String valorStr = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String quantidadeStr = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String emailDoador = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String tipoStr = sc.next().trim().toUpperCase();

                    if (!sc.hasNext()) continue;
                    String validadeStr = sc.next().trim();

                    double valor;
                    int quantidade;
                    int validade;

                    try {
                        valor = Double.parseDouble(valorStr);
                        quantidade = Integer.parseInt(quantidadeStr);
                        validade = Integer.parseInt(validadeStr);
                    } catch (NumberFormatException e) {
                        System.out.println("2:ERRO:formato invalido");
                        continue;
                    }

                    Doador doador = catalogoDoadores.buscarPorEmail(emailDoador);
                    if (doador == null) {
                        System.out.println("2:ERRO:doador inexistente");
                        continue;
                    }

                    TipoPerecivel tipo;
                    if (tipoStr.equals("ALIMENTO")) {
                        tipo = TipoPerecivel.ALIMENTO;
                    } else if (tipoStr.equals("MEDICAMENTO")) {
                        tipo = TipoPerecivel.MEDICAMENTO;
                    } else {
                        System.out.println("2:ERRO:tipo invalido");
                        continue;
                    }

                    Perecivel p = new Perecivel(descricao, valor, quantidade, doador, validade, tipo);
                    doacoes.add(p);

                    System.out.println("2:" + descricao + "," + valor + "," + quantidade + "," + tipo + "," + validade);

                } catch (Exception e) {
                    System.out.println("2:ERRO:formato invalido");
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de doações perecíveis: " + e.getMessage());
        }
    }

    public void cadastrarDoacoesDuraveis() {
        Path path = Paths.get("recursos/doacoesduraveis.csv");
        int linhaNum = 0;

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                linhaNum++;

                if (linha.trim().isEmpty()) continue;

                if (linha.toLowerCase().contains("descricao") && linha.toLowerCase().contains("tipo")) continue;

                try (Scanner sc = new Scanner(linha).useDelimiter("[;\t]")) {

                    if (!sc.hasNext()) continue;
                    String descricao = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String valorStr = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String quantidadeStr = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String emailDoador = sc.next().trim();

                    if (!sc.hasNext()) continue;
                    String tipoStr = sc.next().trim().toUpperCase();

                    double valor;
                    int quantidade;
                    int validade;

                    try {
                        valor = Double.parseDouble(valorStr);
                        quantidade = Integer.parseInt(quantidadeStr);
                    } catch (NumberFormatException e) {
                        System.out.println("3:ERRO:formato invalido");
                        continue;
                    }

                    Doador doador = catalogoDoadores.buscarPorEmail(emailDoador);
                    if (doador == null) {
                        System.out.println("3:ERRO:doador inexistente");
                        continue;
                    }

                    TipoDuravel tipo;
                    if (tipoStr.equals("ROUPA")) {
                        tipo = TipoDuravel.ROUPA;
                    } else if (tipoStr.equals("MOVEL")) {
                        tipo = TipoDuravel.MOVEL;
                    } else if (tipoStr.equals("MOVEL")) {
                        tipo = TipoDuravel.MOVEL;
                    } else if (tipoStr.equals("BRINQUEDO")) {
                        tipo = TipoDuravel.BRINQUEDO;
                    } else if (tipoStr.equals("ELETRODOMESTICO")) {
                        tipo = TipoDuravel.ELETRODOMESTICO;
                    } else {
                        System.out.println("3:ERRO:tipo invalido");
                        continue;
                    }

                    Duravel d = new Duravel(descricao, valor, quantidade, doador, tipo);
                    doacoes.add(d);

                    System.out.println("3:" + descricao + "," + valor + "," + quantidade + "," + tipo);

                } catch (Exception e) {
                    System.out.println("3:ERRO:formato invalido");
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de doações perecíveis: " + e.getMessage());
        }
    }



}
