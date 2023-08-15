inicio
varinicio
inteiro num,i,antepNum,antepNum2,antepNum3, penNum,penNum2,  penNum3, numAtual,numAtual2,numAtual3;
literal teste;
varfim;
antepNum <- 0;
penNum <- 1;
escreva "Obter sequencia de Fibonacci\n";
escreva "Entre com um numero: ";
leia num;
escreva "\nMostrar a sequencia completa:\n";

i<-0;
repita(i<=num)
    numAtual <- antepNum + penNum;
    antepNum <- penNum;
    penNum <- numAtual;
    escreva numAtual;
    escreva ",";
    i<-i+1;
fimrepita
escreva "\nFINALIZOU";

fim