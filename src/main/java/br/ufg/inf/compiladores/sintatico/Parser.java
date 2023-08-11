package br.ufg.inf.compiladores.sintatico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
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
    String fonte_objeto;
    Tipo tipo;
    List<Token> pilha_semantica = new ArrayList<>();
    int variaveisTemporarias = 0;
    

    public Parser() throws IOException {
        regras = Gramatica.getListRegrasGramatica("definicoes/GRAMATICA_MGOL.TXT", Charset.defaultCharset());
        tabela = Gramatica.getTabelaActionGoto("definicoes/TABELA_ACTION_GOTO_UNICA.csv", Charset.defaultCharset());
        fonte_objeto = "#include<stdio.h> \n" + 
                "typedef char literal[256];\n" +
                "void main(void)\n" + 
                "{\n" + 
                "/*----Variaveis temporarias----*/\n"; 

        fonte_objeto = "";
    }

    public void parseFonte(String pathArquivoFonte) throws IOException {
        pilha = new Stack<>();
        pilha.push(Integer.valueOf(0));
        scanner = new Scanner(pathArquivoFonte);

        token = scanner.scanner();
        Integer estadoAtual;
        String simbolo;
        Action acao;
        while (true) {
            estadoAtual = pilha.peek();
            simbolo = token.classe.toString();
            acao = tabela.get(estadoAtual, simbolo);
            //System.out.println("Pilha: " + pilha + " | Simbolo: " + simbolo + " || Acao: " + acao);
            if (acao instanceof Shift) {
                Shift t = (Shift) acao;
                pilha.push(t.getEstadoShift());
                token = scanner.scanner();
                pilha_semantica.add(token);
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

                regraSemantica(regra.getNumero(), regra.ladoEsquerdo);
                
            } else if (acao instanceof Accept) {
                System.out.println(acao);

                fonte_objeto += "\n}";
                System.out.println("\n\n\n#############################################################\n\n");
                System.out.println(fonte_objeto);


                return;
            } else {
                houveErroSintatico = true;

                if(simbolo.equals(Classe.$.toString())){
                    System.out.println(acao + " => Fim de arquivo encontrado inesperadamente!");
                   return;
                }

                List<String> esperados = new ArrayList<>();
                for (String simbolo_tabela : tabela.columnKeySet()) {
                    if (!(tabela.get(estadoAtual, simbolo_tabela) instanceof Error) && Classe.isTerminal(simbolo_tabela)) {
                        esperados.add(simbolo_tabela);
                    }
                }
                
                System.out.println(acao + " => O token [" + token + "] não era esperado na linha "
                        + scanner.getLinha() + " e coluna " + scanner.getColuna()
                        + " => Em vez disso, era(am) esperado(os) = "
                        + esperados.toString().replaceAll(",", " ou"));

                // consome o token
                token = scanner.scanner();
            }
            System.out.println(pilha_semantica);
        }
    }

    public void regraSemantica(int numeroRegra, String naoTerminal){
        if(houveErroSintatico || scanner.houveErroLexico()) return;
        //System.out.println(pilha_semantica);

        switch(numeroRegra){
            case 5:
                fonte_objeto += "\n\n\n";
                break;  
            case 6:
                // (A) Amarração de atributos, organizar a passagem de valores do atributo TIPO.tipo, para L.TIPO;
                
                
                break;
            case 7:   
            case 8:
                Iterator<Token> itr = pilha_semantica.iterator();
                itr.next(); // Pular o varinicio
                while (itr.hasNext()) {
                    Token tok = itr.next();
                    if(tok.classe == Classe.id || tok.classe == Classe.vir  ){
                        tok.tipo = tipo;
                        fonte_objeto += " " + tok.lexema;
                        
                    }
                    itr.remove();
                }
                if(pilha_semantica.size() == 1 && !fonte_objeto.endsWith(";\n")){
                    fonte_objeto += ";\n";
                }
                break;  
            case 9:
                // 
                tipo = Tipo.inteiro;
                fonte_objeto += " int ";
                pilha_semantica.remove(pilha_semantica.size()-2);
                break;  
            case 10:
                tipo = Tipo.real;
                fonte_objeto += " double ";
                pilha_semantica.remove(pilha_semantica.size()-2);
                break;  
            case 11:
                tipo = Tipo.literal;
                fonte_objeto += " " + Tipo.literal + " ";
                pilha_semantica.remove(pilha_semantica.size()-2);

                break;                  
            default:
            System.out.println("entrou aqui!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                break;
        }


    }

}