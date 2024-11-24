#include "vreme.h"

bool operator>(const Vreme& v1, const Vreme& v2)
{
	if (v1.sati > v2.sati) { return true; }
	if (v1.sati < v2.sati) { return false; }
	if (v1.minuti > v2.minuti) { return true; }
	//if (v1.minuti < v2.minuti) { return false; }
	return false;
}

bool operator<(const Vreme& v1, const Vreme& v2)
{
	if (v1 > v2) { return false; }
	if (v1.sati == v2.sati && v1.minuti == v2.minuti) { return false; }

	return true;
}

Vreme& operator+(const Vreme& v1, const Vreme& v2)
{
	int sati, minuti;
	int z_sati = v1.sati + v2.sati;
	int z_minuti = v1.minuti + v2.minuti;
	sati = (z_sati + z_minuti/60) % 24 ;
	minuti = z_minuti % 60;

	return *new Vreme(sati, minuti);
}

std::ostream& operator<<(std::ostream& os, const Vreme& v)
{
	return os << std::setfill('0') << std::setw(2) << v.sati 
		<< ":" << std::setfill('0') << std::setw(2) << v.minuti;
}
