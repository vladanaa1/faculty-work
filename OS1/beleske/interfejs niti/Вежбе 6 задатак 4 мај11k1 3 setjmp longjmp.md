nema teksta na pdfu 
Nekorektna implementacija setjmp i longjmp
svi registri 32bitni, adrese 32bitne, adresibilna jedinica je bajt 
prevodilac povratnu vrednost fje prenosi kroz registar r0? //setjmp treba da vrati 0?
### Resenje:
```c++
int setjmp(jmp_buf buf){
	asm{
		clr r0;
		push r1;
		load r1, -2*4[sp]; //r1 = buff, vidi sliku
		store sp, 0*4[r1];
		store psw, 1*4[r1];
		store r2, 2*4[r1];
		store r3, 3*4[r1];
		...
		store r31, 31*4[r1];
		store pc, 32*4[r1]; 
		pop r1;//*odavde
	}
}

void longjmp(jmp_buf buf, int val){
//struktura buf kontekst iz kojeg kupimo registre i uradimo restauraciju procesa
	asm{
		load r0, -2*4[sp]
		and r0, r0, r0
		jnz continue
		load r0, #1
	continue:
		load r1, -1*4[sp]
		load sp, 0*4[r1]
		load psw, 1*4[r1]
		load r2, 2*4[r1]
		load r3, 3*4[r1]
		...
		load r31, 31*4[r1]
		load pc, 32*4[r1]// poslednje sto se radi u longjmpu je da se kupi pc i onda sa te adrese se nastavlja izvrsavanje programa tj *
	}
}
```
![[Pasted image 20230429130422.png]]
primer zasto ovo ne vallja je dispatch() iz skolskog jezgra, on nakon poziva setjmp ima poziv druge fje Scheduler koja moze da "ubrlja" stek i postavi losu vrednost na mesto odakle kupimo r1, r1 jedini nije sacuvan u strukturi buf