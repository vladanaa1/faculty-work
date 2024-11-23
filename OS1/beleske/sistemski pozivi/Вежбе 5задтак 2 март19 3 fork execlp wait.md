U nekom operativnom sistemu postoje sledeći sistemski pozivi:

	• fork(), execlp(const char*): kao u sistemu Unix i njemu sličnim;
	• int wait(int pid, unsigned timeout): suspenduje pozivajući roditeljski proces dok se ne završi proces dete sa zadatim PID, ali ga suspenduje najduže onoliko koliko je zadato drugim argumentom (vreme čekanja u milisekundama). Ako je vrednost prvog argumenta NULL, pozivajući proces se suspenduje dok se ne završe sva njegova deca (ili ne istekne vreme čekanja); ako je vrednost drugog argumenta 0, poziv odmah vraća rezultat, bez čekanja. Vraćena vrednost 0 označava da su svi procesi koji su se čekali završili (pre isteka vremena čekanja); vraćena vrednost veća od 0 znači da je vreme čekanja isteklo pre nego što je neki od procesa koji su se čekali završili.
	• int kill(int pid): gasi proces sa zadatim PID.
Svi ovi sistemski pozivi vraćaju negativan kod greške u slučaju neuspeha. 
Na jeziku C napisati program koji se poziva sa jednim argumentom koji predstavlja stazu do exe fajla. Ovaj program treba da kreira N procesa koji izvršavaju program u exe fajlu zadatom argumentom (N je konstanta definisana u programu), a potom da čeka da se svi ti procesi-deca završe u roku od 5 sekundi. Sve procese-decu koji se nisu završili u tom roku treba da ugasi.
Obraditi sve greške u sistemskim pozivima ispisom odgovarajuće poruke.
```c
const int n = ...
const unsigned timeout 5000;
int pid[n]

int main(int argc, const char* argv[]){
	if(argc<2) exit(-1);
	int ret = 0; //?

	for(int i = 0; i<n;i++){
		pid[i] = fork();
		if(pid[i]<0){//fork nije uspeo
			printf("Err");
			ret = -2;
		}else if(pid[n]==0){//ovu granu vrsi samo proces dete
			execlp(argv[1]);
			printf("Error");
			exit(-1);//prekine samo proces dete
		}
	}

	int stat = wait(NULL,timeout);//cekamo da se zavrse sva deca timeout milisekundi
	//nije mi jasno wait skroz, ustv pise u tekstu sta radi ako je arg null
	if (stat<0) {
		printf(“Error: Failed to wait for the children processes.\n”);
		ret -= 4; //moze i ret =  -4 
	}
	
	if (stat == 0) { printf(“All children completed in time.\n”,i); exit(0); }
	
	for (int i=0;i<n;i++){
		stat = wait(pid[i], 0); //stat ce biti 0 ako je proces vec zavrsen, wait time stagod kao arg je 0
		if (stat == 0) continue; // Child completed, nastavi dalje
		if (stat<0) {
			printf(“Error: Failed to wait for the child process number %d.\n”,i);
			if (ret > -8) ret -= 8;
		}
		stat = kill(pid[i]); // znaci da se proces nije zavrsio
		if (stat<0) { printf(“Error: Failed to kill the child proces number%d.\n”,i); 
			if (ret > -16) ret -= 16; 
		}
		
	}
	exit(ret);

}
```