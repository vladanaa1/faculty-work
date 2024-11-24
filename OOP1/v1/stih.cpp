#include<string>
#include "stih.h"

void Stih::kopiraj(const Stih& s)
{
	Node* pret = nullptr;
	Node* trn = s.prva_rec;
	while (trn) {
		Node* nov = new Node(trn->rec);

		if (!pret) { this->prva_rec = nov; }
		else { pret->sled = nov; }
		pret = nov;
		trn = trn->sled;
	}
}

void Stih::premesti(Stih& s)
{
	this->prva_rec = s.prva_rec;
	s.prva_rec = nullptr;
}

void Stih::brisi()
{
	Node* trn = this->prva_rec;
	Node* old;

	while (trn) {
		old = trn;
		trn = trn->sled;
		delete old;
	}
}

Stih::Stih(const Stih& s)
{
	kopiraj(s);
	
}

Stih::Stih(Stih&& s)
{
	premesti(s);
}

Stih& Stih::operator=(const Stih& s)
{
	if (this != &s) {
		brisi();
		kopiraj(s);
	}
	return *this;
}

Stih& Stih::operator=(Stih&& s)
{
	if (this != &s) {
		brisi();
		premesti(s);
	}
	return *this;
}

Stih::~Stih()
{
	brisi();
}

Stih& Stih::operator+=(Rec& r)
{
	Node* trn = this->prva_rec;
	while (trn && trn->sled) {
		trn = trn->sled;
	}
	Node* nov = new Node(r);
	if (this->prva_rec) { trn->sled = nov; }
	else { this->prva_rec = nov; }
	return *this;
}

int Stih::operator+() const
{
	int br = 0;
	Node* trn = this->prva_rec;
	while (trn) {
		br++;
		trn = trn->sled;
	}
	return br;
}

int Stih::operator~() const
{
	int br = 0;
	Node* trn = this->prva_rec;
	while (trn) {
		br += trn->rec.operator~();
		trn = trn->sled;
	}
	return br;
}

Rec& Stih::operator[](int n) const
{
	int br = 0;
	Node* trn = this->prva_rec;
	while (trn) {
		br++;
		if (br == n) {
			return trn->rec;
		}
		trn = trn->sled;
	}
}

void Stih::operator()(int n)
{
	int br = 0;
	Node* trn = this->prva_rec, * pret = nullptr;
	while (trn) {
		br++;
		if (br == n) {
			if (!pret) { this->prva_rec = trn->sled; }
			else { pret->sled = trn->sled; }
			trn->sled = nullptr;
			delete trn;
			return;
		}
		pret = trn;
		trn = trn->sled;
	}

}

void Stih::operator()(Rec& r, int n)
{
	//if (n > this->operator+()) { return; }    //nije neophodno jer se ide dok postoji trn
	int br = 0;
	Node* trn = this->prva_rec, * pret = nullptr;
	while (trn) {
		br++;
		if (br == n) {
			Node* nov = new Node(r);
			if (!pret) { this->prva_rec = nov; }
			else { pret->sled = nov; }

			nov->sled = trn;
			return;
		}
		pret = trn;
		trn = trn->sled;
	}

}

bool operator^(const Stih& stih1, const Stih& stih2)
{
	int ind1 = stih1.operator+();
	int ind2 = stih2.operator+();

	Rec rec1 = stih1[ind1];
	Rec rec2 = stih2[ind2];

	return rec1 ^ rec2;
}

std::istream& operator>>(std::istream& is, Stih& s)
{
	if (s.prva_rec) {
		Stih::Node* trn = s.prva_rec;
		Stih::Node* old;

		while (trn) {
			old = trn;
			trn = trn->sled;
			delete old;
		}
	}s.prva_rec = nullptr;
	//s = *new Stih();
	char c = getchar();
	std::string str = "";
	if (c != '\n' && c!=' ') { str += c; }
	while (1) {
		c = getchar();
		if (c != ' ' && c != '\n') {
			str += c;
		}
		else {
			if (str == "") { continue; }
			Rec* r = new Rec(str);
			s += *r;
			str = "";
			if (c == '\n') { break; }
		}
	}
	/*
	while (1) {
		Rec r = *new Rec("");
		is >> r;
		if (+r == 0) { delete &r; break; }
		s += &r;
	}
	*/
	return is;
}

/*

class Error : public exception {
public:
	const char* what() const override {
		return "text";
	}
};
*/

/*
try {
}
catch (exception& e) {
	cout << e.what();
}
*/

/*
template<typename T>
class Lista
{
	struct Node {
	T pod;
	Node* next;
	Node(const T& d, Node* n = nullptr) : pod(d) {
		next = n;
	}

*/

std::ostream& operator<<(std::ostream& os, const Stih& s)
{
	Stih::Node* trn = s.prva_rec;
	while (trn) {
		os << trn->rec;
		if (trn->sled) { os << " "; }
		trn = trn->sled;
	}
	return os << std::endl;
}
