### Postavka:
U školskom jezgru modifikuje se (uopštava) koncept brojačkog semafora podržan klasom Semaphore na sledeći način. Operacija wait() prima jedan nenegativan celobrojni argument n sa podrazumevanom vrednošću 1 i sa sledećim značenjem. Trenutna nenegativna vrednost semafora v predstavlja broj raspoloživih „žetona“. Operacijom wait pozivaju ća nit „traži“ n „žetona“ (kod standardnog brojačkog semafora n je uvek podrazumevano 1). Ako je trenutna vrednost v semafora veća ili jednaka argumentu n operacije wait, ta vrednost v će biti umanjena za n, a nit će nastaviti izvršavanje bez blokade, jer je „dobila“ svih traženih n „žetona“. U suprotnom, ova nit će „uzeti“ v „žetona“, i čekaće blokirana na semaforu dok se operacijama signal ne pojavi još n-v žetona; kada se to dogodi, nit može nastaviti sa izvršavanjem (jer je dobila svih traženih n „žetona“). Operacija signal „obezbeđuje“ uvek jedan „žeton“, kao i kod standardnog brojačkog semafora. 

	class Semaphore {
	public: Semaphore (unsigned int init=1);
	void wait(unsigned int n=1);
	void signal(); int val (); 
	}; 

Za podršku ovoj implementaciji, u klasi Thread postoji nenegativno celobrojno polje waiting koje pokazuje na koliko još preostalih „žetona“ čeka data nit, ukoliko je blokirana na nekom semaforu. Osim toga, klasa Queue kojom se implementira FIFO red čekanja na semaforu ima operaciju first() koja vraća prvu nit u tom redu, ako je ima (0 ako je red prazan), bez izbacivanja te niti iz reda. Pomoćne operacije block() i deblock() klase Semaphore su iste kao i u postojećoj implementaciji školskog jezgra. Dati izmenjenu implementaciju operacija wait() i signal().
### Resenje:
```c++
void Semaphore::wait (unsigned int n = 1) {
	lock(lck);
	val -= n; 
	if (val<0) { 
		Thread::running->waiting = -val; //val je neg, u waiting br niti koje cekaju izvrsavanje
		val = 0; block(); // da bi drugoj niti falilo n zetona a ne vise kad bi val stavili da je neg
	} 
	unlock(lck); 
}
 
void Semaphore::signal () {
	lock(lck);
	Thread* thr = blocked.first(); // semafor ima blocked kao atribut red blokiranih
	if (thr) { // There are blocked threads, val==0
	thr->waiting--;
	if (thr->waiting==0) deblock();
	} else // No blocked threads, val>=0
	val++; 
	unlock(lck);
}
```