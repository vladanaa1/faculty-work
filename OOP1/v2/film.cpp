#include "film.h"


void Film::pisi(std::ostream& os) const
{
	os << this->oznaka() << " " << this->naziv << " " << this->vreme_trajanja() << " " << this->prosecna_ocena();
}

void Film::oceni(int o)
{
	this->ocene += o;
}

Vreme Film::vreme_trajanja() const
{
	return this->trajanje;
}

float Film::prosecna_ocena() const
{
	float suma = 0;
	if (this->ocene.br_pod() == 0) { return 0.0; }
	for (int ind = 0; ind < this->ocene.br_pod(); ind++) {
		suma+=this->ocene[ind];
	}
	return suma / this->ocene.br_pod();
}

std::ostream& operator<<(std::ostream& os, const Film& f)
{
	f.pisi(os);
	return os;
	//return os << f.oznaka() << " " << f.naziv << " " << f.vreme_trajanja() << " " << f.prosecna_ocena();
}
