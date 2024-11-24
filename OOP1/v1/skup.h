#ifndef _skup_h_
#define _skup_h_

#include<string>
#include<iostream>

class Skup
{
	std::string slova;

public:
	Skup(std::string);
	Skup(const Skup&) = delete;
	Skup& operator=(const Skup&) = delete;
	~Skup() = default;

	Skup& operator+=(char);	
	bool operator() (char)const;  //da li se nalazi u skupu

	//pom
	//friend std::ostream& operator<<(std::ostream&, Skup&);
};

#endif