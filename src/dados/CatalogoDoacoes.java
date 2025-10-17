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

    // Construtor corrigido
    public CatalogoDoacoes(CatalogoDoadores catalogoDoadores) {
        this.doacoes = new ArrayList<>();
        this.catalogoDoadores = catalogoDoadores;
    }

    public void cadastrarDoacaoDePerecivel() {
        Path path = Paths.get("recursos/doacoespereciveis.csv");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;

            // Ignora o cabeçalho
            bufferedReader.readLine();

            while ((linha = bufferedReader.readLine()) != null) {
                try {
                    Scanner sc = new Scanner(linha).useDelimiter(";");

                    // ordem do CSV: DESCRICAO;VALOR;QUANTIDADE;E-MAIL;TIPO;VALIDADE
                    String descricao = sc.next();
                    double valor = Double.parseDouble(sc.next());
                    int quantidade = Integer.parseInt(sc.next());
                    String emailDoador = sc.next();
                    String tipoStr = sc.next().toUpperCase();
                    int validade = Integer.parseInt(sc.next());

                    // valida tipo
                    TipoPerecivel tipo;
                    if (tipoStr.equals("ALIMENTO")) {
                        tipo = TipoPerecivel.ALIMENTO;
                    } else if (tipoStr.equals("MEDICAMENTO")) {
                        tipo = TipoPerecivel.MEDICAMENTO;
                    } else {
                        System.out.println("2:ERRO:tipo invalido");
                        continue;
                    }

                    // busca doador
                    Doador doador = catalogoDoadores.buscarPorEmail(emailDoador);
                    if (doador == null) {
                        System.out.println("2:ERRO:doador inexistente");
                        continue;
                    }

                    // cria o objeto Perecivel usando o construtor da sua classe
                    Perecivel p = new Perecivel(descricao, valor, quantidade, doador, validade, tipo);

                    // adiciona ao catálogo
                    doacoes.add(p);

                    // exibe resumo
                    System.out.println("2:" + p.geraResumo());

                } catch (NumberFormatException e) {
                    System.out.println("2:ERRO:formato invalido");
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de doações perecíveis.");
        }
    }
}
