#include<stdio.h>
typedef char literal[256];
void main(void)
{/*----Variaveis temporarias----*/
int T0;
int T1;
double T2;
double T3;
double T4;
double T5;
double T6;
double T7;
double T8;
int T9;
/*------------------------------*/
literal A;
literal B,C;
int D;
int E,F;
double G;
double H,TESTE;


printf ("\nDigite A: ");
scanf ("%s",A);
printf ("%s",A);
printf ("\nDigite D: ");
scanf ("%d",&D);
printf ("%d",D);
printf ("\nDigite G: ");
scanf ("%lf",&G);
printf ("%lf",G);
T0 = D>2;
if (T0) {
	T1 = D<=22;
	if (T1) {
		printf ("\nD esta entre 2 e 22\n");
	}
}
printf ("\nvalor de G=");
printf ("%lf",G);
printf ("\n");
T2 = G>0.1;
while (T2) {
	printf ("\nG=");
	printf ("%lf",G);
	printf ("\n");
	T3 = G>3.0;
	if (T3) {
		T4 = G<=9.0;
		if (T4) {
			printf ("\nG esta entre 3.0 e 9.0\n");
		}
	}
	T5 = G-1.0;
	G=T5;
	T2 = G>0.1;
}
G=10.0;
T6 = G>0.1;
while (T6) {
	printf ("\nDIGITE UM NUMERO REAL MENOR QUE 0.1 PARA SAIR DO LOOP\n");
	scanf ("%lf",&G);
	T7 = G>0.1;
	if (T7) {
		printf ("\nVoce digitou um numero MAIOR que o ESPERADO\n");
	}
	T8 = G-1.5;
	G=T8;
	T6 = G>0.1;
}
T9 = D==D;
if (T9) {
	printf ("\nD=D\n");
}
printf ("\nFINALIZOU\n");
}
