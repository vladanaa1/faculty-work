#include "strani_film.h"

StraniFilm::StraniFilm(Vreme& t, std::string n, std::string z, int p):Film(t,n)
{
	this->zemlja = z;
	this->prevod = (TipPrevoda)p;
}

void StraniFilm::pisi(std::ostream& os)const
{
	
	Film::pisi(os);
	os << " " << this->zemlja << " ";
	switch (this->prevod)
	{
	case 0: {os << "TITLOVAN"; }break;
	case 1: {os << "SINHRONIZOVAN"; }break;
	default:break;
	}
}

char StraniFilm::oznaka() const
{
	return 'S';
}
