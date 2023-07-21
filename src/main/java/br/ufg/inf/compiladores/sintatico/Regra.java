package br.ufg.inf.compiladores.sintatico;

import java.util.ArrayList;
import java.util.List;

public class Regra {
    private static int count = 1;
    int numero;
    String ladoEsquerdo;
    List<String> ladoDireito = new ArrayList<String>();


    public Regra(){
        numero = count++;
    }

    public Integer getNumero() {
        return Integer.valueOf(numero);
    }

    public String getLadoEsquerdo() {
        return ladoEsquerdo;
    }

    public void setLadoEsquerdo(String ladoDireito) {
        this.ladoEsquerdo = ladoDireito;
    }

    public List<String> getLadoDireito() {
        return ladoDireito;
    }

    public void setLadoDireito(List<String> ladoEsquerdo) {
        this.ladoDireito = ladoEsquerdo;
    }

    public int getQtdLadoDireito(){
        return ladoDireito.size();
    }

    @Override
    public String toString() {
        return "Regra [numero regra="+numero+" ,ladoEsquerdo=" + ladoEsquerdo + ", ladoDireito=" + ladoDireito + "]";
    }


    
}
