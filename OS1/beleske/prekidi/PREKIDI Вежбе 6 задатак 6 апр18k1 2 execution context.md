Neki procesor obrađuje prekide (hardverske i softverske) tako što tokom izvršavanja prekidne rutine koristi poseban stek koji se koristi u sistemskom, privilegovanom režimu rada procesora, u kome se izvršava kod kernela (čiji je deo i prekidna rutina). Taj stek alociran je u delu memorije koju koristi kernel, a na vrh tog steka ukazuje poseban registar SSP procesora koji je dostupan samo u privilegovanom režimu. Prilikom obrade prekida, procesor na ovom steku čuva programski brojač (PC) i programsku statusnu reč (PSW), tim redom, ali ne i ostale programski dostupne registre. Prilikom povratka iz prekidne rutine instrukcijom iret, procesor restaurira ove registre sa sistemskog steka i vraća se u korisnički režim, a time i na korisnički stek. Postoji samo jedan kernel stek koji se koristi za izvršavanje celog koda kernela. Svi potprogrami kernela pisani su tako da na ovom steku čuvaju (i sa njega potom restauriraju) sve registre procesora koje koriste. Za sve vreme izvršavanja kernel koda prekidi su maskirani (procesor ih implicitno maskira prilikom prihvatanja prekida, a kernel kod ih ne demaskira). U strukturi PCB postoje polja za čuvanje vrednosti svih programski dostupnih registara procesora; pomeraj polja za neki registar Ri u odnosu na početak strukture PCB označen je simboličkom konstantom offsRi (npr. offsSP, offsPC, offsPSW, offsR0, . . . , offsR31). Procesor je RISC, sa load/store arhitekturom i ima sledeće programski dostupne registre: 32 registra opšte namene (R0..R31), SP, PSW i PC. Svi registri su 32-bitni.
U kodu kernela postoji statički definisan pokazivač running koji ukazuje na PCB tekućeg procesa. Potprogram sys_call_proc, koji nema argumente, realizuje obradu sistemskog poziva, kao i raspoređivanje, tako što, nakon obrade samog sisteskog poziva, postavlja pokazivač running da ukazuje na PCB odabranog novog tekućeg procesa.
Na asembleru datog procesora napisati kod prekidne rutine sys_call za sistemske pozive softverskim prekidom, s tim da ona, zbog efikasnijeg rada, ne treba da vrši promenu konteksta (čuvanje i restauraciju registara) ako za tim nema potrebe, odnosno ukoliko procedura sys_call_proc nije promenila pokazivač running.

### Resenje:
```assembler
sys_call:
	push r0 
	push r1
	load r0, [running]// stari running
	call sys_call_proc // u sustini sys call ne vraca isti proces koji se izvrsavao vec nasumicno bira ga iz ready procesa
	load r1, [running]// novi running, moramo da ih uporedimo zato smo ih sacuvali
	cmp r0, r1 //postavlja flag equals
	jne switch // ako nisu isti
	pop r1
	pop r0
	iret // iret ocekuje samo psw i pc na steku
switch:
// cuvamo konteks starom procesa
	store r2, #offsR2[r0]... // u oldPCB stavljas
	store r31, #offsR31[r0]
	store sp, #offsSP[r0]
	pop r1 // sacuvas r1 pomocu r1, stek izgleda kao na slici dole
	store r1, #offsR1[r0]
	pop r1 // sacuvas r0 pomocu r1, zasto ne preko r0? ro se ponasa kao adresa oldPCB strukture u koju smo smestilli
	store r1, #offsR0[r0]
	
	pop r1
	store r1, #offsPSW[r0]
	pop r1
	store r1, #offsPC[r0] //u r1 PC old korisnickog procesa
	// prelazimo na novi kontekst 
	load r0, [running]
	
	load r1, #offsPC[r0] 
	push r1
	load r1, #offsPSW[r0]
	push r1
	
	load sp, #offsSP[r0] // radi jer imamo odvojene SSP i SP
	load r31, #offsR31[r0]
	...
	iret
	
	
```
![[Pasted image 20230428164212.png]]

![[Pasted image 20230428171047.png]]