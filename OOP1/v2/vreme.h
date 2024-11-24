#ifndef _vreme_h_
#define _vreme_h_
#include<iostream>
#include<iomanip>

class Vreme
{
	int sati;
	int minuti;


public:
	Vreme(int s, int m) : sati(s), minuti(m) {};
	Vreme(const Vreme&)=default;
	Vreme(Vreme&&)=default;
	Vreme& operator=(const Vreme&)=default;
	Vreme& operator=(Vreme&&)=default;
	~Vreme()=default;

	friend bool operator>(const Vreme&, const Vreme&);
	friend bool operator<(const Vreme&, const Vreme&);
	friend Vreme& operator+(const Vreme&, const Vreme&);  //stvaranje vremena sa pomerajem 
	friend std::ostream& operator<<(std::ostream&, const Vreme&);
};

#endif