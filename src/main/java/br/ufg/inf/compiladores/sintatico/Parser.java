package br.ufg.inf.compiladores.sintatico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.google.common.base.Strings;
import com.google.common.collect.Table;

import br.ufg.inf.compiladores.lexico.*;

public class Parser {
    private boolean houveErroSintatico;
    Scanner scanner;
    Stack<Integer> pilha;
    static List<Regra> regras;
    static Table<Integer, String, Action> tabela;
    Integer estadoAtual;
    Token token;
    String fonte_objeto;
    String fonte_objeto_final;
    Tipo tipo;
    LinkedList<Token> pilha_semantica;
    LinkedList<String> pilha_semantica_repita;
    LinkedList<Token> listaVariaveisTemporarias;
    int variaveisTemporarias;
    private boolean houveErroSemantico;
    private int qtdTabs;
    Token tx,oprd2,opm,oprd1,id,ld,opr;
    String declaracaoTx;
    public Parser() throws IOException {
        
    }

    public void parseFonte(String pathArquivoFonte,String pathArquivoDestino) throws IOException {
        regras = Gramatica.getListRegrasGramatica("definicoes/GRAMATICA_MGOL.TXT", Charset.defaultCharset());
        tabela = Gramatica.getTabelaActionGoto("definicoes/TABELA_ACTION_GOTO_UNICA.csv", Charset.defaultCharset());
        fonte_objeto = "";

        houveErroSemantico = false;
        houveErroSintatico = false;
        variaveisTemporarias = 0;
        qtdTabs = 0;
        pilha = new Stack<>();
        pilha.push(Integer.valueOf(0));
        pilha_semantica  = new LinkedList<Token>();
        pilha_semantica_repita = new LinkedList<String>();
        listaVariaveisTemporarias  = new LinkedList<Token>();
        scanner = new Scanner(pathArquivoFonte);

        token = scanner.scanner();
        Integer estadoAtual;
        String simbolo;
        Action acao;
        while (true) {
            estadoAtual = pilha.peek();
            simbolo = token.classe.toString();
            acao = tabela.get(estadoAtual, simbolo);
            //System.out.println("Pilha: " + pilha + " | Token: " + token + " || Acao: " + acao);
            if (acao instanceof Shift) {
                Shift t = (Shift) acao;
                pilha.push(t.getEstadoShift());
                token = scanner.scanner();
                simbolo = token.classe.toString();
                
                if(token.classe == Classe.varfim){
                } else if( token.classe == Classe.id 
                        || token.classe == Classe.num 
                        || token.classe == Classe.lit
                        || token.classe == Classe.vir
                        || token.classe == Classe.opm
                        || token.classe == Classe.opr) {
                            pilha_semantica.add(token);
                }

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
                //System.out.println(t + " | " + regra);

                regraSemantica(regra.getNumero(), regra.ladoEsquerdo);
                
            } else if (acao instanceof Accept) {
                //System.out.println(acao);
                fonte_objeto_final = "#include<stdio.h>\n" + 
                "typedef char literal[256];\n" +
                "void main(void)\n" + 
                "{/*----Variaveis temporarias----*/\n"; 
                //fonte_objeto += Strings.repeat("\t", qtdTabs) +  "\n}\n";
                for (Token tk : listaVariaveisTemporarias) {

                    switch(tk.tipo){
                        case literal:
                            fonte_objeto_final += tk.tipo + " " + tk.lexema +";\n";
                            break;
                        case inteiro:
                            fonte_objeto_final += "int " + tk.lexema +";\n";
                            break;
                        case real:
                            fonte_objeto_final += "double " + tk.lexema +";\n";
                            break;
                        
                    }
                }
                fonte_objeto_final += "/*------------------------------*/\n";
                fonte_objeto_final += fonte_objeto;
                //System.out.println("\n\n\n#############################################################\n\n");
                if( !houveErroSemantico && !houveErroSintatico && !scanner.houveErroLexico()){

                    System.out.println(fonte_objeto_final);
                }
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
                simbolo = token.classe.toString();
                
                if(token.classe == Classe.varfim){
                } else if( token.classe == Classe.id 
                        || token.classe == Classe.num 
                        || token.classe == Classe.lit
                        || token.classe == Classe.vir
                        || token.classe == Classe.opm
                        || token.classe == Classe.opr) {
                            pilha_semantica.add(token);
                }
            }
            //System.out.println(pilha_semantica);
        }
    }

