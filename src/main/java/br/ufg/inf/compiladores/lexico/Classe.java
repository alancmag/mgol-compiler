package br.ufg.inf.compiladores.lexico;

public enum Classe {

    num("num"),
    id("id"),
    lit("lit"),
    $("$"),
    opr("opr"),
    atr("atr"),
    opm("opm"),
    ab_p("ab_p"),
    fc_p("fc_p"),
    pt_v("pt_v"),
    error("error"),
    vir("vir"),
    inicio("inicio"),
    varinicio("varinicio"),
    varfim("varfim"),
    escreva("escreva"),
    leia("leia"),
    se("se"),
    entao("entao"),
    fimse("fimse"),
    repita("repita"),
    fimrepita("fimrepita"),
    fim("fim"),
    inteiro("inteiro"),
    literal("literal"),
    real("real");

    private String descricao;

    Classe(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public static boolean isTerminal(String simbolo) {
        for (Classe classe : Classe.values()) {
            if(classe.descricao.equals(simbolo)){
                return true;
            }
        }
        return false; 
    }
}
