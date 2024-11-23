//procitaj prekide iz knjige
u sustini ovakav sluc posmatras kao switch case gde u odnosu na r0 skaces u neku granu, u ovom slucaju za ovu uslugu nama treba 0 u r0
U nekom sistemu svi sistemski pozivi vrše se softverskim prekidom broj 44h, pri čemu u registru r0 sistem očekuje identifikator sistemskog poziva. U ovom sistemu postoji sistemski poziv za kreiranje i pokretanje niti nad potprogramom koji prihvata jedan argument tipa pokazivača. Ovaj poziv u registrima očekuje sledeće argumente:
• u r0 treba da bude identifikator ovog poziva, 0 u ovom slučaju;
• u r1 treba da bude adresa potprograma nad kojim se kreira nit;
• u r2 treba da bude argument potprograma nad kojim se kreira nit.
Svaki sistemski poziv vraća rezultat u registru r0, a za ovaj sistemski poziv rezultat je identifikator niti u jezgru (ceo broj veći od 0), odnosno kod greške (ceo broj manji od 0). U asemblerskim blokovima unutar C/C++ koda može se koristiti simbolička konstanta sa nazivom formalnog argumenta funkcije unutar koje se nalazi dati asemblerski kod; ova simbolička konstanta ima vrednost odgovarajućeg pomeraja lokacije u kojoj se nalazi dati formalni argument u odnosu na vrh steka (kao što je pokazano u primerima na predavanjima). C/C++ funkcije vraćaju vrednost u registru r0 ukoliko je tip povratne vrednosti odgovarajući.
1. Implementirati funkciju 
		*int create_thread (void (*f)(void * ), void* arg); //prvi parametar pok na fju koja za arg prima void* ?
	koja vrši opisani sistemski poziv (kreira nit nad funkcijom f sa argumentom arg) i koja može da se poziva iz C koda sa zadatim argumentima. Ova funkcija vraća identifikator kreirane niti koji se može koristiti u ostalim sistemskim pozivima da identifikuje tu nit u jezgru, odnosno kod greške. 
2. Korišćenjem prethodne funkcije, implementirati funkciju start klase Thread. Ova klasa obezbeđuje koncept niti u objektnom duhu, poput onog u školskom jezgru (kreira nit nad virtuelnom funkcijom run).
```c
class Thread { 
public: 
	Thread () : pid(0) {} 
	int start (); // nakon izvrsenja start nit je u ready queue
	virtual void run () {} //izvrsavanje je u run tj sta nit radi
private: 
	int pid; // Thread ID 
};
```
### Resenje:
a)
```c
*int create_thread (void (*f)(void * ), void* arg){
// u sustini iz teksta u ovu fju treba da upakujes sistemski poziv tj kreira nit nad f(arg)
	asm{
		load r0,#0
		load r1, #f[sp] //u r1 adresu fje f
		load r2, #arg[sp]
		int 44h // instrukcija softverskog prekida, ti svaki prekid ovako pozivas i onda na osnovu vrednosti u r1 r2 r3 izvrsice se sistemski poziv kreiranja niti
		
	}
}
```
b)
```c++
void wrapper (void* t) {
	if(t) ((Thread*)t)->run();//mora preko pokazivaca zbog polimorfizma klase Thread u sustini sve niti se kreiraju iz izv objekaata klase Thread
}
int Thread::start () {
	if(pid) return pid; //vec jednom se probalo sa pozivanjem metode start jer je u konstruktoru podr vr 0
	else return pid = create_thread(&wrapper,this);//nit se kreira nad run
}
```