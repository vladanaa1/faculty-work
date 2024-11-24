#include "rec.h"

Skup Rec::alfabet("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz");

bool nosilac_sloga(std::string rec, int i) {
	Skup samoglasnik("aeiouAEIOU");
	Skup sonant("lnLNrR");
	Skup l_n("lnLN");

	char slovo = rec[i];
	if (!samoglasnik(slovo) && !sonant(slovo)) { return false; }
	char pre = (i == 0 ? 'b' : rec[i - 1]);
	char posle = (i == rec.length() - 1 ? 'b' : rec[i + 1]);

	if (samoglasnik(pre) || samoglasnik(posle)) { return false; }
	if (l_n(slovo) && ('j' == pre || 'J' == pre || 'j' == posle || 'J' == posle)) { return false; }

	return true;

}

Rec::Rec(std::string s) {
	this->rec = "";
	for (int i = 0; i < s.length(); i++) {
		char novo_slovo = s[i];
		if (alfabet(novo_slovo)) {
			this->rec += novo_slovo;
		}
	}

}
/*
Rec::Rec(const Rec& r)
{
	this->rec = r.rec;
}

Rec& Rec::operator=(const Rec& r)
{
	this->rec = r.rec;
	return *this;
}
*/
int Rec::operator+() const
{
	return this->rec.length();
}

int Rec::operator~() const
{
	Skup samoglasnik_r("aeiouAEIOUrR");
	Skup sonant("lnLN");

	int br = 0;
	for (int i = 0; i < this->rec.length(); i++) {
		/*
		char slovo = this->rec[i];
		if (!samoglasnik_r(slovo) && !sonant(slovo)) { continue; }
		char pre = (i == 0 ? 'b' : this->rec[i - 1]);
		char posle = (i == this->rec.length()-1 ? 'b' : this->rec[i + 1]);

		if (samoglasnik_r(pre) || samoglasnik_r(posle)) { continue; }
		if (sonant(slovo) && ('j' == pre || 'J' == pre || 'j' == posle || 'J' == posle)) { continue; }

		br++;
		*/
		if (nosilac_sloga(this->rec, i)) { br++; }
	}

	return br;
}

int Rec::operator()(int n) const
{
	int br_slog = this->operator~();
	//if (br_slog < abs(n)) { return -1; }
	
	if (n >= 0) {
		int br = 0;
		for (int i = 0; i < this->rec.length(); i++) {
			if (nosilac_sloga(this->rec, i)) {
				
				if (br == n /* || n == 0*/) { return i; }  //vraca prvi slog ako je n=0, drugi za n=1
				br++;
			}
		}
	}
	else {
		int br = 0;
		for (int i = this->rec.length()-1; i >=0; i--) {
			if (nosilac_sloga(this->rec, i)) {
				br++;
				if (br == abs(n)) { return i; }
			}
		}
	}
	
	return -1;
}

bool operator^(const Rec& rec1, const Rec& rec2)
{
	int br = (~rec1 < ~rec2 ? ~rec1 : ~rec2);
	br = br >= 2 ? 2 : br;

	int i1 = rec1.rec.length()-1;
	int i2 = rec2.rec.length()-1;

	int kraj1 = rec1(-br);
	int kraj2 = rec2(-br);
	if (i1 - kraj1 != i2 - kraj2) { return false; }

	while (i1 >= kraj1) {
		if (rec1.rec[i1] != rec2.rec[i2]) { return false; }
		i1--;
		i2--;
	}
	return true;
}

std::istream& operator>>(std::istream& is, Rec& rec)
{
	std::string r;
	is >> r;
	rec = Rec(r);    
	return is;
}

std::ostream& operator<<(std::ostream& os, const Rec& r)
{
	return os << r.rec;
}
