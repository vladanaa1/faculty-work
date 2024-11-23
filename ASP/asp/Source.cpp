#include <iostream>

#include "Heap.h"


int main() {


	int k[15] = {1,2,33,4,55,6,78,8,9,10,11,344,5,14,100};
	int l[5] = { 32,314,7,2022,19 };
	Heap h(k, 15, 2);
	h.ubaciKljuc(7);

	//Heap a = merge(h, h2);
	cout << h;
	
	//h.destroy();

	return 0;
}