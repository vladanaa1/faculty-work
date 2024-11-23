Neki troadresni RISC procesor sa load/store arhitekturom, poput onog opisanog na predavanjima, poseduje 32 registra opšte namene, označenih sa R0..R31, statusnu reč PSW i pokazivač steka SP, koji su dostupni instrukcijama koje se izvršavaju u korisničkom režimu rada procesora, kao i dva posebna registra Rx i Rp koji su dostupni samo u privilegovanom (sistemskom) režimu rada procesora. Registar Rx se može koristiti kao i bilo koji registar R0..R31, i operativni sistem ga može koristiti proizvoljno za sopstvene potrebe (npr. prilikom promene konteksta). Registar Rp se može samo čitati, jer je ožičen tako da njegova vrednost predstavlja jedinstveni identifikator svakog pojedinačnog procesora u multiprocesorskom sistemu. Svi registri su 32-bitni, a adresibilna jedinica je bajt.

Za multiprocesorski sistem sa ovim procesorom pravi se operativni sistem. U kernelu tog sistema postoje sledeće definisane konstante i strukture podataka:

	const int NumOfProcessors = ... // Number of processors
	struct PCB; // Process Control Block
	PCB* runningProcesses[NumOfProcessors]; // Running processes
	//u Rp je index procesora
==U strukturi PCB postoje polja za čuvanje vrednosti svih registara R0..R31, PSW i SP. ==Pomeraji ovih polja u odnosu na početak strukture PCB simbolički označavaju sa offs_r0 itd. Niz runningProcesses, u svakom svom elementu n, sadrži pokazivač na PCB onog procesa koji se trenutno izvršava na procesoru broj n (n=0..NumOfProcesses-1). Na raspolaganju je operacija ==schedule()== (bez argumenata), koja vrši odabir narednog procesa za izvršavanje na procesoru na kome se izvršava i koja upisuje adresu PCB tog procesa u odgovarajući element niza runningProcesses.
1. Na asembleru datog procesora napisati operaciju ==dispatch()== (bez argumenata) koja čuva kontekst tekućeg izvršavanja i restaurira kontekst izvršavanja procesa kome treba dati procesor. U asembleru datog procesora može se koristiti identifikator statički alociranog podatka iz C programa, pri čemu se takva upotreba prevodi u konstantu sa vrednošću adrese tog podatka. Ova operacija se izvršava u kodu kernela, u sistemskom režimu. U ovu operaciju ulazi se iz prekidne rutine koja obrađuje sistemski poziv, pa su prekidi već maskirani (ne treba ih maskirati i demaskirati). 
2. Da li je u ovu operaciju neophodno ubaciti kod za međusobno isključenje konkurentnog izvršavanja od strane različitih procesora tehnikom uposlenog čekanja (spin lock)? Obrazložiti.
ne treba spin lock, jedini deljeni podatak je niz PCB * a proces pristupa samo svom privatnom el niza preko Rp
u schedule treba ubaciti da, mada mi ne znamo kako on radi

u sistemskom rezimu: Rx pomocni registar, Rp sluzi da saznamo koji je redni br procesora
### Resenje:
```
dispatch:
	load Rx, Rp
	//treba nam i pokazivac na tr proces
	shl Rx, 2 //podaci su 4 bajta, znaci pomraj br procesa*4
	load Rx, #runningProcesses[Rx] //u Rx smestamo PCB trenutnog procesa
	store #offsR0[Rx], R0
	...
	store #offR31[Rx], R31
	store #offsPSW[Rx], PSW
	store #offsSP[Rx], SP
	//PC smo sacuvali hardverski?
	call schedule

	Load Rx, Rp
	shl Rx, 2
	Load Rx, #runningProcesses[Rx]
	load R0, #offsR0[Rx]
	...
	load PSW, #offPSW[Rx]
	load SP, #offsSP[Rx]
	rts
	
```