#include<iostream>


#include"lista.h"
#include"vreme.h"
#include"greska.h"
#include"film.h"
#include"domaci_film.h"
#include"strani_film.h"

using namespace std;


int main() {

	typedef Lista<int> Klasa1;

	//Klasa1 obj1;
	int a = 2;
	try {
		Klasa1 l1;
		//l1 += a;
		l1[-1] = 4;
		cout << l1[0] << endl;
	}
	catch (PozicijaIzvanOpsega e) {
		cout << e.what()<<endl;
	}

	Vreme v1(14, 55), v2(9, 6);
	Vreme v3 = v1 + v2;
	cout <<endl<< v3<<endl;

	//################################################
	
	DomaciFilm f1(v3, "DFilm1");
	f1.oceni(3);
	f1.oceni(7);
	f1.oceni(6);
	//StraniFilm()
	StraniFilm f2(v1, "SFilm1", "Nemacka", 0);
	f2.oceni(10);
	f2.oceni(5);
	f2.oceni(0);
	Film** filmovi = new Film*[2];
	filmovi[0] = &f1;
	filmovi[1] = &f2;



	cout << "FILMOVI:" << endl << *filmovi[0] << endl << *filmovi[1] << endl;
	
}