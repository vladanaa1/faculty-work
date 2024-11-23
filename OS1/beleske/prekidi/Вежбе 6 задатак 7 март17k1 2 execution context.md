Neki procesor obrađuje prekide (hardverske i softverske) tako što tokom izvršavanja prekidne rutine koristi poseban stek koji se koristi u sistemskom, privilegovanom režimu rada procesora, u kome se izvršava kod kernela (čiji je deo i prekidna rutina). Taj stek alociran je u delu memorije koju koristi kernel, a na vrh tog steka ukazuje poseban registar SSP procesora koji je dostupan samo u privilegovanom režimu.
Prilikom obrade prekida, procesor na ovom steku čuva: pokazivač vrha korisničkog steka (SP) koji je koristio prekinuti proces, programsku statusnu reč (PSW) i programski brojač (PC), tim redom, ali ne i ostale programski dostupne registre. Prilikom povratka iz prekidne rutine instrukcijom iret, procesor restaurira ove registre sa sistemskog steka i vraća se u korisnički režim, a time i na korisnički stek. ==Svaki proces ima takav sopstveni sistemski stek.== Prilikom promene konteksta, ostale programski dostupne registre treba sačuvati na ovom sistemskom steku prekinutog procesa. U strukturi PCB postoji polje za čuvanje vrednosti SSP steka procesa; pomeraj ovog polja u odnosu na početak strukture PCB označen je simboličkom konstantom offsSSP. Procesor je RISC, sa load/store arhitekturom i ima sledeće programski dostupne registre: 32 registra opšte namene (R0..R31), SP, PSW i PC. U kodu kernela postoji statički definisan pokazivač running koji ukazuje na PCB tekućeg procesa. Potprogram scheduler, koji nema argumente, realizuje raspoređivanje, tako što smešta PCB na koji ukazuje pokazivač running u listu spremnih procesa, a iz nje uzima jedan odabrani proces i postavlja pokazivač running na njega. Na asembleru datog procesora napisati kod prekidne rutine ===dispatch koja vrši promenu konteksta korišćenjem potprograma scheduler. ===Ova prekidna rutina poziva se sistemskim pozivom iz korisničkog procesa, pri čemu se sistemski poziv realizuje softverskim prekidom.
### Resenje:
```
dispatch:
	push r0
	push r1
	...
	push r31
	load r0, running
	store SSP, #offsSSP[r0]

	call scheduler

	load r0, running
	load ssp, #offsSSP[r0]
	pop r31
	pop r30
	...
	pop r1
	iret //iret skida sa steka sp, psw i pc, prilikom toga vraca se i na korisnicki stek
```
![[Pasted image 20230428234037.png]]
koristim korisnicki stek k1, pozovem sistemski poziv dispatch za promenu konteksta, prelazim na sistemski stek s1, cuvam kontekst procesa na njemu(registe r0-r31, procesor ce da sacuva sp, psw i pc), prelazim na sistemski stek drugog procesa, i sa njega skidam kontekst 
u sustini, na sistemskog steku svakog procesa se cuvaju njeg registri(ukljucujuci i sp korisnicki) i to je poenta zadatka