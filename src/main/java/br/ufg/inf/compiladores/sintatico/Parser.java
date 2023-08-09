package br.ufg.inf.compiladores.sintatico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.checkerframework.checker.units.qual.s;

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
        regras = Gramatica.getListRegrasGramatica("definicoes/GRAMATICA_MGOL.TXT", Charset.defaultCharset());
        tabela = Gramatica.getTabelaActionGoto("definicoes/TABELA_ACTION_GOTO_UNICA.csv", Charset.defaultCharset());
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
            System.out.println("Pilha: " + pilha + " | Simbolo: " + simbolo + " || Acao: " + acao);
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

                Goto acaoGoto = (Goto) acao;
                Integer gotoEstado = acaoGoto.getEstadoGoto();
                pilha.push(gotoEstado);
                System.out.println(t + " | " + regra);
                
            } else if (acao instanceof Accept) {
                System.out.println(acao);
                return;
            } else {
                houveErroSintatico = true;

                if(simbolo.equals(Classe.$.toString())){
                    System.out.println("ERRO SINTATICO = numero: " + acao.numero + " => Fim de arquivo encontrado inesperadamente!");
                   return;
                }

                List<String> esperados = new ArrayList<>();
                for (String simbolo_tabela : tabela.columnKeySet()) {
                    if (!(tabela.get(estadoAtual, simbolo_tabela) instanceof Error) && Classe.isTerminal(simbolo_tabela)) {
                        esperados.add(simbolo_tabela);
                    }
                }
                
                System.out.println("ERRO NUMERO: " + acao.numero + " => O token [" + token + "] não era esperado na linha "
                        + scanner.getLinha() + " e coluna " + scanner.getColuna()
                        + "\t=> Em vez disso, era(am) esperado(os) = "
                        + esperados.toString().replaceAll(",", " ou"));

                token = scanner.scanner();
                simbolo = token.classe.toString();
            }
        }
    }

}