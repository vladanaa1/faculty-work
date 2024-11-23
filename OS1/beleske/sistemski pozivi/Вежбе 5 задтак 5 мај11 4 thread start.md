pomocu clone() pravis start()
U nekom operativnom sistemu sistemski poziv clone() pravi novu nit (thread) kao klon roditeljske niti, sa istovetnim kontekstom izvršavanja i u istom adresnom prostoru kao što je i roditeljska nit – isto kao i fork(), samo što pravi nit u istom adresnom prostoru, a ne proces. Ovaj poziv vraća 0 u kontekstu niti-deteta, a vrednost >0 koja predstavlja ID kreirane niti u kontekstu niti-roditelja. Vraćena vrednost <0 označava neuspešan poziv. Sistemski poziv terminate(int) gasi nit sa datim ID. Korišćenjem ovog sistemskog poziva realizovati klasu Thread sa istim interfejsom kao u školskom jezgru (kreiranje niti nad virtuelnom funkcijom run() i pokretanje niti pozivom funkcije start()).
### Resenje:
```c++
class Thread {
public: 
	int start ();
protected:
	Thread () : myID(0) {}
	virtual void run() {}
private: 
	int myID;
};
int Thread::start () {
	int id = clone();//slicno se ponasa kao fork samo pravi nit a ne proces tj pravi procese sa istim adr prostorom 
	if (id<0) return id; // Failure
	if (id>0) { // Successful start in the parent’s context
	myID=id;
	return 0;
	}
	//Child context: 
	run();//kad se zavrsi run moramo da ugasimo nit, ali postoji uposleno cekanje, zasto?
	//moze da se desi da se run zavrsio pre dodele IDja
	while (myID==0);// Busy-wait until the parent writes myID
	terminate(myID);
}
```