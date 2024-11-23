Data je sledeća implementacija operacije Semaphore::lock() koja obezbeđuje zaključavanje semafora (međusobno isključenje operacija na semaforu) u kodu školskog jezgra za višeprocesorski sistem. Operacija swap() implementirana je pomoću odgovarajuće atomične instrukcije procesora. Objasniti zašto ova implementacija operacije lock() nije dobra, a onda je popraviti.

```c++
extern void swap (int*, int*);//glob fja
void Semaphore::lock(){ //lock je atomiccan
	int zero = 0;
	mask_interrupts(); //zabrana sp maskirajucih prekida
	while (!this->lck) {} //uposleno cekanje busy waiting
	swap(&zero,&(this->lck)); //zero u atribut lock a this->lck u zero
}
```
Problem je u tome sto je ovo viseprocesorski sistem. metodu lock moze izvrsavati istovremeno vise procesa, vise procesa moze istovreemeno da prodje while i swap i da udje u kriticnu sekciju. tj mogu obe istovr da izadju iz while pa onda jedna pa druga swap jer je swap atomicno al opet jbg obe usle u kriticnu sekciju
```c++
void Semaphore::lock() {
	int zero = 0;
	mask_interrupts();
	while (!zero) swap(&zero, &(this->lck));
}
```
ako obe istovr izadju iz whilea jedna pa druga rade swap jer je swap atomicna prva koja uradi swap ulazi u kriticnu sekciju 