package br.ufg.inf.compiladores.sintatico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Stack;
import com.google.common.collect.Table;
import br.ufg.inf.compiladores.lexico.Scanner;
import br.ufg.inf.compiladores.lexico.Token;

public class Parser {
    Scanner scanner;
    Stack<Integer> pilha;
    static List<Regra> regras;
    static Table<Integer, String, Action> tabela;

    public Parser() throws IOException {
        regras = Gramatica.getListRegrasGramatica("definicoes\\GRAMATICA_MGOL.TXT", Charset.defaultCharset());
        tabela = Gramatica.getTabelaActionGoto("definicoes\\TABELA_ACTION_GOTO_UNICA.csv", Charset.defaultCharset());
    }

    public void parseFonte(String pathArquivoFonte) throws IOException {
        pilha = new Stack<>();
        pilha.push(Integer.valueOf(0));
        scanner = new Scanner(pathArquivoFonte);
        
        Token token = scanner.scanner();
        Integer estadoAtual;
        String simbolo;
        Action acao;
        while (true) {
            estadoAtual = pilha.peek();
            simbolo = token.classe.toString();
            acao = tabela.get(estadoAtual,simbolo);
            System.out.println("Pilha: " + pilha + " | Simbolo: " + simbolo + " || Acao: " + acao);
            if (acao instanceof Shift) 
            {
                Shift t = (Shift) acao;
                pilha.push(t.getEstadoShift());
                token = scanner.scanner();
            } 
            else if (acao instanceof Reduce) 
            {
                Reduce t = (Reduce) acao;
                Regra regra = regras.get(t.getNumeroRegraReducao());
                for (int i = 0; i < regra.getQtdLadoDireito(); i++) {
                    pilha.pop();
                }
                estadoAtual = pilha.peek();
                acao = tabela.get(estadoAtual, regra.ladoEsquerdo);
                if(acao instanceof Goto){
                    Goto acaoGoto = (Goto) acao;
                    Integer gotoEstado = acaoGoto.getEstadoGoto();
                    pilha.push(gotoEstado);
                }
                else {
                    System.out.println(regra);
                    System.out.println("ERROR GOTO ="+acao );
                    System.out.println("DEU ERRO NO GOTO!!!");
                    return;
                }
            } 
            else if (acao instanceof Accept) 
            {
                System.out.println("FONTE ACEITO!");
                return;
            } 
            else 
            {
                System.out.println("ERROR="+token );
                System.out.println("DEU ERRO DE ACTION!!!");
                return;

            }
        }
    }


    
}