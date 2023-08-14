inicio
varinicio
literal A;
literal B,C;
inteiro D;
inteiro E,F;
real G;
real H,TESTE;
varfim;
escreva "Digite A: ";
leia A;
escreva "Digite D: ";
leia D;
escreva "Digite G: ";
leia G;
se(D>2)
entao
se(D<=22)
entao
escreva "D esta entre 2 e 22\n";
fimse
fimse
escreva "valor de G=";
escreva G;
escreva "\n";
repita (G>0.1)
escreva "\nG=";
escreva G;
escreva "\n";
se(G>3.0)
entao
se(G<=9.0)
entao
escreva "G esta entre 3.0 e 9.0\n";
fimse
fimse
G<-G-1.0;
fimrepita
G<-10.0;
repita (G>0.1)
escreva "DIGITE UM NUMERO REAL MENOR QUE 0.1 PARA SAIR DO LOOP\n";
leia G;
se(G>0.1) entao
escreva "Voce digitou um numero MAIOR que o ESPERADO\n";
fimse
G<-G-1.5;
fimrepita
escreva "FINALIZOU\n";
fim