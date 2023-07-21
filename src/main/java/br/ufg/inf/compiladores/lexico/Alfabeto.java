package br.ufg.inf.compiladores.lexico;

import java.util.ArrayList;
import java.util.List;

public final class Alfabeto {

    static Simbolo a = new Simbolo('a', true,true,false,false);
    static Simbolo b = new Simbolo('b', true,true,false,false);
    static Simbolo c = new Simbolo('c', true,true,false,false);
    static Simbolo d = new Simbolo('d', true,true,false,false);
    static Simbolo e = new Simbolo('e', true,true,false,false);
    static Simbolo f = new Simbolo('f', true,true,false,false);
    static Simbolo g = new Simbolo('g', true,true,false,false);
    static Simbolo h = new Simbolo('h', true,true,false,false);
    static Simbolo i = new Simbolo('i', true,true,false,false);
    static Simbolo j = new Simbolo('j', true,true,false,false);
    static Simbolo k = new Simbolo('k', true,true,false,false);
    static Simbolo l = new Simbolo('l', true,true,false,false);
    static Simbolo m = new Simbolo('m', true,true,false,false);
    static Simbolo n = new Simbolo('n', true,true,false,false);
    static Simbolo o = new Simbolo('o', true,true,false,false);
    static Simbolo p = new Simbolo('p', true,true,false,false);
    static Simbolo q = new Simbolo('q', true,true,false,false);
    static Simbolo r = new Simbolo('r', true,true,false,false);
    static Simbolo s = new Simbolo('s', true,true,false,false);
    static Simbolo t = new Simbolo('t', true,true,false,false);
    static Simbolo u = new Simbolo('u', true,true,false,false);
    static Simbolo v = new Simbolo('v', true,true,false,false);
    static Simbolo w = new Simbolo('w', true,true,false,false);
    static Simbolo x = new Simbolo('x', true,true,false,false);
    static Simbolo y = new Simbolo('y', true,true,false,false);
    static Simbolo z = new Simbolo('z', true,true,false,false);
    static Simbolo A = new Simbolo('A', true,true,false,false);
    static Simbolo B = new Simbolo('B', true,true,false,false);
    static Simbolo C = new Simbolo('C', true,true,false,false);
    static Simbolo D = new Simbolo('D', true,true,false,false);
    static Simbolo E = new Simbolo('E', true,true,false,false);
    static Simbolo F = new Simbolo('F', true,true,false,false);
    static Simbolo G = new Simbolo('G', true,true,false,false);
    static Simbolo H = new Simbolo('H', true,true,false,false);
    static Simbolo I = new Simbolo('I', true,true,false,false);
    static Simbolo J = new Simbolo('J', true,true,false,false);
    static Simbolo K = new Simbolo('K', true,true,false,false);
    static Simbolo L = new Simbolo('L', true,true,false,false);
    static Simbolo M = new Simbolo('M', true,true,false,false);
    static Simbolo N = new Simbolo('N', true,true,false,false);
    static Simbolo O = new Simbolo('O', true,true,false,false);
    static Simbolo P = new Simbolo('P', true,true,false,false);
    static Simbolo Q = new Simbolo('Q', true,true,false,false);
    static Simbolo R = new Simbolo('R', true,true,false,false);
    static Simbolo S = new Simbolo('S', true,true,false,false);
    static Simbolo T = new Simbolo('T', true,true,false,false);
    static Simbolo U = new Simbolo('U', true,true,false,false);
    static Simbolo V = new Simbolo('V', true,true,false,false);
    static Simbolo W = new Simbolo('W', true,true,false,false);
    static Simbolo X = new Simbolo('X', true,true,false,false);
    static Simbolo Y = new Simbolo('Y', true,true,false,false);
    static Simbolo Z = new Simbolo('Z', true,true,false,false);
    static Simbolo zero = new Simbolo('0', true,false,true,false);
    static Simbolo um = new Simbolo('1', true,false,true,false);
    static Simbolo dois = new Simbolo('2', true,false,true,false);
    static Simbolo tres = new Simbolo('3', true,false,true,false);
    static Simbolo quatro = new Simbolo('4', true,false,true,false);
    static Simbolo cinco = new Simbolo('5', true,false,true,false);
    static Simbolo seis = new Simbolo('6', true,false,true,false);
    static Simbolo sete = new Simbolo('7', true,false,true,false);
    static Simbolo oito = new Simbolo('8', true,false,true,false);
    static Simbolo nove = new Simbolo('9', true,false,true,false);
    static Simbolo virgula = new Simbolo(',', true,false,false,false);
    static Simbolo pt_v = new Simbolo(';', true,false,false,false);
    static Simbolo dois_pt = new Simbolo(':', true,false,false,false);
    static Simbolo ponto = new Simbolo('.', true,false,false,false);
    static Simbolo exclamacao = new Simbolo('!', true,false,false,false);
    static Simbolo interrogacao = new Simbolo('?', true,false,false,false);
    static Simbolo barra_invertida = new Simbolo('\\', true,false,false,false);
    static Simbolo asterisco = new Simbolo('*', true,false,false,false);
    static Simbolo mais = new Simbolo('+', true,false,false,false);
    static Simbolo menos = new Simbolo('-', true,false,false,false);
    static Simbolo barra = new Simbolo('/', true,false,false,false);
    static Simbolo ab_p = new Simbolo('(', true,false,false,false);
    static Simbolo fc_p = new Simbolo(')', true,false,false,false);
    static Simbolo ab_chave = new Simbolo('{', true,false,false,false);
    static Simbolo fc_chave = new Simbolo('}', true,false,false,false);
    static Simbolo ab_colchete = new Simbolo('[', true,false,false,false);
    static Simbolo fc_colchete = new Simbolo(']', true,false,false,false);
    static Simbolo menor = new Simbolo('<', true,false,false,false);
    static Simbolo maior = new Simbolo('>', true,false,false,false);
    static Simbolo igual = new Simbolo('=', true,false,false,false);
    static Simbolo aspa_simples = new Simbolo('\'', true,false,false,false);
    static Simbolo aspa_dupla = new Simbolo('"', true,false,false,false);
    static Simbolo underline = new Simbolo('_', true,false,false,false);
    static Simbolo espaco = new Simbolo(' ', true,false,false,false);
    static Simbolo tab = new Simbolo('\t', true,false,false,false);
    static Simbolo nova_linha = new Simbolo('\n', true,false,false,false);
    static Simbolo retorno_carro = new Simbolo('\r', true,false,false,false);
    static Simbolo EOF = new Simbolo('$', false,false,false,true);

