#ifndef _greska_h_
#define _greska_h_

#include<exception>

class PozicijaIzvanOpsega : public std::exception {

public:
	PozicijaIzvanOpsega() :exception("Pozicija izvan opesga!") {}
};



#endif // !_greska_h_
