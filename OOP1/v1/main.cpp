#include<iostream>
#include"skup.h"
#include"rec.h"
#include"stih.h"
#include"strofa.h"
#include"katren.h"
#include<string>


using namespace std;


int main() {
	
	Stih* s1 = new Stih();
	Strofa* strofa = new Katren();
	cout << strofa->oznaka() << endl;
	for (int i = 0; i < 5; i++) {
		cin >> *s1;
		strofa->operator+=(*s1);
		cout << *s1 << "br slog: " << ~*s1 << endl;
	}
	cout << "Strofa:" << endl;
	cout << *strofa;
	cout << "rimuju se? " << strofa->operator*()<<endl;

	


//	Stih* s_2 = strofa->operator[](2);
//	cout <<"Stih sa ind 2: " << *s_2;
}
