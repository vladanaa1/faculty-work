#ifndef _strofa_h_
#define _strofa_h_

#include"stih.h"
#include<iostream>

class Strofa
{
protected:
	int kap;
	int br;

	Stih** stihovi;

	void kopiraj(const Strofa&);
	void premesti(Strofa&);
	void brisi();
public:
	Strofa(int);
	Strofa(const Strofa&);
	Strofa(Strofa&&);
	Strofa& operator=(const Strofa&);
	Strofa& operator=(Strofa&&);
	virtual ~Strofa();

	int kapacitet()const;
	Strofa& operator+=(const Stih&);  //dodavanje stiha
	void operator-();  //uklanjanje poslednjeg stiha
	int operator+()const;  //br stihova
	Stih& operator[](int)const;  //dohvatanje stiha
	void operator()	(int, int); //zamena mesta
	virtual bool operator*()const = 0; //da li se rimuju stihovi i vrsta strofe
	virtual char oznaka()const = 0;
	friend std::ostream& operator<<(std::ostream&, const Strofa&);
};

#endif