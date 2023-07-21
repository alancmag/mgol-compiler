package br.ufg.inf.compiladores.sintatico;

public class Accept extends Action {
    
    public Accept(){
        super(-1);
    }

    @Override
    public String toString() {
         return "FONTE ACEITO!";
    }
}
