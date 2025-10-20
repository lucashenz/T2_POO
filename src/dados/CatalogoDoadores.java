package dados;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogoDoadores {
    private List<Doador> doadores;

    public CatalogoDoadores(){
        doadores = new ArrayList<>();
    }

    public void cadastrarDoadores() {
        Path path = Paths.get("recursos/doadores.csv");
        int linhaNum = 0;

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                linhaNum++;

                if (linha.trim().isEmpty()) continue;

                if (linha.toLowerCase().contains("nome") && linha.toLowerCase().contains("mail")) continue;

                Scanner tec = new Scanner(linha).useDelimiter("[;\t]");

                if (!tec.hasNext()) continue;
                String nome = tec.next().trim();

                if (!tec.hasNext()) continue;
                String email = tec.next().trim();

                if (buscarPorEmail(email) != null) {
                    System.out.println(linhaNum + "1:ERRO:doador repetido. ");
                } else {
                    Doador novo = new Doador(nome, email);
                    doadores.add(novo);
                    System.out.println("1:" + nome + ", " + email);
                }

                tec.close();
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // metodo aux
    public Doador buscarPorEmail(String email) {
        for (Doador d : doadores) {
            if (d.getEmail().equalsIgnoreCase(email)) {
                return d;
            }
        }
        return null;
    }

    public Doador buscarPorNome(String nome) {
        for (Doador d : doadores) {
            if (d.getNome().equalsIgnoreCase(nome)) {
                return d;
            }
        }
        return null;
    }



}
