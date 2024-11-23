slicno kao 7. zadatak sa Vezbi 6, gde su prekidi realizovani sistemskim pozivom znaci korisnicki stek 1 ->sistemski stek 1-> sistemski stek 2 -> kor stek 2
Neki procesor obrađuje prekide (hardverske i softverske) tako što tokom izvršavanja prekidne rutine koristi poseban stek koji se koristi u sistemskom, privilegovanom režimu rada procesora, u kome se izvršava kod kernela (čiji je deo i prekidna rutina). Taj stek alociran je u delu memorije koju koristi kernel, a na vrh tog steka ukazuje poseban registar SSP procesora koji je dostupan samo u privilegovanom režimu.

Prilikom obrade prekida, procesor na ovom steku čuva: pokazivač vrha korisničkog steka (SP) koji je koristio prekinuti proces, programsku statusnu reč (PSW) i programski brojač (PC), tim redom, ali ne i ostale programski dostupne registre. Prilikom povratka iz prekidne rutine instrukcijom iret, procesor restaurira ove registre sa sistemskog steka i vraća se u korisnički režim, a time i na korisnički stek.

==Svaki proces ima sopstveni takav sistemski stek.== Prilikom promene konteksta, ostale programski dostupne registre treba sačuvati na ovom sistemskom steku prekinutog procesa. U strukturi PCB postoji polje za čuvanje vrednosti SSP steka procesa; pomeraj ovog polja u odnosu na početak strukture PCB označen je simboličkom konstantom offsSSP.

Procesor je RISC, sa load/store arhitekturom i ima sledeće programski dostupne registre: 32 registra opšte namene (R0..R31), SP, PSW i PC.

==Sistem je multiprocesorski.== Poseban registar procesora RPID služi za identifikaciju tekućeg procesa koji taj procesor trenutno izvršava i dostupan je samo u privilegovanom režimu. Potprogram scheduler, koji nema argumente, realizuje raspoređivanje, tako što smešta adresu PCB onog procesa koji se raspoređuje na datom procesoru (na kome se izvršava kod) u registar RPID.

1. ==Na asembleru datog procesora napisati kod prekidne rutine dispatch koja vrši promenu konteksta korišćenjem potprograma scheduler.== Ova prekidna rutina poziva se sistemskim pozivom iz korisničkog procesa, pri čemu se sistemski poziv realizuje softverskim prekidom.
2. Pod pretpostavkom da procedura scheduler obezbeđuje međusobno isključenje pristupa deljenim strukturama podataka (npr. redu spremnih procesa) od strane različitih procesora, da li i ostatak prekidne rutine dispatch treba da obezbedi isto, tj. da li je u nju potrebno ugraditi spin lock? Precizno obrazložiti odgovor.
- Ne, Ova prekidna rutina pristupa samo registrima procesora, strukturi PCB tekućeg procesa i njegovom sistemskom steku. Kako su sve tri stvari korišćene isključivo od strane tog procesora (jer je taj proces raspoređen samo tom procesoru), nema potrebe za međusobnim isključenjem
- u sustini samo za scheduler treba spin lock
### Resenje:
```
dispatch:
	push r0
	push r1
	...
	push r31
	store #offsSSP[RPID]
	call scheduler
	 
	load ssp, #offsSSP[RPID]
	pop r31
	pop r30
	..
	pop r0
	
	iret
```