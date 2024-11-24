#ifndef _strani_film_h_
#define _strani_film_h_
#include "film.h"
class StraniFilm :
    public Film
{
    typedef enum TipPrevoda { TITLOVAN, SINHRONIZOVAN };

    std::string zemlja;
    TipPrevoda prevod;

    void pisi(std::ostream&)const override;
public:
    StraniFilm(Vreme& t, std::string n, std::string z, int p);
    StraniFilm(const StraniFilm&) = default;
    StraniFilm& operator=(const StraniFilm&) = default;
    ~StraniFilm() = default;

    char oznaka()const override;

};

#endif