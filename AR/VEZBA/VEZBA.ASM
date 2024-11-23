! inicijalizacija simulacije
dc x.1, x.9996
dc x.0, x.9998
onkp false, x.1, x.1
onkp false, x.1, x.1
onkp true, x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
kpreg 1.1, r0, x.1
kpreg 1.1, r1, x.2
kpreg 1.1, r2, x.3
kpreg 1.1, r3, x.4
kpreg 1.1, r4, x.5
kpreg 1.1, r5, x.6
kpreg 1.1, r6, x.7
kpreg 1.1, r7, x.8
kpreg 2.1, r0, x.9
kpreg 2.1, r1, x.a
kpreg 2.1, r2, x.b
kpreg 2.1, r3, x.c
kpreg 2.1, r4, x.d
kpreg 2.1, r5, x.e
kpreg 2.1, r6, x.f
kpreg 2.1, r7, x.10
reg pc, x.4000
reg ivtp, x.4000
reg sp, x.9000
! kraj inicijalizacije

org x.4000
ldimm x.200,r1
mvrir r1, ivtp
ldimm x.500, r1
stmem x.205, r1
ldimm x.1000, r1
stmem x.204, r1
ldimm x.1500, r1
stmem x.203, r1
ldimm x.2000, r1
stmem x.202, r1
ldimm x.2500, r1
stmem x.200, r1
ldimm x.3000, r1
stmem x.201, r1

ldimm x.5000, r1 !pokazivac na niz A
ldimm x.6000, r2 !pokazivac na niz B
ldimm x.8, r3 !brojac za niz A
ldimm x.8, r4 !brojac za niz B
!startovanje periferija kp1 i kp2
ldimm x.5, r5 !upisivanje vrednosti 5 u kontrolne reg,
! start = 1 i ulaz = 1
stmem x.f100, r5
ldimm x.5, r5
stmem x.f200, r5 
ldimm x.1, r5

loop1:
ldmem x.f101, r6
and r6, r6, r5
beql loop1
ldmem x.f103, r7
stri [r1], r7
inc r1
dec r3
bneq loop1

loop2:
ldmem x.f201, r6
and r6, r6, r5
beql loop2
ldmem x.f203, r7
stri [r2], r7
inc r2
dec r4
bneq loop2

!iskljucivanje periferija kp1 i kp2
ldimm x.0000, r0 ! zbog ovoga ti mzd nece raditi
stmem x.f100, r0
stmem x.f200, r0

ldimm x.5000, r1
ldimm x.6000, r2
ldimm x.8, r3

!u r0 je nula ne diraj ga
!priprema steka za poziv fje sumAll
push r3 ! cnt
push r2 ! * A
push r1 ! * B
jsr sumAll
pop r1
pop r2
pop r3 !moramo da ocistimo sstek tj skinemo ona 3 arg
stmem x.9999, r0 !sacuvamo rez fje na 9999h
!slanje niza B koriscenjem dma 1.4 uredjaja
!u paketskom rezimu rada

ldimm x.8, r5
stmem x.f0c4, r5 !okej
ldimm x.6000, r5
stmem x.f0c5, r5 
ldimm x.6100, r5
stmem x.f0c6, r5 
ldimm x.1, r5 ! smestis u entry registar dma4
stmem x.F0C2, r5
ldimm x.00be, r5 
stmem x.f0c0, r5

!ceaknje semafora za dma 4.1
ldimm x.1, r5
wait1: ldmem x.3ff0, r6
cmp r6, r5
bneq wait1

! slanje niza A dma 1.1 u burst rez i slanje vrednosti sa 
! MEM LOk 9999h uredjaju dma 1.2 u cycle stealing rez

ldimm x.5000, r5
stmem x.f005, r5 !ar1 izvorisni registar
ldimm x.8, r5
stmem x.f004, r5 ! upis u count registar
ldimm x.3, r5
stmem x.f002, r5 ! upis u entry registar
ldimm x.00ae, r5 
stmem x.f000, r5 !upis u kontrolni registar

ldimm x.1, r5
wait2: ldmem x.3ff0, r6
cmp r5, r6
bneq wait2

ldimm x.9999, r5
stmem x.f045, r5
ldimm x.1, r5
stmem x.f044, r5
ldimm x.0, r5
stmem x.f042, r5
ldimm x.000e, r5
stmem x.f040, r5

ldimm x.1, r5
wait3: ldmem x.3FF0, r6
cmp r5, r6
bneq wait3

halt

sumAll:
push r5
mvrpl r5, sp

push r1
push r2
push r3
push r4
push r6

ldrid[r5]x.2, r1 !A
ldrid[r5]x.3, r2 !B
ldrid[r5]x.4, r3 !size

ldrid[r1]x.0, r0
ldrid[r2]x.0, r4
add r0, r0, r4 ! A[0] + B[0]
loop:
inc r1
inc r2
dec r3
beql izadji
ldrid [r1]x.0, r4
ldrid [r2]x.0, r6
add r4, r4, r6
add r0, r0, r4
jmp loop

izadji:
pop r6
pop r4
pop r3
pop r2
pop r1
pop r5
rts

org x.3000 !prekidna rutina dma
push r0
ldimm x.1, r0
stmem x.3ff0, r0 !postavljenje semafora
clr r0
stmem x.f0c0, r0 !upis u kontrolni registar
pop r0
rti


org x.1500 !prekidna rutina za dma1.1
push r0
ldimm x.1, r0
stmem x.3ff0, r0
clr r0
stmem x.f000, r0 !upis u kontrolni reg
pop r0
rti


org x.2500 !prekidna rutina za dma1.2
push r0
ldimm x.1, r0
stmem x.3ff0, r0
clr r0
stmem x.f040, r0 !upis u kontrolni reg
pop r0
rti