package br.ufg.inf.compiladores.sintatico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Stack;
import com.google.common.collect.Table;

import br.ufg.inf.compiladores.lexico.Alfabeto;
import br.ufg.inf.compiladores.lexico.Scanner;
import br.ufg.inf.compiladores.lexico.Tipo;
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
            //System.out.println("Pilha: " + pilha + " | Simbolo: " + simbolo + " || Acao: " + acao);
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
                    System.out.println("Acao: " + t + " | Regra: " + regra);
                    
                }
                else {
                    System.out.println("DEU ERRO NO GOTO!!!");
                    System.out.println(acao + "\n" + regra + "\nSimbolo: " + simbolo);
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
                System.out.println("DEU ERRO DE ACTION!!!");
                System.out.println(acao + "\nSimbolo: " + simbolo);
                trataErro(estadoAtual, token, (Error) acao);
                return;

            }
        }
    }


    private void trataErro(Integer estadoAtual, Token token, Error erro){

        if(erro.numero >= 1 && erro.numero <= 24){
           //Esperado ler inicio
            System.out.println("TOKEN ESPERADO: ["+ Tipo.inicio + "] mas encontrado o Token = ["+ token + "] na linha " + scanner.getLinha() + " e coluna "+ scanner.getColuna());
        }
        else{

             System.out.println( token + " não era esperado na linha " + scanner.getLinha() + " e coluna "+ scanner.getColuna());
        }

    }

    
}