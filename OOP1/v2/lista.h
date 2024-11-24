#ifndef _lista_h_
#define _lista_h_
#include"greska.h"

template <class T>class Lista {

	struct Node {
		T pod;
		Node* sled;
		Node(const T& t) {
			pod = t;
			sled = nullptr;
		}
		/*~Node() {
			delete pod;
			sled = nullptr;
		}*/
	};

	Node* lista=nullptr;
	void kopiraj(const Lista&);
	void premesti(Lista&&);
	void brisi();

public:
	Lista() = default;
	Lista(const Lista&);
	Lista(Lista&&);
	Lista& operator=(const Lista&);
	Lista& operator=(Lista&&);
	~Lista();

	void operator+=(const T&);  //dodavanje na kraj
	int br_pod()const;  //dohvata br pod
	T& operator[](int)const; //dohvatanje n-tog podatka



};



template<class T>
inline void Lista<T>::kopiraj(const Lista& l)
{
	Node* trn = l.lista;
	Node* pret = nullptr;
	while (trn) {
		//T* nov_pod = new T(*trn->pod);   //neophodan k.k. za tip T!!!
		Lista::Node* nov = new Lista::Node(trn->pod);

		if (!pret) { this->lista = nov; }
		else { pret->sled = nov; }

		pret = nov;
		trn = trn->sled;

	}

}

template<class T>
inline void Lista<T>::premesti(Lista&& l)
{
	this->lista = l.lista;
	l.lista = nullptr;
}

template<class T>
inline void Lista<T>::brisi()
{
	Node* trn = this->lista;
	while (trn) {
		Node* old = trn;
		trn = trn->sled;
		delete old;
	}
	this->lista = nullptr;

}

template<class T>
inline Lista<T>::Lista(const Lista& l)
{
	kopiraj(l);
}

template<class T>
inline Lista<T>::Lista(Lista&& l)
{
	premesti(l);
}

template<class T>
inline Lista<T>& Lista<T>::operator=(const Lista& l)
{
	if (this != &l) {
		brisi();
		kopiraj(l);
	}
	return *this;
}

template<class T>
inline Lista<T>& Lista<T>::operator=(Lista&& l)
{
	premesti(l);

	return *this;
}

template<class T>
inline Lista<T>::~Lista()
{
	brisi();
}

template<class T>
inline void Lista<T>::operator+=(const T& t)
{
	Node* trn = this->lista;
	while (trn && trn->sled) {
		trn = trn->sled;
	}
	//T* nov_pod = new T(t);
	Node* nov = new Node(t);
	if (!this->lista) { this->lista = nov; }
	else { trn->sled = nov; }
}

template<class T>
inline int Lista<T>::br_pod() const
{
	int br = 0;
	Node* trn = this->lista;
	while (trn) {
		br++;
		trn = trn->sled;
	}
	return br;
}

template<class T>
inline T& Lista<T>::operator[](int n)const
{
	if (n < 0 || n >= this->br_pod()) { throw PozicijaIzvanOpsega(); }
	int br = 0;
	Node* trn = this->lista;
	while (trn) {
		if (br == n) { return trn->pod; }
		br++;
		trn = trn->sled;
	}
	throw PozicijaIzvanOpsega();  // suvisno
}

#endif