    static List<Simbolo> simbolos = new ArrayList<>();

    static  {
            simbolos.add(a);
            simbolos.add(b);
            simbolos.add(c);
            simbolos.add(d);
            simbolos.add(e);
            simbolos.add(f);
            simbolos.add(g);
            simbolos.add(h);
            simbolos.add(i);
            simbolos.add(j);
            simbolos.add(k);
            simbolos.add(l);
            simbolos.add(m);
            simbolos.add(n);
            simbolos.add(o);
            simbolos.add(p);
            simbolos.add(q);
            simbolos.add(r);
            simbolos.add(s);
            simbolos.add(t);
            simbolos.add(u);
            simbolos.add(v);
            simbolos.add(w);
            simbolos.add(x);
            simbolos.add(y);
            simbolos.add(z);
            simbolos.add(A);
            simbolos.add(B);
            simbolos.add(C);
            simbolos.add(D);
            simbolos.add(E);
            simbolos.add(F);
            simbolos.add(G);
            simbolos.add(H);
            simbolos.add(I);
            simbolos.add(J);
            simbolos.add(K);
            simbolos.add(L);
            simbolos.add(M);
            simbolos.add(N);
            simbolos.add(O);
            simbolos.add(P);
            simbolos.add(Q);
            simbolos.add(R);
            simbolos.add(S);
            simbolos.add(T);
            simbolos.add(U);
            simbolos.add(V);
            simbolos.add(W);
            simbolos.add(X);
            simbolos.add(Y);
            simbolos.add(Z);
            simbolos.add(zero);
            simbolos.add(um);
            simbolos.add(dois);
            simbolos.add(tres);
            simbolos.add(quatro);
            simbolos.add(cinco);
            simbolos.add(seis);
            simbolos.add(sete);
            simbolos.add(oito);
            simbolos.add(nove);
            simbolos.add(virgula);
            simbolos.add(pt_v);
            simbolos.add(dois_pt);
            simbolos.add(ponto);
            simbolos.add(exclamacao);
            simbolos.add(interrogacao);
            simbolos.add(barra_invertida);
            simbolos.add(asterisco);
            simbolos.add(mais);
            simbolos.add(menos);
            simbolos.add(barra);
            simbolos.add(ab_p);
            simbolos.add(fc_p);
            simbolos.add(ab_chave);
            simbolos.add(fc_chave);
            simbolos.add(ab_colchete);
            simbolos.add(fc_colchete);
            simbolos.add(menor);
            simbolos.add(maior);
            simbolos.add(igual);
            simbolos.add(aspa_simples);
            simbolos.add(aspa_dupla);
            simbolos.add(underline);
            simbolos.add(espaco);
            simbolos.add(tab);
            simbolos.add(nova_linha);
            simbolos.add(retorno_carro);

    }

    static boolean pertenceAlfabeto(Simbolo s){
        return simbolos.contains(s);
    }

    static boolean pertenceAlfabeto(char caractere){
        return simbolos.stream().anyMatch(simbolo -> simbolo.caractere == caractere);
    }

    static Simbolo getSimbolo(char caractere){
        return simbolos.get(simbolos.indexOf(new Simbolo(caractere, false, false, false,false)));
    }

}
