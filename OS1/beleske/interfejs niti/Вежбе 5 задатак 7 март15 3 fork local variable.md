U nekom sistemu sistemski poziv fork() kreira nit – klon pozivajuće niti, sa iskopiranim celokupnim stekom, slično istoimenom sistemskom pozivu na sistemu Unix (osim što se ovde radi o nitima, a ne procesima). 
Dole je dat program čija je zamisao da izvršava dve uporedne niti, jednu koja učitava znak po znak sa stadardnog ulaza i taj znak prenosi kroz promenljivu c drugoj niti, koja taj primljeni znak ispisuje na standardni izlaz, i tako neograničeno.

1. Precizno objasniti problem koji ovaj program ima i ispraviti taj problem. 
2. Prepraviti samo funkciju pipe() tako da se umesto jednog para niti koje vrše razmenu znakova. formiraju dva para takvih niti; svaki par niti predstavlja odvojeni „tok“. pa je potrebno definisati dva para promenljivih c i flag (npr. c1, c2, flag1 i flag2).
```c++
#include <iostream>
void writer (char* c, int* flag) {
	while (1) {
		while (*flag==1);
		cin>>(*c);
		*flag = 1;
		}
} 
void reader (char* c, int* flag) {
	while (1) {
		while (*flag==0);//upposleno cekaju dok ne dodje red na reader
		cout<<(*c);
		*flag = 0;
		}
}
void pipe () {
	char c;
	int flag = 0;//c i flag su lokalne prom u fji pipe, tj one su na steku, a fork ce da kopira ceo stek(pise eksolicitno u tekstu zad) tako da ce i dete i roditelj imati svoj i c i flag,
//resicemo problem tako sto ce c i flag biti globalne
	if (fork())
		writer(&c,&flag);//roditelj
	else reader(&c,&flag);//dete
}
void main () { pipe(); }
```
b) static char c1, c2
static int flag1, flag2
i 3 forka!!!