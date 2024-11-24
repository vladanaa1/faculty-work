#include "skup.h"

Skup::Skup(std::string s)
{
	this->slova = "";
	for (int i = 0; i < s.length(); i++) {
		char novo_slovo = s[i];
		bool postoji = false;
		for (int j = 0; j < this->slova.length(); j++) {
			if (this->slova[j] == novo_slovo) { postoji = true; break; }
		}
		if (!postoji) {
			this->slova += novo_slovo;
		}
	}
}

Skup& Skup::operator+=(char c)
{
	if (this->operator()(c) == true) { return *this;	} //vec postoji

	this->slova += c;
	
	return *this;
}

bool Skup::operator()(char c) const
{
	for (int j = 0; j < this->slova.length(); j++) {
		if (this->slova[j] == c) { return true; }
	}
	return false;
}

/*std::ostream& operator<<(std::ostream& os, Skup& s)
{
	return os << "SKUP: " << s.slova << std::endl;
}
*/