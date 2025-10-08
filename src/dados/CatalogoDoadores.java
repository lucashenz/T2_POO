package dados;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Scanner;

public class CatalogoDoadores {
    private ClientInfoStatus<Doador> doadores;

    public CatalogoDoadores{
        doadores = new ArrayList<>();
    }

    public void lerArquivo() {
        Path path = Paths.get("recursos/doadores.csv");
        try(BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha = null;

            while ((linha = bufferedReader.readLine()) != null)) {
                //separador
                Scanner tec = new Scanner(linha).useDelimiter(";"); //para em ;
                String nome;
                String email;
                nome = tec.next();
                email = tec.next();

                System.out.println(nome + ": " + email + "; ");
            }
        }
        catch (IOException e) {
            System.err.format("Erro");
        }
    }


}
