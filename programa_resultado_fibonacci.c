#include<stdio.h>
typedef char literal[256];
void main(void)
{/*----Variaveis temporarias----*/
int T0;
int T1;
int T2;
/*------------------------------*/
int num,i,antepNum,antepNum2,antepNum3,penNum,penNum2,penNum3,numAtual,numAtual2,numAtual3;
literal teste;


antepNum=0;
penNum=1;
printf ("Obter sequencia de Fibonacci\n");
printf ("Entre com um numero: ");
scanf ("%d",&num);
printf ("\nMostrar a sequencia completa:\n");
i=0;
T0 = i<=num;
while (T0) {
	T1 = antepNum+penNum;
	numAtual=T1;
	penNum=penNum;
	antepNum=numAtual;
	printf ("%d",numAtual);
	printf (",");
	T2 = i+1;
	i=T2;
	T0 = i<=num;
}
printf ("\nFINALIZOU");
}
