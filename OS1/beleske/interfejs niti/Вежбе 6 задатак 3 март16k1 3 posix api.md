U nastavku je data donekle izmenjena i pojednostavljena specifikacija dva sistemska poziva iz standardnog POSIX thread API (Pthreads). Obe ove funkcije vraćaju negativnu vrednost u slučaju greške, a 0 u slučaju uspeha.

• int pthread_create(pthread_t * thread, void * (* start_routine)(void *) ,void *arg): kreira novu nit nad funkcijom na koju ukazuje start_routine, dostavljajući joj argument arg; identifikator novokreirane niti smešta u lokaciju na koju ukazuje thread;
	//void * (* start_routine)(void *)  -  pokazivac na fju koja kao arg prima void * i vraca void *, jer se svi argumenti smestaju u strukturu pa fja kao arg prima pokazivac na tu strukturu, isto tako i vracamo pokazivac na bilo sta
	//void *arg - argument fje start_routine
• int pthread_join(pthread_t thread, void ** retval): čeka da se nit identifikovana sa thread završi (ukoliko se već završila, odmah vraća kontrolu); ako retval nije NULL, kopira povratnu vrednost funkcije start_routine koju je ta nit izvršavala u lokaciju na koju ukazuje retval. 
//npr kad imamo nit roditelj da mozemo da sacekamo da se zavrsi nit dete
Data je struktura Node koja predstavlja jedan čvor binarnog stabla. Korišćenjem datih sistemskih poziva implementirati funkciju createSubtree koja rekurzivno kreira binarno stablo dubine n i vraća pokazivač na njegov koreni čvor, tako što u istoj niti kreira levo podstablo, a u novokreiranoj niti uporedo kreira desno podstablo. Ignorisati eventualne greške
### Resenje:
```c++
struct Node {
Node () : leftChild(NULL), rightChild(NULL) {} 
Node *leftChild, *rightChild; ... }; 

inline void* _createSubtree (void* n) { //wrapper
	return createSubtree(*(int*)n); 
}

Node *createSubtree(int n){
	if (n<=0) return 0;
	Node* node = new Node();
	if (n<=1) return node;
	int n1 = n - 1; //jos n-1 levela treba da napravimo
	//Create a new thread to create the right subtree:
	pthread_t pid;
	pthread_create(&pid,&_createSubtree,&n1);//n1 argument za createSubtree
	// za svako levo podstablo pustis nit koja zida desno podstablo
	// Create the left subtree in this thread:
	node->leftChild = createSubtree(n1);
	pthread_join(pid,&node->rightChild);//cekaj da se ne zavrsi nit sa id i u njen rightChild sacuvaj rez createsubtree
	return node;
}
```
![[Pasted image 20230428161049.png]]
// odradi se jedan saceka se dvojka joinom i tek onda rekurzivno se poziva troja za levo levo podstablo