    public void regraSemantica(int numeroRegra, String naoTerminal){
        switch(numeroRegra){
            case 5:
                fonte_objeto += "\n\n";
                pilha_semantica.clear();
                break;
            case 6:
                // finaliza a declaracao de um tipo de variaveis
                // logica pra declaracao de 1 ou mais IDs na lista de variaveis
                Iterator<Token> itr = pilha_semantica.iterator();
                while (itr.hasNext()) {
                    Token tok = itr.next();

                    if(tok.tipo != Tipo.NULO){
                        //variavel já foi declarada antes
                        houveErroSemantico = true;
                        System.out.println("Erro11: Variável "+tok.lexema + " na linha "
                        + scanner.getLinha() + " e coluna " + scanner.getColuna()
                        +" já foi declarada anteriormente.");
                    }

                    if(tok.classe == Classe.id || tok.classe == Classe.vir  ){
                        tok.tipo = tipo;
                        fonte_objeto +=  tok.lexema;
                    }
                    itr.remove();
                }
                fonte_objeto += ";\n"; 
                break;
            case 9:
                tipo = Tipo.inteiro;
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  "int ";
                break;  
            case 10:
                tipo = Tipo.real;
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  "double ";
                break;  
            case 11:
                tipo = Tipo.literal;
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  "literal ";
                break;  
            case 13:    
                Token id = pilha_semantica.removeLast();
                switch(id.tipo){
                    case literal:
                        fonte_objeto += Strings.repeat("\t", qtdTabs) +  "scanf (\"%s\","+ id.lexema+");\n";
                        break;
                    case inteiro:
                        fonte_objeto += Strings.repeat("\t", qtdTabs) +  "scanf (\"%d\",&"+ id.lexema+");\n";
                        break;
                    case real:
                        fonte_objeto += Strings.repeat("\t", qtdTabs) +  "scanf (\"%lf\",&"+ id.lexema+");\n";
                        break;
                    default:
                        houveErroSemantico = true;
                        System.out.println("Erro1: Variável "+id.lexema 
                        +" não declarada encontrada na linha "
                        + scanner.getLinha() + " e coluna " + scanner.getColuna());
                        break;
                }
                break;  
            case 14:
                Token arg = tx;
                switch(arg.tipo){
                    case literal:
                        if(arg.classe == Classe.id){
                            fonte_objeto += Strings.repeat("\t", qtdTabs) +  "printf (\"%s\","+ arg.lexema+");\n";
                        } else {
                            fonte_objeto += Strings.repeat("\t", qtdTabs) +  "printf ("+ arg.lexema+");\n";
                        }
                        break;
                    case inteiro:
                        fonte_objeto += Strings.repeat("\t", qtdTabs) +  "printf (\"%d\","+ arg.lexema+");\n";
                        break;
                    case real:
                        fonte_objeto += Strings.repeat("\t", qtdTabs) +  "printf (\"%lf\","+ arg.lexema+");\n";
                        break;
                    default:
                        houveErroSemantico = true;
                        System.out.println("Erro2: Variável "+arg.lexema 
                        +" não declarada encontrada na linha "
                        + scanner.getLinha() + " e coluna " + scanner.getColuna());
                        break;
                }
                break;  
            case 15:
                tx = pilha_semantica.removeLast();
                break;
            case 16:
                tx = pilha_semantica.removeLast();
                break;
            case 17:
                tx = pilha_semantica.removeLast();
                if(tx.tipo == Tipo.NULO){
                    houveErroSemantico = true;
                    System.out.println("Erro3: Variável "+tx.lexema 
                    +" não declarada encontrada na linha "
                    + scanner.getLinha() + " e coluna " + scanner.getColuna());
                }
                break;   
            case 19:
                int idx_tx = pilha_semantica.indexOf(tx);
                id = pilha_semantica.remove(idx_tx-1);
                idx_tx = pilha_semantica.indexOf(tx);
                tx = pilha_semantica.remove(idx_tx);
                pilha_semantica.remove(tx);

                if(id.tipo == Tipo.NULO){
                    houveErroSemantico = true;
                    System.out.println("Erro7: Variável "+ id.lexema 
                    +" não declarada encontrada na linha "
                    + scanner.getLinha() + " e coluna " + scanner.getColuna());
                }
                else if( tx.tipo == id.tipo ){
                    fonte_objeto += Strings.repeat("\t", qtdTabs) +  id.lexema + "="+ tx.lexema +";\n";
                } else {
                    houveErroSemantico = true;
                    System.out.println("Erro6:  Tipos diferentes para atribuição na linha "
                    + scanner.getLinha() + " e coluna " + scanner.getColuna());
                }    
                break; 
            case 20:
                oprd2 = pilha_semantica.removeLast();
                opm = pilha_semantica.removeLast();
                oprd1 = pilha_semantica.removeLast();
                
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  "T"+ variaveisTemporarias +" = "
                + oprd1.lexema + opm.lexema + oprd2.lexema +";\n";
                tx = new Token(Classe.num,"T"+variaveisTemporarias,oprd1.tipo);
                pilha_semantica.addLast(tx);
                listaVariaveisTemporarias.addLast(tx);
                variaveisTemporarias++;
            
                if( oprd1.tipo != oprd2.tipo || oprd1.tipo == Tipo.literal || oprd2.tipo == Tipo.literal ){
                    houveErroSemantico = true;
                    System.out.println("Erro5: Operandos ("+oprd1.lexema + " e "+ oprd2.lexema +") com tipos ("+oprd1.tipo+""+ oprd2.tipo+") incompatíveis na linha "
                    + scanner.getLinha() + " e coluna " + scanner.getColuna());
                }
                break;    
            case 21:
                //fonte_objeto += Strings.repeat("\t", qtdTabs) +  " REGRA 21 tem so uma atribuicao simples com 1 operador\n";
                tx = pilha_semantica.peekLast();
                break;   
            case 22:
                if(pilha_semantica.get(pilha_semantica.size()-2).tipo == Tipo.NULO && pilha_semantica.get(pilha_semantica.size()-2).classe == Classe.id){
                    houveErroSemantico = true;
                    System.out.println("Erro4: Variável "+pilha_semantica.get(pilha_semantica.size()-2).lexema 
                    +" não declarada encontrada na linha "
                    + scanner.getLinha() + " e coluna " + scanner.getColuna());
                }
                break;    
             case 25:
                qtdTabs--;
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  "}\n";
                break;      
             case 26:
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  "if ("+tx.lexema+") {\n";
                pilha_semantica.remove(tx);
                qtdTabs++;
                break;
            case 27:
                oprd2 = pilha_semantica.removeLast();
                opr = pilha_semantica.removeLast();
                oprd1 = pilha_semantica.removeLast();
                
                switch(opr.lexema){
                    case "=":
                        if((oprd1.tipo == Tipo.inteiro && oprd2.tipo == Tipo.inteiro ) || (oprd1.tipo == Tipo.real && oprd2.tipo == Tipo.real )){
                            declaracaoTx = "T"+ variaveisTemporarias +" = "
                            + oprd1.lexema + "==" + oprd2.lexema +";\n";
                            tx = new Token(Classe.num,"T"+variaveisTemporarias,oprd1.tipo);
                        } else {
                            houveErroSemantico = true;
                            System.out.println("Erro18: Operandos ("+oprd1.lexema + " e "+ oprd2.lexema +") com tipos ("+oprd1.tipo+" e "+ oprd2.tipo+") incompatíveis na linha "
                            + scanner.getLinha() + " e coluna " + scanner.getColuna());
                            tx = new Token(Classe.error,"T"+variaveisTemporarias,Tipo.NULO);
                        }
                        break;
                    case "<>":
                        if((oprd1.tipo == Tipo.inteiro && oprd2.tipo == Tipo.inteiro ) || (oprd1.tipo == Tipo.real && oprd2.tipo == Tipo.real )){
                            declaracaoTx = "T"+ variaveisTemporarias +" = "
                            + oprd1.lexema + "!=" + oprd2.lexema +";\n";
                            tx = new Token(Classe.num,"T"+variaveisTemporarias,oprd1.tipo);
                        } else {
                            houveErroSemantico = true;
                            System.out.println("Erro19: Operandos ("+oprd1.lexema + " e "+ oprd2.lexema +") com tipos ("+oprd1.tipo+" e "+ oprd2.tipo+") incompatíveis na linha "
                            + scanner.getLinha() + " e coluna " + scanner.getColuna());
                            tx = new Token(Classe.error,"T"+variaveisTemporarias,Tipo.NULO);
                        }
                        break;
                    default:
                        if((oprd1.tipo == Tipo.inteiro && oprd2.tipo == Tipo.inteiro ) || (oprd1.tipo == Tipo.real && oprd2.tipo == Tipo.real )){
                            declaracaoTx = "T"+ variaveisTemporarias +" = "
                            + oprd1.lexema + opr.lexema + oprd2.lexema +";\n";
                            tx = new Token(Classe.num,"T"+variaveisTemporarias,oprd1.tipo);
                        } else {
                            houveErroSemantico = true;
                            System.out.println("Erro20: Operandos ("+oprd1.lexema + " e "+ oprd2.lexema +") com tipos ("+oprd1.tipo+" e "+ oprd2.tipo+") incompatíveis na linha "
                            + scanner.getLinha() + " e coluna " + scanner.getColuna());
                            tx = new Token(Classe.error,"T"+variaveisTemporarias,Tipo.NULO);
                        }
                        
                    break;
                }
                    
                variaveisTemporarias++; 
                listaVariaveisTemporarias.addLast(tx);
                    
                pilha_semantica.addLast(tx);
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  declaracaoTx;
                
                break;
            case 34:
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  "while ("+tx.lexema+") {\n";
                pilha_semantica.remove(tx);
                pilha_semantica_repita.push(declaracaoTx); // colocar a declaracao do tx no topo da pilha do repita pra ser utilizada no fechamento do fimrepita
                qtdTabs++;
                break;
            case 38:
                fonte_objeto += Strings.repeat("\t", qtdTabs) +  pilha_semantica_repita.pollLast()+"}\n";
                qtdTabs--;
                break;
            case 39:
                fonte_objeto += Strings.repeat("\t", qtdTabs) + "}\n";
                qtdTabs--;
                break;
            default:
                //System.out.println("entrou aqui! regra: "+numeroRegra);
                break;
        }


    }

}