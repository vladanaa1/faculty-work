U implementaciji jezgra nekog jednoprocesorskog time-sharing operativnog sistema, radi pojednostavljenja celog mehanizma promene konteksta, primenjeno je sledeće neobično rešenje. Promena konteksta vrši se isključivo kao posledica prekida, na samo jednom mestu u kodu koji se izvršava na prekid. Prekidi dolaze od raznih uređaja, u najmanju ruku od vremenskog brojača, jer on u svakom slučaju generiše prekid zbog toga što je tekućoj niti isteklo dodeljeno procesorsko vreme. Zbog toga tekuća nit nikada ne gubi procesor sinhrono, čak ni kada poziva blokirajuću operaciju (sistemski poziv). Umesto toga, ukoliko je potrebno da se nit suspenduje (blokira) u nekom blokirajućem pozivu, nit se samo „označi“ suspendovanom i nastavlja sa izvršavanjem (uposlenim čekanjem) sve dok ne stigne sledeći prekid. Kada takav prekid stigne, prekidna rutina vrši samu promenu konteksta.
Na primer, implementacija operacije suspend, koja suspenduje pozivajući proces, i resume, koja ponovo deblokira dati proces, izgledaju ovako:

	void suspend () { 
	running->status = suspended; // Mark as suspended 
	while (running->status == suspended); // and then busy-wait // until it is preempted, suspended, and then resumed later 
	} 
	void resume (int pid) { processes[pid].status = ready; // Mark as ready 
	}
	
Procesor je RISC sa load/store arhitekturom, ima 32 registra opšte namene i SP. Prilikom prekida na steku čuva samo PC i PSW. ==Tajmer se restartuje upisom odgovarajuće vrednosti u registar koji se nalazi na adresi simbolički označenoj sa Timer.== PCB procesa je dat strukturom definisanom dole, a svi procesi zapisani su u nizu processes. U asembleru, simboličko ime polja strukture ima vrednost pomeraja (engl. offset) tog polja od početka strukture.
```c++
enum ProcessStatus { unused, initiating, terminating, ready, suspended };
typedef unsigned long Time;
typedef unsigned Register;
struct PCB { ProcessStatus status; // Process status
Time timeSlice; // Time slice for time sharing
Register savedSP; // Saved stack pointer ... } 
const unsigned long NumOfProcesses = ...;
PCB processes[NumOfProcesses];
PCB* running; // Running process
```
1. Na asembleru datog procesora napisati kod prekidne rutine koja vrši promenu konteksta. Ova prekidna rutina, pored čuvanja i restauracije konteksta na steku procesa, treba da pozove potprogram scheduler koji će u pokazivač running smestiti vrednost koja ukazuje na novoizabrani tekući proces, i da tajmer restartuje sa vremenskim kvantumom dodeljenim tom procesu. Pretpostavlja se da uvek postoji barem jedan spreman proces.
2. Na jeziku C napisati potprogram scheduler koji bira sledeći proces za izvršavanje. Spremne procese treba da bira redom, u krug, a ne svaki put od početka niza processes.
### Resenje:
```
dispatch: ; Save the current context 
push r0 ; save regs 
push r1 ... push r31
load r0, running
store sp, #savedSP[r0] ; save sp ; 
Select the next running process 
call scheduler ; 
Restore the new context 
load r0, running
load r1, #timeSlice[r0]; restart timer // jedina caka 
store r1, [Timer] 
load sp, #savedSP[r0]; restore sp 
pop r31 
pop r30 ; restore regs ... 
pop r0 
; Return 
iret
```
```c++
void scheduler () {
do
{ running = processes + (running - processes + 1) % NUM_OF_PROCESSES; }
				   while (running->status!=ready);
 }
```