package br.ufg.inf.compiladores.sintatico;

public class Reduce extends Action {
    public Reduce(int numeroRegraReducao) {
        super( numeroRegraReducao);
    }
    
    @Override
    public String toString() {
        return "Reduce Regra: " + numero;
    }

    public Integer getNumeroRegraReducao(){
        return Integer.valueOf(numero);
    }
}
