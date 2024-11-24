#include "domaci_film.h"

void DomaciFilm::pisi(std::ostream& os) const
{
    //os << this->oznaka() << " " << this->naziv << " " << this->vreme_trajanja() << " " << this->prosecna_ocena();
    Film::pisi(os);
}

char DomaciFilm::oznaka() const
{
    return 'D';
}
