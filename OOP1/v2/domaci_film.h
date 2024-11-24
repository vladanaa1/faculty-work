#ifndef _domaci_film_h_
#define _domaci_film_h_
#include "film.h"
class DomaciFilm :
    public Film
{
    void pisi(std::ostream&)const override;
public:
    DomaciFilm(Vreme& t, std::string n) :Film(t, n) {};
    DomaciFilm(const DomaciFilm&) = default;
    DomaciFilm& operator=(const DomaciFilm&) = default;
    ~DomaciFilm() = default;

    char oznaka()const override;
    
};

#endif