package br.ufg.inf.compiladores.sintatico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import br.ufg.inf.compiladores.lexico.Classe;

public class Gramatica {

    public static List<Regra> getListRegrasGramatica(String pathArquivoGramatica, Charset encoding) throws IOException {
        List<Regra> regras = new ArrayList<>();
        List<String> linhasArquivo = Files.readAllLines(Paths.get(pathArquivoGramatica));
        for (String linha : linhasArquivo) {
            linha = linha.trim().replace("\t", " ").replace("  ", " ");
            List<String> producoes = new ArrayList<>(Arrays.asList(linha.split(" "))); 
            String ladoEsquerdo = producoes.remove(0); 
            producoes.remove(0); // remove o -> 
            Regra regra = new Regra();
            regra.setLadoEsquerdo(ladoEsquerdo);
            regra.setLadoDireito(producoes); //sobrou apenas as produçoes do lado direito
            regras.add(regra);
            //System.out.println(regra);
        }
        return regras;
    }

    public static Table<Integer,String,Action> getTabelaActionGoto(String pathArquivoTabelaActionGoto, Charset encoding) throws IOException {
        Table<Integer,String,Action> tabela =  HashBasedTable.create();
        List<String> linhasArquivo = Files.readAllLines(Paths.get(pathArquivoTabelaActionGoto));
        String linhaCabecalho = linhasArquivo.remove(0);
        List<String> listaCabecalho = Arrays.asList(linhaCabecalho.split(","));

        int numeroEstado = 0;
        for (String linha : linhasArquivo) {
            List<String> listaAcoes = Arrays.asList(linha.split(",",-1));
            for (int i = 0; i < listaCabecalho.size(); i++ ) {
                String acaoString = listaAcoes.get(i);
                String simbolo = listaCabecalho.get(i);
                Action action;
                if ( Classe.isTerminal(simbolo) ){
                    //Simbolo Terminal
                    switch (acaoString.charAt(0)) {
                        case 's':
                            action = new Shift(Integer.parseInt(acaoString.substring(1, acaoString.length())));
                            break;
                        case 'r':
                            action = new Reduce(Integer.parseInt(acaoString.substring(1, acaoString.length())));
                            break;
                        case 'a':
                            action = new Accept();
                            break;
                        default:
                            action = new Error();
                    }

                }
                else {
                    // Simbolo Não terminal
                    switch (acaoString.charAt(0)) {
                        case 'E':
                            action = new Error();
                            break;
                        default:
                            action = new Goto(Integer.parseInt(acaoString));
                    }
                    

                }
                //System.out.println(action);
                tabela.put(Integer.valueOf(numeroEstado), simbolo , action);
            }
            numeroEstado++;
        }
        return tabela;
    }

}