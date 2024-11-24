#ifndef _katren_h_
#define _katren_h_
#include "strofa.h"


class Katren : public Strofa
{

public:

	Katren() : Strofa(4) {};
	Katren(const Katren& k);
	Katren(Katren&& k);
	Katren& operator= (const Katren&);
	Katren& operator= (Katren&&);
	~Katren();

	bool operator*()const override;
	char oznaka() const override;
};

#endif