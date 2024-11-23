### Postavka:
Data je bibliotečna funkcija namenjena podršci optimističkom pristupu međusobnom isključenju bez eksplicitnog zaključavanja: 

	int cmpxchg(void** ptr, void* oldValue, void* newValue);  // atomicno uporedjuje vrednosti na dva pointera(*ptr i oldValue) i ako su jednake na adresu datu sa ptr upisuje newValue, vraca 1

Ova funkcija kao prvi argument (ptr) prima adresu lokacije u kojoj se nalazi neki pokazivač bilo kog tipa (void*). Ona atomično poredi vrednost pokazivača koji je dostavljen kao drugi argument (oldValue) sa vrednošću na lokaciji na koju ukazuje ptr, i ako su te vrednosti iste, u lokaciju na koju ukazuje ptr upisuje vrednost datu trećim argumentom (newValue) i vraća 1; u suprotnom, ako su ove vrednosti različite, ne radi ništa, već samo vraća 0. Atomičnost je obezbeđena implementacijom pomoću odgovarajuće mašinske instrukcije.

Struktura Record predstavlja zapis (jedan element) jednostruko ulančane liste. U toj strukturi polje next ukazuje na sledeći element u listi. 
Korišćenjem date operacije cmpxchg() implementirati funkciju:

	void insert (Record** head, Record* e); // 

Ova funkcija prima argument koji predstavlja adresu pokazivača na prvi element liste (adresu lokacije u kojoj je glava liste), a kao drugi argument dobija pokazivač na novi element u listi koga treba da umetne na početak date liste. ==Lista je deljena između više procesa, pa ova funkcija treba da bude sigurna za uporedne pozive iz više procesa, s tim da međusobno isključenje treba obezbediti optimističkom strategijom bez eksplicitnog zaključavanja.== Zapis na koga ukazuje drugi element (e) je privatan samo za pozivajući proces (drugi procesi mu ne pristupaju pre umetanja u listu).

optimisticni i pesimisticki pristup:
pesimisticki: uvek pozivamo lock i unlock pri kriticnoj sekciji jer smatramo da ce uvek doci do promene konteksta
optimisticki(optimisticko zakljucavanje a zakljucavanja nema): pustimo da se desi promena konteksta, smatramo da novi proces nece raditi sa podacima sa kojim m trenutno radimo, na kraju moramo da proverimo da li je na kraju doslo do promene kriticne sekcije!!!!
### Resenje:
```c++
void insert (Record** head, Record* e) {
	do { Record* oldHead = *head; 
		Record* newHead = oldHead; 
		e->next = newHead; 
		newHead = e; 
		} while (cmpxchg(head,oldHead,newHead)==0); 
		
```
![[Pasted image 20230428125323.png]]