#ifndef _stih_h_
#define _stih_h_
#include"rec.h"
#include<iostream>

class Stih
{
	struct Node {
		Rec rec;
		Node* sled;
		Node(const Rec& r):rec(r) {
			//rec = *new Rec(r);
			sled = nullptr;
		}
		~Node() {
			sled = nullptr;
			//delete &rec;    //!!!!!!!!!Da li treba bristai rec?????????????
		}
	};

	Node* prva_rec = nullptr;
	void kopiraj(const Stih&);
	void premesti(Stih&);
	void brisi();

public:
	Stih() = default;
	Stih(const Stih&);
	Stih(Stih&&);
	Stih& operator=(const Stih&);
	Stih& operator=(Stih&&);
	~Stih();

	Stih& operator+=(Rec&);  //dodaj rec na kraj
	int operator+()const;  //br reci
	int operator~()const;  //ukupan br slogova
	Rec& operator[] (int)const;  //dohvatanje reci
	void operator() (int);  //brisanje zadate reci
	void operator() (Rec&, int);  //umetanje reci pre zadate poz
	friend bool operator^(const Stih&, const Stih&);  //da li se rimuju
	friend std::istream& operator>>(std::istream&, Stih&);
	friend std::ostream& operator<<(std::ostream&, const Stih&);
};

#endif // !_stih_h_
