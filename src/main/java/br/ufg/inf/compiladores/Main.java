package br.ufg.inf.compiladores;

import java.io.IOException;

import br.ufg.inf.compiladores.sintatico.Parser;
 
public class Main {
    public static void main(String[] args) throws IOException {
        
        if(args == null || args.length <1 || !args[0].endsWith(".alg")){
            System.out.println("Caminho do programa fonte .alg de entrada é obrigatótio.\nEx. Main teste.alg MeuFonte.c");
            return;
        }
        if(args.length < 2 || !args[1].endsWith(".c")){
            System.out.println("Nome do programa destino .c obrigatótio.\nEx. Main teste.alg MeuFonte.c");
            return;
        }
        Parser parser = new Parser();
        parser.parseFonte(args[0],args[1]);  
    }
}