#pragma once
#include <iostream>
#include <cstdlib>
#include<math.h>

using namespace std;
class Heap
{


private:
	
	int red;
	int kap;
	int vel;
	static int otac(int i, int red) { return (i - 1) / red; }
	static int prviSin(int i, int red) { return (i + 1) * red - 1; }

public:
	int* niz;
	Heap() = default;
	Heap(int m,int n);
	Heap(int m) { 
		kap = 0;
		niz = nullptr;
		red = m;
		vel = 0;
	}
	
	void ubaciKljuc(int kljuc) {
		
		int ot = otac(vel, red);
		int v = vel++;
		while(v>0 && niz[ot]>kljuc){
			//niz[v] = niz[ot];
			swap(&niz[v], &niz[ot]);
			v = ot;
			ot = otac(ot, red);
	}
		niz[v] = kljuc;
	}

	int findMin(int* kljucevi) {
		int min = 100000;
		for (int i = 0; i < red; i++)
			if (kljucevi[i] < min) {
				min = kljucevi[i];
			}
		return min;
	}
	void swap(int* x, int* y)
	{
		int temp = *x;
		*x = *y;
		*y = temp;
	}

	void deleteKey() {
		niz[0] = niz[--vel];
		int f = 0;
		int* sinovi = new int[red];
		int* pozicije = new int[red];
		while (f < vel - red + 1) {
			for (int i = 0; i < red; i++) {
				sinovi[i] = prviSin(f, red) + i;
				pozicije[i] = (i + 1) * red - 1 + i;
			}
			int min = findMin(sinovi); //pozicija
			if (niz[f] > niz[pozicije[min]]) {
				int tmp = niz[f];
				niz[f] = niz[pozicije[min]];
				f = pozicije[min];
			}
			else break;
			}
	}



	int get() {
		int min = 1000;
		for (int i = 0; i < vel; i++) {
			if (niz[i] < min) min = niz[i];
		
		}
		return min;
	}

	void convertTo(int m) {
		red = m;
	
	}
	void destroy() {
		delete[]niz; niz = nullptr;
		kap = vel = 0;
		red = 0;
	}

	void napraviHeap(int k) {
		if (k < 1) return;
		if (k > kap) k = kap;
		vel = 0;
		for (int i = 0; i < kap; i++) ubaciKljuc(niz[i]);
	}

	friend Heap& merge(Heap& h, Heap& j) {
	
		int jed = h.vel;
		int dva = j.vel;
		
		int* rez = new int[jed + dva];
		
		std::copy(h.niz, h.niz + jed, rez);
		std::copy(j.niz, j.niz + dva, rez + jed);
		Heap a(rez, jed + dva, h.red);
		h.destroy();
		j.destroy();
		return a;
	
	}


	Heap(int* niz, int k, int r);

	friend ostream& operator<<(ostream& os, Heap& h) {
		int stepen = 1;
		int blok = 0;
		os << h.niz[0] << endl;
		for (int i = 1; i < h.vel; i++) {
			os << h.niz[i] << ' ';
			blok++;
			if (pow(h.red, stepen) == blok) { os << endl; blok = 0; stepen++; }
			else if (i % h.red == 0) os << '\t';
			
		}
		return os;
	}
};

