package br.ufg.inf.compiladores.sintatico;

public class Shift extends Action {
    public Shift(int estadoShift){
        super(estadoShift);
    }
        
    @Override
    public String toString() {
        return "Shift estado: " + numero;
    }
    
    public Integer getEstadoShift(){
        return Integer.valueOf(numero);
    }
}
