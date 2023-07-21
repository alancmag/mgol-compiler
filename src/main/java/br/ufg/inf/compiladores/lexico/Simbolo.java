package br.ufg.inf.compiladores.lexico;

public class Simbolo {
    char caractere;
    boolean isParteAlfabeto;
    boolean isLetra;
    boolean isNumero;
    boolean isFinalArquivo;
    public Simbolo(char caractere, boolean isParteAlfabeto, boolean isLetra, boolean isNumero,boolean isFinalArquivo) {
        this.caractere = caractere;
        this.isParteAlfabeto = isParteAlfabeto;
        this.isLetra = isLetra;
        this.isNumero = isNumero;
        this.isFinalArquivo = isFinalArquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simbolo simbolo = (Simbolo) o;
        return caractere == simbolo.caractere;
    }

    @Override
    public String toString() {
        return Character.toString(caractere);
    }
}
