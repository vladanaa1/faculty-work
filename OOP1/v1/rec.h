#ifndef _rec_h_
#define _rec_h_
#include"skup.h"
#include<string>
#include<iostream>

class Rec
{
	static Skup alfabet;
	std::string rec;

public:
	Rec(std::string);
	Rec(const Rec&)=default;
	Rec& operator=(const Rec&)=default;
	~Rec() = default;

	int operator+() const;  //duzina
	int operator~() const;  //br slogova
	int operator() (int n)const;  //n-ti nosilac sloga      (0 i 1 vracaju prvi nosilac)
	friend bool operator^ (const Rec&, const Rec&);   //da li se rimuju dve reci
	friend std::istream& operator>>(std::istream&, Rec&);
	friend std::ostream& operator<<(std::ostream&, const Rec&);


};

#endif // !_rec_h_

