package br.ufg.inf.compiladores;

import java.io.IOException;

import br.ufg.inf.compiladores.sintatico.*;
 
public class Main {
    public static void main(String[] args) throws IOException {
        /* T1 FUNCIONANDO
         Scanner scanner = new Scanner("fontes/fonte_sem_erros_comentario.alg");
        Token token;
        do { token = scanner.scanner();
             System.out.println(token);
        } while(!token.classe.equals(Classe.$)); */
        if(args == null || args.length <1 || !args[0].endsWith(".alg")){
            System.out.println("Programa fonte .alg obrigatótio.");
            return;
        }
        // T3 FUNCIONANDO
        Parser parser = new Parser();
        //parser.parseFonte("fontes/fonte_sem_erros.alg","");  
        //parser.parseFonte("fontes/fonteT3.alg","");  
        parser.parseFonte(args[0],"PROGRAMA.c");  
    }
}