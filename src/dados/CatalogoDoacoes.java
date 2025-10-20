package dados;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CatalogoDoacoes {

    private Collection<Doacao> doacoes;
    private CatalogoDoadores catalogoDoadores;
    private Duravel duravel;
    private Perecivel perecivel;
    private Doacao doacao;

    public CatalogoDoacoes(CatalogoDoadores catalogoDoadores) {
        this.doacoes = new ArrayList<>();
        this.catalogoDoadores = catalogoDoadores;
    }

    public void cadastrarDoacoesPereciveis() {
        Path path = Paths.get("recursos/doacoespereciveis.csv");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {

                if (linha.trim().isEmpty()) continue;
                // ignora cabeçalho
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

                    // Converte tipoStr para enum de forma segura
                    TipoPerecivel tipo;
                    try {
                        tipo = TipoPerecivel.valueOf(tipoStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("2:ERRO:tipo invalido: " + tipoStr);
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
                    try {
                        tipo = TipoDuravel.valueOf(tipoStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("3:ERRO:tipo invalido: " + tipoStr);
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
            System.err.println("Erro ao ler o arquivo de doações duráveis: " + e.getMessage());
        }
    }

    public void mostraTodasDoacoes() {
        if (doacoes.isEmpty()) {
            System.out.println("5:ERRO:nenhuma doacao cadastrada");
            return;
        }
        for (Doacao doacao : doacoes) {
            String resumo = doacao.geraResumo();
            Doador doador = doacao.getDoador();
            if (doador != null) {
                resumo += "," + doador.getNome() + "," + doador.getEmail();
            }
            System.out.println("5:" + resumo);
        }
    }

    public void mostrarTodosDoadores() {
        Map<String, Integer> contagemDoacoes = new HashMap<>();

        for (Doacao doacao : doacoes) {
            Doador doador = doacao.getDoador();
            if (doador == null) {
                System.out.println("6:ERRO:nenhum doador encontrado.");
            }

            String email = doador.getEmail();
            contagemDoacoes.put(email, contagemDoacoes.getOrDefault(email, 0) + 1);
        }

        for (String email : contagemDoacoes.keySet()) {
            Doador doador = catalogoDoadores.buscarPorEmail(email);
            int qtd = contagemDoacoes.get(email);
            System.out.println("6:" + doador.getNome() + "," + doador.getEmail() + "," + qtd);
        }
    }

    public void mostrarDoacoesDeUmDoador(Doador doador) {
        for (Doacao doacao1 : doacoes) {
            if(doacao1.getDoador().equals(doador)){
                System.out.println("7:" + doacao1.geraResumo() + "," + doador.getEmail());
            }
        }

    }

}
