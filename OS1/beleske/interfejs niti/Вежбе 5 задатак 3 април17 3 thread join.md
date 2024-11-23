U standardnom jeziku C++ postoje, između ostalog, sledeći elementi podrške za niti: 

	• std::thread: klasa kojom se predstavljaju niti; nova nit se pokreće odmah po kreiranju nekog objekta ove klase;
	• std::thread::thread: konstruktor klase std::thread koji kreira nit nad funkcijom na koju ukazuje prvi argument; ta funkcija prima jedan argument i ne vraća rezultat; novokreirana nit poziva datu funkciju sa stvarnim argumentom jednakim drugom argumentu konstruktora;
	• std::thread::join: suspenduje pozivajuću nit dok se ne završi nit čija se ova funkcija poziva;
	• thread_local: specifikator životnog veka i načina alokacije koji se navodi u deklaraciji objekta; objekti ove kategorije životnog veka alociraju se kad god se kreira nova nit i nestaju sa završetkom te niti; svaka kreirana nit, uključujući i „glavnu“ nit kreiranu nad pozivom funkcije main, ima svoju sopstvenu kopiju (instancu) ovog objekta, različitu od instanci tog objekta u drugim nitima.

Precizno navesti šta sve može da ispiše sledeći C++ program (izlazne operacije ispisa na standardni izlaz su atomične).
```c++
thread_local int i=0; //svaka nit ima svoje i
void f (int id) { 
	i=id;
	++i;
	std::cout<<i;
}

void main() {
	i=9;
	std::thread t1(f,1);
	std::thread t2(f,2);
	std::thread t3(f,3);
	t1.join(); //main(pozivajuca nit) se blokira sve dok se ne zavrsi nit ciji se join poziva(t1)
	t2.join();
	t3.join();
	std::cout<<i<<std::endl;//u sustini main ceka da se zavrse t1 t2 t3 proizvoljnim redosledom pa se tek onda ispisuje 9(i maina) pa su redosledi 2349,3249,4329...
}
```
