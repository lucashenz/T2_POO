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

    public CatalogoDoadores(ArrayList doadores){
        doadores = new ArrayList<>();
    }

    //metodo 1
    public void cadastrarDoadores() {
        Path path = Paths.get("recursos/doadores.csv");
        int linhaNum = 0; // contador de linha

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                linhaNum++;
                Scanner tec = new Scanner(linha).useDelimiter(";");
                String nome = tec.next();
                String email = tec.next();

                if (buscarPorEmail(email) != null) {
                    System.out.println(linhaNum + "1:ERRO:doador repetido");
                } else {
                    Doador novo = new Doador(nome, email);
                    doadores.add(novo);
                    System.out.println("1:");
                    doadores.toString();
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



}
