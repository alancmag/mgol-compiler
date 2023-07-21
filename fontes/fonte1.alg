"d"@
inicio!@#$%¨&*()
{numeros inteiros e reais}@
10
22e30
33E44
55.0e66
66.1E77

22e+30
33E+44
55.0e+66
66.1E+77

22e-30
33E-44
55.0e-66
66.1E-77

22e--30
33E-+44
55.0e-+66
66.1E--77

{teste     !@#$%¨&*()   comentario}
{teste ,.;/!@#$%¨&*()\ comentario-+=<>:}
    varinicio
        literal A,B;
        inteiro B;
        inteiro D;
        real C ;
        {teste comentario2}
        10e
    varfim;
    escreva "@#Digite B:";
    leia B;
    escreva "Digite A:";
    leia A;
    se(B>2)
    entao
        se(B<=4)
        entao
            escreva "B esta entre 2 e 4";
        fimse
    fimse
    B<-B+1;
    B<-B+2;
    B<-B+3;
    D<-B;
    C<-5.0;
    repita (B<5)
        C<-B+2;
        escreva C;
        B<-B+1;
    fimrepita
    escreva "\nB=\n";
    escreva D;
    escreva "\n";
    escreva C;
    escreva "\n";
    escreva A;
fim