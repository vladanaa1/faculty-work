#include "katren.h"




Katren::Katren(const Katren& k):Strofa(4)
{
    kopiraj(k);
}

Katren::Katren(Katren&& k):Strofa(4)
{
    premesti(k);
}

Katren& Katren::operator=(const Katren& k)
{
    if (this != &k) {
        brisi();
        kopiraj(k);
    }
    return *this;
}

Katren& Katren::operator=(Katren&& k)
{
    if (this != &k) {
        brisi();
        premesti(k);
    }
    return *this;
}

Katren::~Katren()
{
    brisi();
}

bool Katren::operator*() const
{
    if (this->br != this->kap) { return false; }
    Stih s1 = *this->stihovi[0];
    Stih s2 = *this->stihovi[3];
    if (!(s1 ^ s2)) { return false; }
    s1 = *this->stihovi[1];
    s2 = *this->stihovi[2];
    if (!(s1 ^ s2)) { return false; }

    return true;
}

char Katren::oznaka() const
{
    return 'K';
}
