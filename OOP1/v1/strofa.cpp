#include "strofa.h"

void Strofa::kopiraj(const Strofa& s)
{
	this->kap = s.kap;
	this->br = s.br;
	this->stihovi = new Stih * [this->kap];
	for (int i = 0; i < this->br; i++) {
		this->stihovi[i] = new Stih(*s.stihovi[i]);
	}
}

void Strofa::premesti(Strofa& s)
{
	this->kap = s.kap;
	this->br = s.br;
	this->stihovi = s.stihovi;
	s.stihovi = nullptr;
}

void Strofa::brisi()
{
	for (int i = 0; i < this->br; i++) {
		delete this->stihovi[i];
	}
	delete[] this->stihovi;
	this->stihovi = nullptr;
	this->br = 0;
}

Strofa::Strofa(int k)
{
	this->kap = k;
	this->br = 0;
	this->stihovi = new Stih * [this->kap];
}

Strofa::Strofa(const Strofa& s)
{
	kopiraj(s);
}

Strofa::Strofa(Strofa&& s)
{
	premesti(s);
}

Strofa& Strofa::operator=(const Strofa& s)
{
	if (this != &s) {
		brisi();
		kopiraj(s);
	}

	return *this;
}

Strofa& Strofa::operator=(Strofa&& s)
{
	if (this != &s) {
		brisi();
		premesti(s);
	}
	return *this;
}

Strofa::~Strofa()
{
	brisi();
}

int Strofa::kapacitet() const
{
	return this->kap;
}

Strofa& Strofa::operator+=(const Stih& s)
{
	if (this->br == this->kap) { return *this;	}
	if (this->br != 0 && ~s != ~(*(this->stihovi[0]))) { return *this; }
	Stih* nov = new Stih(s);

	this->stihovi[this->br++] = nov;
	return *this;
}

void Strofa::operator-()
{
	if (this->br == 0) { return; }
	delete this->stihovi[--this->br];

}

int Strofa::operator+() const
{
	return this->br;
}

Stih& Strofa::operator[](int n) const
{
	return *this->stihovi[n];
}

void Strofa::operator()(int i, int j)
{
	if (i >= this->br || j >= this->br) { return; }
	if (i < 0 || j < 0) { return; }
	Stih* pom = this ->stihovi[i];
	this->stihovi[i] = this->stihovi[j];
	this->stihovi[j] = pom;
	pom = nullptr;
}

/*bool Strofa::operator*() const
{
	for (int i = 0; i < this->br; i++) {
		for (int j = i + 1; j < this->br; j++) {
			Stih s1 = *this->stihovi[i];
			Stih s2 = *this->stihovi[j];

			if (!(s1 ^ s2)) { return false; }
		}
	}
	return true;
}*/

std::ostream& operator<<(std::ostream& os, const Strofa& s)
{
	for (int i = 0; i < s.br; i++) {
		os << *s.stihovi[i];
	}
	return os;
}
