package br.ufg.inf.compiladores.lexico;

public enum Tipo {
    NULO("NULO"),
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

    /*/Nao terminais da linguagem
    ARG("ARG"),
    LD("LD"),
    OPRD("OPRD"),
    TIPO("TIPO"),
    P("P"),
    V("V"),
    LV("LV"),
    D("D"),
    L ("L"),
    A("A"),
    ES("ES"),
    CMD("CMD"),
    COND ("COND"),
    CAB("CAB"),
    EXP_R("EXP_R"),
    CP("CP"),
    R("R"),
    CABR ("CABR"),
    CPR("CPR")
    ;*/
    private String descricao;

    Tipo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao ;
    }
}
