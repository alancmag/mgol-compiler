package br.ufg.inf.compiladores.lexico;

public enum Tipo {
    INTEIRO("INTEIRO"),
    REAL("REAL"),
    LITERAL("LITERAL"),
    NULO("NULO"),
    NUM("NUM"),
    ID("ID"), 
    OPR("OPR"), 
    RCB("RCB"), 
    OPM("OPM"), 
    AB_P("AB_P"), 
    FC_P("FC_P"), 
    PT_V("PT_V"), 
    VIR("VIR"), 
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

    Tipo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao ;
    }
}
