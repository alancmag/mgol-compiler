#include<stdio.h>
typedef char literal[256];
void main(void)
{/*----Variaveis temporarias----*/
double T0;
int T1;
int T2;
double T3;
double T4;
double T5;
double T6;
double T7;
double T8;
double T9;
int T10;
/*------------------------------*/
literal A;
literal B,C;
int D;
int E,F;
double G;
double H,TESTE;


T0 = G-1.0;
G=T0;
printf ("\nDigite Literal A: ");
scanf ("%s",A);
printf ("%s",A);
printf ("\nDigite Inteiro D: ");
scanf ("%d",&D);
printf ("%d",D);
printf ("\nDigite Real G: ");
scanf ("%lf",&G);
printf ("%lf",G);
T1 = D>2;
if (T1) {
	T2 = D<=22;
	if (T2) {
		printf ("\nD esta entre 2 e 22\n");
	}
}
printf ("\nvalor de G=");
printf ("%lf",G);
printf ("\n");
T3 = G>0.1;
while (T3) {
	printf ("\nG=");
	printf ("%lf",G);
	printf ("\n");
	T4 = G>3.0;
	if (T4) {
		T5 = G<=9.0;
		if (T5) {
			printf ("\nG esta entre 3.0 e 9.0\n");
		}
	}
	T6 = G-1.0;
	G=T6;
	T3 = G>0.1;
}
G=10.0;
T7 = G>0.1;
while (T7) {
	printf ("\nDIGITE UM NUMERO REAL MENOR QUE 0.1 PARA SAIR DO LOOP\n");
	scanf ("%lf",&G);
	T8 = G>0.1;
	if (T8) {
		printf ("\nVoce digitou um numero MAIOR que o ESPERADO para sair\n");
	}
	T9 = G-1.5;
	G=T9;
	T7 = G>0.1;
}
T10 = D==D;
if (T10) {
	printf ("\nD=D\n");
}
printf ("\nFINALIZOU\n");
}
