package br.ufg.inf.compiladores.sintatico;

public class Goto extends Action {
    
    public Goto(int numeroEstadoGoto){
        super(numeroEstadoGoto);
    }
        
    @Override
    public String toString() {
        return "Goto estado: " + numero;
    }

    public Integer getEstadoGoto(){
        return Integer.valueOf(numero);
    }
}
