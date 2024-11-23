#include "Heap.h"

Heap::Heap(int m, int n)// konstruktor prazan hip
{
	niz = new int[n];
	kap = n;
	red = m;
	vel = 0;
}

Heap::Heap(int* niz, int k, int r)
{
	
	red = r;
	kap = k;
	this->niz = niz;
	vel = 0;
	napraviHeap(kap);
	
}
