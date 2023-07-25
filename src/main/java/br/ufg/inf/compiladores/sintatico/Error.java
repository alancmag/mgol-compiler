package br.ufg.inf.compiladores.sintatico;

public class Error extends Action {
    public Error(int erro ){
        super(erro);
    }

    @Override
    public String toString() {
         return "ERRO - Numero erro: " + numero;
    }
}
