#ifndef _film_h_
#define _film_h_
#include<string>
#include<iostream>
#include"vreme.h"
#include"lista.h"

class Film
{

	Vreme trajanje;
	std::string naziv;
	Lista<int> ocene;
protected:
	virtual void pisi(std::ostream&)const;

public:
	Film(Vreme& v, std::string n) :trajanje(v), naziv(n), ocene() {};
	Film(const Film&) = default;
	Film& operator=(const Film&) = default;
	virtual ~Film() = default;

	void oceni(int);
	Vreme vreme_trajanja()const;
	float prosecna_ocena()const;
	virtual char oznaka()const=0;
	friend std::ostream& operator<<(std::ostream&, const Film&);


};

#endif