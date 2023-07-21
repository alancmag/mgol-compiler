package br.ufg.inf.compiladores.lexico;

import java.util.Objects;

public class Token {
    public Classe classe;
    public String lexema = "";
    public Tipo tipo;

    public Token() {
    }

    public Token(Classe classe, String lexema, Tipo tipo) {
        this.classe = classe;
        this.lexema = lexema;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return  "Classe: " + classe +
                ", Lexema: " + lexema +
                ", Tipo: " + tipo ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(lexema, token.lexema);
    }


}
