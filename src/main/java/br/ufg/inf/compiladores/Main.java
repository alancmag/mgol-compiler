package br.ufg.inf.compiladores;

import java.io.IOException;

import br.ufg.inf.compiladores.lexico.*;
import br.ufg.inf.compiladores.sintatico.*;
 
public class Main {
    public static void main(String[] args) throws IOException {

        /* T1 FUNCIONANDO
         Scanner scanner = new Scanner("fontes/fonte_sem_erros_comentario.alg");
        Token token;
        do {
            token = scanner.scanner();
            System.out.println(token);
        } while(!token.classe.equals(Classe.$)); */

        Parser parser = new Parser();
        parser.parseFonte("fontes/fonte_sem_erros.alg");

    }
}