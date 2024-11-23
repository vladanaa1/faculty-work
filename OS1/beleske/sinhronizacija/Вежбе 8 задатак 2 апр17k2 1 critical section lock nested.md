Neki multiprocesorski operativni sistem sa preotimanjem (preemptive) dozvoljava preuzimanje tokom izvršavanja koda kernela, osim u kritičnim sekcijama, kada se pristupa deljenim strukturama podataka jezgra kojima mogu da pristupaju različiti procesori. Svaku takvu deljenu strukturu „štiti“ jedna celobrojna promenljiva koja služi za međusobno isključenje pristupa sa različitih procesora pomoću tehnike spin lock, dok se zabrana preuzimanja na datom procesoru obezbeđuje maskiranjem prekida. Međusobno isključenje pristupa kritičnoj sekciji, odnosno deljenoj strukturi, obavlja se pozivima operacija čiji je argument pokazivač na celobrojnu promenljivu koja „štiti“ datu deljenu strukturu:

	void lock (short* lck); 
	void unlock (short* lck);
na ulazu, odnosno izlazu iz kritične sekcije. Pošto postoji potreba da kernel kod istovremeno pristupa različitim deljenim strukturama, ===kritične sekcije se mogu ugnježdavati.=== 
===Zato demaskiranje prekida treba uraditi tek kada se izađe iz krajnje spoljašnje kritične sekcije (a ne pri svakom pozivu unlock). ===
Podaci koji su svojstveni svakom procesoru (svaki procesor ima svoju instancu ovih podataka) grupisani su u strukturu tipa ProcessorPrivate. (Ovu strukturu možete proširivati po potrebi.) Na raspolaganju su i sledeće funkcije ili makroi:

• disable_interrupts(): onemogućava spoljašnje prekide na ovom procesoru;
• enable_interrupts(): dozvoljava spoljašnje prekide na ovom procesoru; 
• test_and_set(short* lck): „obavija“ instrukciju procesora tipa test-and-set; //setuje vrednost na lck na jedan i kao rezultat vraca njegovu prethodnu vrednost
• this_processor(): vraća celobrojni identifikator tekućeg procesora (na kome se kod izvršava); 
• ProcessorPrivate processor_private[[NumOfProcessors]]: niz struktura sa „privatnim“ podacima svakog procesora.

Implementirati operacije lock i unlock.
### Resenje:
```c++
struct ProcessorPrivate {
	int lock_count; // Counts the number of locks (nested critical sections)
	ProcessorPrivate () : lock_count(0),... {...} 
	}; 
void lock (short* lck) { 
	disable_interrupts(); // Multiple calls of disable_intterupt are harmless
	processor_private[this_processor()].lock_count++; // nad strukturom niti koja je izvrsila lock povecamo njegov lock count
	while (test_and_set(lck)); // tehnika spin lock
}
void unlock (short* lck) {
	*lck=0;
	if (--processor_private[this_processor()].lock_count==0)
		enable_interrupts();
}
```
![[Pasted image 20230428142939.png]]