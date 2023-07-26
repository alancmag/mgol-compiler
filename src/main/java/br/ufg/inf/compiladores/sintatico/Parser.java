package br.ufg.inf.compiladores.sintatico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.common.collect.Table;

import br.ufg.inf.compiladores.lexico.*;

public class Parser {
    private boolean houveErroSintatico = false;
    Scanner scanner;
    Stack<Integer> pilha;
    static List<Regra> regras;
    static Table<Integer, String, Action> tabela;
    Integer estadoAtual;
    Token token;

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
            acao = tabela.get(estadoAtual, simbolo);
            // System.out.println("Pilha: " + pilha + " | Simbolo: " + simbolo + " || Acao:
            // " + acao);
            if (acao instanceof Shift) {
                Shift t = (Shift) acao;
                pilha.push(t.getEstadoShift());
                token = scanner.scanner();
            } else if (acao instanceof Reduce) {
                Reduce t = (Reduce) acao;
                Regra regra = regras.get(t.getNumeroRegraReducao());
                for (int i = 0; i < regra.getQtdLadoDireito(); i++) {
                    pilha.pop();
                }
                estadoAtual = pilha.peek();
                acao = tabela.get(estadoAtual, regra.ladoEsquerdo);

                if (acao instanceof Goto) {
                    Goto acaoGoto = (Goto) acao;
                    Integer gotoEstado = acaoGoto.getEstadoGoto();
                    pilha.push(gotoEstado);
                    System.out.println("Acao: " + t + " | Regra: " + regra);

                } else {
                    houveErroSintatico = true;
                    // System.out.println(acao + "\n" + regra + "\nSimbolo: " + simbolo);
                    trataErro((Error) acao);
                    if (token.classe.equals(Classe.$)) {
                        return;
                    }
                }
            } else if (!houveErroSintatico && !scanner.houveErroLexico() && (acao instanceof Accept)) {
                System.out.println("FONTE ACEITO!");
                return;
            } else {
                houveErroSintatico = true;
                // System.out.println("DEU ERRO DE ACTION!!!");
                // System.out.println(acao + "\nSimbolo: " + simbolo);
                trataErro((Error) acao);
                
                if (token.classe.equals(Classe.$)) {
                    return;
                }

            }
        }
    }

    private void trataErro(Error erro) throws IOException {
        if (token == null)
            return;

        List<String> esperados = new ArrayList<>();
        for (String simbolo : tabela.columnKeySet()) {
            if (!(tabela.get(estadoAtual, simbolo) instanceof Error) && Classe.isTerminal(simbolo)) {
                esperados.add(simbolo);
            }
        }

        // tem apenas um simbolo de token esperado, e esse simbolo tem uma Action para
        // executar no estado atual
        if (esperados.size() == 1 && !(tabela.get(estadoAtual, esperados.get(0)) instanceof Error)) {
            aplicaIteracaoComSimbolo(esperados.get(0));
            // depois aplica o estado novo esperado com o simbolo lido
            aplicaIteracaoComSimbolo(token.classe.toString());
            return;
        }

        System.out.println("ERRO NUMERO: " + erro.numero + " => O token [" + token + "] não era esperado na linha "
                + scanner.getLinha() + " e coluna " + scanner.getColuna()
                + "\t=> Em vez disso, era(am) esperado(os) = "
                + esperados.toString().replaceAll(",", " ou"));

    }

    private void aplicaIteracaoComSimbolo(String simbolo) throws IOException {
        Action acao = tabela.get(estadoAtual, simbolo);
        // System.out.println("====> Pilha: " + pilha + " | Simbolo: " + simbolo + " ||
        // Acao: " + acao);
        if (acao instanceof Shift) {
            Shift t = (Shift) acao;
            pilha.push(t.getEstadoShift());
            token = scanner.scanner();
        } else if (acao instanceof Reduce) {
            Reduce t = (Reduce) acao;
            Regra regra = regras.get(t.getNumeroRegraReducao());
            for (int i = 0; i < regra.getQtdLadoDireito(); i++) {
                pilha.pop();
            }
            estadoAtual = pilha.peek();
            acao = tabela.get(estadoAtual, regra.ladoEsquerdo);

            if (acao instanceof Goto) {
                Goto acaoGoto = (Goto) acao;
                Integer gotoEstado = acaoGoto.getEstadoGoto();
                pilha.push(gotoEstado);
                System.out.println("Acao: " + t + " | Regra: " + regra);

            } else {
                // System.out.println("DEU ERRO NO GOTO!!!");
                // System.out.println(acao + "\n" + regra + "\nSimbolo: " + simbolo);
                trataErro((Error) acao);
                // return;
            }
        } else {
            // System.out.println("DEU ERRO DE ACTION!!!");
            // System.out.println(acao + "\nSimbolo: " + simbolo);
            trataErro((Error) acao);

            if (token.classe.equals(Classe.$)) {
                return;
            }
        }
    }

}