package dados;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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
                        System.out.println("2:ERRO:formato invalido.");
                        continue;
                    }

                    Doador doador = catalogoDoadores.buscarPorEmail(emailDoador);
                    if (doador == null) {
                        System.out.println("2:ERRO:doador inexistente.");
                        continue;
                    }

                    TipoPerecivel tipo;
                    try {
                        tipo = TipoPerecivel.valueOf(tipoStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("2:ERRO:tipo invalido.");
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

                // pula cabeçalho
                if (linha.toLowerCase().contains("descricao") && linha.toLowerCase().contains("tipo")) continue;

                try (Scanner sc = new Scanner(linha).useDelimiter("[;\t]")) {

                    if (!sc.hasNext()) throw new IllegalArgumentException();
                    String descricao = sc.next().trim();

                    if (!sc.hasNext()) throw new IllegalArgumentException();
                    String valorStr = sc.next().trim();

                    if (!sc.hasNext()) throw new IllegalArgumentException();
                    String quantidadeStr = sc.next().trim();

                    if (!sc.hasNext()) throw new IllegalArgumentException();
                    String emailDoador = sc.next().trim();

                    if (!sc.hasNext()) throw new IllegalArgumentException();
                    String tipoStr = sc.next().trim().toUpperCase();

                    double valor;
                    int quantidade;

                    try {
                        valor = Double.parseDouble(valorStr);
                        quantidade = Integer.parseInt(quantidadeStr);
                    } catch (NumberFormatException e) {
                        System.out.println("3:ERRO:formato invalido.");
                        continue;
                    }

                    Doador doador = catalogoDoadores.buscarPorEmail(emailDoador);
                    if (doador == null) {
                        System.out.println("3:ERRO:doador inexistente.");
                        continue;
                    }

                    TipoDuravel tipo;
                    try {
                        tipo = TipoDuravel.valueOf(tipoStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("3:ERRO:tipo invalido.");
                        continue;
                    }

                    Duravel d = new Duravel(descricao, valor, quantidade, doador, tipo);
                    doacoes.add(d);

                    System.out.println("3:" + descricao + "," + valor + "," + quantidade + "," + tipo);

                } catch (Exception e) {
                    System.out.println("3:ERRO:formato invalido.");
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
        boolean encontrou = false;

        for (Doacao doacao1 : doacoes) {
            if (doacao1.getDoador().equals(doador)) {
                System.out.println("7:" + doacao1.geraResumo() + "," + doacao1.getDoador().getEmail());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("7:ERRO:nenhuma doacao localizada");
        }
    }

    public void mostrarDoacoesDeUmTipo(String tipo) {
        TipoDuravel tipoA;
        try {
            tipoA = TipoDuravel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("8:ERRO:tipo invalido");
            return;
        }

        for (Doacao doacao : doacoes) {
            if (doacao instanceof Duravel) {
                Duravel duravel = (Duravel) doacao;

                if (duravel.getTipoDuravel() != null &&
                        duravel.getTipoDuravel().getNome().equalsIgnoreCase(tipo)) {

                    System.out.println("8:" + duravel.geraResumo() + "," + duravel.getDoador().getNome() + "," + duravel.getDoador().getEmail());
                }
            }
        }
    }

    public void doacaoPerecivelComMaiorQtd(String tipoStr) {
        TipoPerecivel tipo;
        try {
            tipo = TipoPerecivel.valueOf(tipoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("9:ERRO:tipo invalido");
            return;
        }

        Perecivel maiorDoacao = null;

        for (Doacao doacao : doacoes) {
            if (doacao instanceof Perecivel) {
                Perecivel perecivel = (Perecivel) doacao;
                if (perecivel.getTipoPerecivel() == tipo) {
                    if (maiorDoacao == null || perecivel.getQuantidade() > maiorDoacao.getQuantidade()) {
                        maiorDoacao = perecivel;
                    }
                }
            }
        }

        if (maiorDoacao == null) {
            System.out.println("9:ERRO:nenhuma doacao localizada");
            return;
        }

        System.out.println("9:" + maiorDoacao.geraResumo() + "," +
                maiorDoacao.getDoador().getNome() + "," +
                maiorDoacao.getDoador().getEmail());
    }

    public void mostrarDoadorComMaiorSomatorioDeDoacoes() {
        Doador maiorDoador = null;
        double maiorDoacao = 0;

        for (Doacao doacao1 : doacoes) {
            Doador doadorAtual = doacao1.getDoador();
            double totalDoacoesDoDoador = 0;

            for (Doacao doacao2 : doacoes) {
                if (doacao2.getDoador().equals(doadorAtual)) {
                    totalDoacoesDoDoador += doacao2.getValor();
                }
            }

            if (totalDoacoesDoDoador > maiorDoacao) {
                maiorDoacao = totalDoacoesDoDoador;
                maiorDoador = doadorAtual;
            }
        }

        double maiorSomatorio = maiorDoacao;
        DecimalFormat formato = new DecimalFormat("#.##");
        maiorSomatorio = Double.valueOf(formato.format(maiorDoacao));

        if(maiorDoador != null) {
            System.out.println("10:" + maiorDoador + "," + maiorSomatorio);
        } else {
            System.out.println("10:ERRO:nenhum doador localizado.");
        }
    }

}
