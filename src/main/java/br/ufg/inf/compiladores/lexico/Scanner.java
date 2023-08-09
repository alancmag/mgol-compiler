package br.ufg.inf.compiladores.lexico;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
    public List<Token> tabelaDeSimbolos = new ArrayList<>();
    private Estado estadoAtual;
    private Estado estadoInicial;
    private boolean houveErro = false;

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public boolean houveErroLexico(){
       return houveErro;
    }

    private int linha;
    private int coluna;
    private int colunaAnterior;
    private final char[] codigoFonte;
    private int indice = 0;
    private int caracteresRestantes;
    private String lexema = "";
    private Simbolo simbolo;

    public Scanner(String pathArquivo) throws IOException {
        codigoFonte = readFile(pathArquivo, Charset.defaultCharset());
        caracteresRestantes = codigoFonte.length;
        linha = coluna = 1;
        inicializaMaquinaDeEstados();
        inicializaTabelaDeSimbolos();
    }

    static private char[] readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString().toCharArray();
    }

    public Token scanner() throws IOException {
        limpaVariaveis();
        while (caracteresRestantes >= 0) {
            simbolo = lerSimbolo();
            // System.out.println(simbolo);

            if (estadoAtual.hasTransicao(simbolo)) {
                Acao acao = estadoAtual.getAcao(simbolo);
                estadoAtual = estadoAtual.nextEstado(simbolo);

                switch (acao) {
                    case IGNORAR:
                        break;
                    case ADICIONAR_AO_LEXEMA:
                        lexema = lexema.concat(simbolo.toString());
                        break;
                    default:
                        throw new IOException("Comportamento inesperado");

                }
            } else {
                Token token;

                if (estadoAtual.isFinal) {
                    retornaIndice();
                    token = geraToken(lexema, estadoAtual.classeTokens, estadoAtual.tipoTokens);

                    if (estadoAtual.classeTokens.equals(Classe.id)) {
                        int index = tabelaDeSimbolos.indexOf(token);
                        if (index != -1) {
                            token = tabelaDeSimbolos.get(index);
                        } else {
                            tabelaDeSimbolos.add(token);
                        }
                    }
                } else {
                    token = geraToken(simbolo.toString(), Classe.error, Tipo.NULO);
                    // System.out.println(token);

                    switch (estadoAtual.nome) {
                        case "q0":
                            if (!simbolo.isParteAlfabeto) {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' é inválido na Linguagem, linha " + linha + ", coluna " + coluna);
                            } else {
                                System.out.println("ERRO LÉXICO - Caractere " + simbolo
                                        + " não era esperado e foi encontrado na linha " + linha + ", coluna "
                                        + coluna);
                            }
                            break;
                        case "q1":
                        case "q4":
                        case "q5":
                            if (!simbolo.isParteAlfabeto && !simbolo.isFinalArquivo) {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' inválido na Linguagem presente na formação no Número Real '" + lexema
                                        + "' na linha " + linha + ", coluna " + coluna);
                            } else if (simbolo.isFinalArquivo) {
                                System.out
                                        .println("ERRO LÉXICO - FIM DE ARQUIVO encontrado na formação do Número Real '"
                                                + lexema + "' na linha " + linha + ", coluna " + coluna);
                            } else {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' não esperado na formação no Número Real '" + lexema + "' na linha " + linha
                                        + ", coluna " + coluna);
                            }
                            break;
                        case "q2":
                        case "q3":
                            if (!simbolo.isParteAlfabeto && !simbolo.isFinalArquivo) {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' inválido na Linguagem presente na formação no Número Inteiro '" + lexema
                                        + "' na linha " + linha + ", coluna " + coluna);
                            } else if (simbolo.isFinalArquivo) {
                                System.out.println(
                                        "ERRO LÉXICO - FIM DE ARQUIVO encontrado na formação do Número Inteiro '"
                                                + lexema + "' na linha " + linha + ", coluna " + coluna);
                            } else {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' não esperado na formação no Número Inteiro '" + lexema + "' na linha "
                                        + linha + ", coluna " + coluna);
                            }
                            break;
                        case "q6":
                            if (!simbolo.isParteAlfabeto && !simbolo.isFinalArquivo) {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' inválido na Linguagem presente na formação do Literal '" + lexema
                                        + "' na linha " + linha + ", coluna " + coluna);
                            } else if (simbolo.isFinalArquivo) {
                                System.out.println("ERRO LÉXICO - FIM DE ARQUIVO encontrado na formação do Literal '"
                                        + lexema + "' na linha " + linha + ", coluna " + coluna);
                            } else {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' não esperado na formação do Literal '" + lexema + "' na linha " + linha
                                        + ", coluna " + coluna);
                            }
                            break;
                        case "q7":
                            if (!simbolo.isParteAlfabeto && !simbolo.isFinalArquivo) {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' inválido na Linguagem presente na formação do Comentário na linha " + linha
                                        + ", coluna " + coluna);
                            } else if (simbolo.isFinalArquivo) {
                                System.out.println(
                                        "ERRO LÉXICO - FIM DE ARQUIVO encontrado na formação do Comentário na linha "
                                                + linha + ", coluna " + coluna);
                            } else {
                                System.out.println("ERRO LÉXICO - Caractere '" + simbolo
                                        + "' não esperado na formação do Comentário na linha " + linha + ", coluna "
                                        + coluna);
                            }
                            break;
                    }
                }

                // System.out.println(token);
                if(token.classe.equals(Classe.error)){
                    houveErro = true;
                    limpaVariaveis();
                }
                else {
                    // Se não é erro retorna o token valido, caso contrário vai rodar de novo a busca por outro token valido.
                    return token;
                }

            }
        }
        // nunca chega aqui
        return null;
    }

    Simbolo lerSimbolo() {
        if (caracteresRestantes == 0) {
            return Alfabeto.EOF;
        }
        char caractere = codigoFonte[indice];
        indice++;
        caracteresRestantes--;
        coluna++;

        if (caractere == '\n') {
            linha++;
            colunaAnterior = coluna;
            coluna = 0;
        }

        if (Alfabeto.pertenceAlfabeto(caractere)) {
            return Alfabeto.getSimbolo(caractere);
        }
        return new Simbolo(caractere, false, false, false, false);
    }

    private Token geraToken(String lexema, Classe classe, Tipo tipo) {
        Token token = new Token();
        token.classe = classe;
        token.tipo = tipo;
        if (classe == Classe.$ || lexema.equals(Alfabeto.EOF.toString())) {
            token.lexema = "EOF";
        } else {
            token.lexema = lexema;
        }
        return token;
    }

    void retornaIndice() {
        if (caracteresRestantes == 0)
            return;
        indice--;
        caracteresRestantes++;
        if (codigoFonte[indice] == '\n') {
            linha--;
            coluna = colunaAnterior;
        }
    }

    void limpaVariaveis() {
        estadoAtual = estadoInicial;
        lexema = "";
    }

    void inicializaMaquinaDeEstados() {

        Estado q0 = new Estado("q0", true, false, false, Classe.error, Tipo.NULO);
        estadoInicial = q0;

        Estado EOF = new Estado("EOF", false, true, false, Classe.$, Tipo.NULO);
        Estado INT1 = new Estado("INT1", false, true, true, Classe.num, Tipo.INTEIRO);
        Estado q1 = new Estado("q1", false, false, false, Classe.error, Tipo.NULO);
        Estado q2 = new Estado("q2", false, false, false, Classe.error, Tipo.NULO);
        Estado q3 = new Estado("q3", false, false, false, Classe.error, Tipo.NULO);
        Estado REAL1 = new Estado("REAL1", false, true, true, Classe.num, Tipo.REAL);
        Estado INT2 = new Estado("INT2", false, true, false, Classe.num, Tipo.INTEIRO);
        Estado q4 = new Estado("q4", false, false, false, Classe.error, Tipo.NULO);
        Estado q5 = new Estado("q5", false, false, false, Classe.error, Tipo.NULO);
        Estado REAL2 = new Estado("REAL2", false, true, false, Classe.num, Tipo.REAL);
        Estado q6 = new Estado("q6", false, false, false, Classe.error, Tipo.NULO);
        Estado LIT = new Estado("LIT", false, true, false, Classe.lit, Tipo.LITERAL);
        Estado OPM = new Estado("OPM", false, true, false, Classe.opm, Tipo.NULO);
        Estado ID = new Estado("ID", false, true, false, Classe.id, Tipo.NULO);
        Estado q7 = new Estado("q7", false, false, false, Classe.error, Tipo.NULO);
        Estado OPR1 = new Estado("OPR1", false, true, true, Classe.opr, Tipo.NULO);
        Estado OPR2 = new Estado("OPR2", false, true, false, Classe.opr, Tipo.NULO);
        Estado OPR3 = new Estado("OPR3", false, true, false, Classe.opr, Tipo.NULO);
        Estado ATR = new Estado("ATR", false, true, false, Classe.atr, Tipo.NULO);
        Estado OPR4 = new Estado("OPR4", false, true, true, Classe.opr, Tipo.NULO);
        Estado OPR5 = new Estado("OPR5", false, true, false, Classe.opr, Tipo.NULO);
        Estado OPR6 = new Estado("OPR6", false, true, false, Classe.opr, Tipo.NULO);
        Estado PT_V = new Estado("PT_V", false, true, false, Classe.pt_v, Tipo.NULO);
        Estado VIR = new Estado("VIR", false, true, false, Classe.vir, Tipo.NULO);
        Estado AB_P = new Estado("AB_P", false, true, false, Classe.ab_p, Tipo.NULO);
        Estado FC_P = new Estado("FC_P", false, true, false, Classe.fc_p, Tipo.NULO);

        q0.addTransicao(Alfabeto.espaco, q0, Acao.IGNORAR);
        q0.addTransicao(Alfabeto.tab, q0, Acao.IGNORAR);
        q0.addTransicao(Alfabeto.nova_linha, q0, Acao.IGNORAR);
        q0.addTransicao(Alfabeto.retorno_carro, q0, Acao.IGNORAR);
        q0.addTransicao(Alfabeto.EOF, EOF, Acao.IGNORAR);

        q0.addTransicao(Alfabeto.aspa_dupla, q6, Acao.ADICIONAR_AO_LEXEMA);

        q0.addTransicao(Alfabeto.barra, OPM, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.mais, OPM, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.menos, OPM, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.asterisco, OPM, Acao.ADICIONAR_AO_LEXEMA);

        q0.addTransicao(Alfabeto.ab_chave, q7, Acao.IGNORAR);

        q0.addTransicao(Alfabeto.menor, OPR1, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.maior, OPR4, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.igual, OPR6, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.pt_v, PT_V, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.virgula, VIR, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.ab_p, AB_P, Acao.ADICIONAR_AO_LEXEMA);
        q0.addTransicao(Alfabeto.fc_p, FC_P, Acao.ADICIONAR_AO_LEXEMA);

        INT1.addTransicao(Alfabeto.E, q2, Acao.ADICIONAR_AO_LEXEMA);
        INT1.addTransicao(Alfabeto.e, q2, Acao.ADICIONAR_AO_LEXEMA);
        INT1.addTransicao(Alfabeto.ponto, q1, Acao.ADICIONAR_AO_LEXEMA);

        q2.addTransicao(Alfabeto.mais, q3, Acao.ADICIONAR_AO_LEXEMA);
        q2.addTransicao(Alfabeto.menos, q3, Acao.ADICIONAR_AO_LEXEMA);

        REAL1.addTransicao(Alfabeto.E, q4, Acao.ADICIONAR_AO_LEXEMA);
        REAL1.addTransicao(Alfabeto.e, q4, Acao.ADICIONAR_AO_LEXEMA);

        q4.addTransicao(Alfabeto.mais, q5, Acao.ADICIONAR_AO_LEXEMA);
        q4.addTransicao(Alfabeto.menos, q5, Acao.ADICIONAR_AO_LEXEMA);

        ID.addTransicao(Alfabeto.underline, ID, Acao.ADICIONAR_AO_LEXEMA);

        OPR1.addTransicao(Alfabeto.igual, OPR3, Acao.ADICIONAR_AO_LEXEMA);
        OPR1.addTransicao(Alfabeto.maior, OPR2, Acao.ADICIONAR_AO_LEXEMA);
        OPR1.addTransicao(Alfabeto.menos, ATR, Acao.ADICIONAR_AO_LEXEMA);

        OPR4.addTransicao(Alfabeto.igual, OPR5, Acao.ADICIONAR_AO_LEXEMA);

        // Ignorar o comentário - Vide automato COM CORRECAO DO COMENTARIO na pasta de
        // definiçoes
        q7.addTransicao(Alfabeto.fc_chave, q0, Acao.IGNORAR);

        for (Simbolo s : Alfabeto.simbolos) {

            if (s.isNumero) {
                q0.addTransicao(s, INT1, Acao.ADICIONAR_AO_LEXEMA);
                INT1.addTransicao(s, INT1, Acao.ADICIONAR_AO_LEXEMA);
                q1.addTransicao(s, REAL1, Acao.ADICIONAR_AO_LEXEMA);
                q2.addTransicao(s, INT2, Acao.ADICIONAR_AO_LEXEMA);
                q3.addTransicao(s, INT2, Acao.ADICIONAR_AO_LEXEMA);
                INT2.addTransicao(s, INT2, Acao.ADICIONAR_AO_LEXEMA);

                REAL1.addTransicao(s, REAL1, Acao.ADICIONAR_AO_LEXEMA);
                q4.addTransicao(s, REAL2, Acao.ADICIONAR_AO_LEXEMA);
                q5.addTransicao(s, REAL2, Acao.ADICIONAR_AO_LEXEMA);
                REAL2.addTransicao(s, REAL2, Acao.ADICIONAR_AO_LEXEMA);

                ID.addTransicao(s, ID, Acao.ADICIONAR_AO_LEXEMA);
            }

            if (s.isLetra) {
                q0.addTransicao(s, ID, Acao.ADICIONAR_AO_LEXEMA);
                ID.addTransicao(s, ID, Acao.ADICIONAR_AO_LEXEMA);
            }

            if (!s.equals(Alfabeto.aspa_dupla)) {
                q6.addTransicao(s, q6, Acao.ADICIONAR_AO_LEXEMA);
            } else {
                q6.addTransicao(s, LIT, Acao.ADICIONAR_AO_LEXEMA);
            }

            if (!s.equals(Alfabeto.fc_chave) && !s.equals(Alfabeto.ab_chave)) {
                q7.addTransicao(s, q7, Acao.IGNORAR);
            }
        }

    }

    void inicializaTabelaDeSimbolos() {
        tabelaDeSimbolos.add(new Token(Classe.inicio, Classe.inicio.toString(), Tipo.inicio));
        tabelaDeSimbolos.add(new Token(Classe.varinicio, Classe.varinicio.toString(), Tipo.varinicio));
        tabelaDeSimbolos.add(new Token(Classe.varfim, Classe.varfim.toString(), Tipo.varfim));
        tabelaDeSimbolos.add(new Token(Classe.escreva, Classe.escreva.toString(), Tipo.escreva));
        tabelaDeSimbolos.add(new Token(Classe.leia, Classe.leia.toString(), Tipo.leia));
        tabelaDeSimbolos.add(new Token(Classe.se, Classe.se.toString(), Tipo.se));
        tabelaDeSimbolos.add(new Token(Classe.entao, Classe.entao.toString(), Tipo.entao));
        tabelaDeSimbolos.add(new Token(Classe.fimse, Classe.fimse.toString(), Tipo.fimse));
        tabelaDeSimbolos.add(new Token(Classe.repita, Classe.repita.toString(), Tipo.repita));
        tabelaDeSimbolos.add(new Token(Classe.fimrepita, Classe.fimrepita.toString(), Tipo.fimrepita));
        tabelaDeSimbolos.add(new Token(Classe.fim, Classe.fim.toString(), Tipo.fim));
        tabelaDeSimbolos.add(new Token(Classe.inteiro, Classe.inteiro.toString(), Tipo.inteiro));
        tabelaDeSimbolos.add(new Token(Classe.lit, Classe.lit.toString(), Tipo.literal));
        tabelaDeSimbolos.add(new Token(Classe.real, Classe.real.toString(), Tipo.real));
        tabelaDeSimbolos.add(new Token(Classe.literal, Classe.literal.toString(), Tipo.literal));

    }
}
