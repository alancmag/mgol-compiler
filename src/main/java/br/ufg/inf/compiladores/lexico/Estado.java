package br.ufg.inf.compiladores.lexico;

import java.util.HashMap;
import java.util.Map;

public class Estado
{
    boolean isFinal;
    boolean isInicial;
    boolean isPrefixoOutroEstadoFinal;
    String nome;
    Classe classeTokens;
    Tipo tipoTokens;
    private final Map<Simbolo, Estado> transicoes = new HashMap<>();

    private final Map<Simbolo, Acao> acoes = new HashMap<>();
    public Estado(String nome,boolean isInicial,boolean isFinal,boolean isPrefixoOutroEstadoFinal ,Classe classeTokens, Tipo tipoTokens) {
        this.nome = nome;
        this.isFinal = isFinal;
        this.isInicial = isInicial;
        this.isPrefixoOutroEstadoFinal = isPrefixoOutroEstadoFinal;
        this.classeTokens = classeTokens;
        this.tipoTokens = tipoTokens;
    }

    void addTransicao (Simbolo simbolo, Estado estado, Acao acao){
        transicoes.put(simbolo,estado);
        acoes.put(simbolo,acao);
    }

    public boolean hasTransicao(Simbolo simbolo){
        return transicoes.containsKey(simbolo);
    }

    public Estado nextEstado(Simbolo simbolo) {
        return transicoes.get(simbolo);
    }

    public boolean hasAcao(Simbolo simbolo){
        return acoes.containsKey(simbolo);
    }

    public Acao getAcao(Simbolo simbolo){
        return acoes.get(simbolo);
    }

 }