U sistemu UNIX i sličnim sistemima sistemski poziv

	int execvp(const char * filename, char * const args[]);

When a process **calls** the **execlp** or one of the other 7 **exec** functions, that process is completely replaced by the new program, and the new program starts executing at its main function. The process ID does not change across an **exec** , because a new process is not created.
	
radi isto što i poziv execlp, s tim što drugi argument predstavlja niz pokazivača na nizove znakova (null-terminated strings) koji predstavljaju listu argumenata poziva programa koji treba izvršiti. Po konvenciji, prvi argument pokazuje na naziv fajla samog programa koji se izvršava. Ovaj niz pokazivača mora da se završi elementom sa vrednošću NULL.

U nastavku je dat jedan neispravan program. Namera programera je bila da sastavi program koji prima jedan celobrojni argument (po konvenciji, u programu se on vidi kao drugi argument, pored naziva programa), i da, samo ukoliko je vrednost celobrojnog argumenta veća od 0, kreira proces-dete nad istim programom i sa za jedan manjom vrednošću svog celobrojnog argumenta, potom ispiše svoju vrednost argumenta i sačeka da se proces-dete završi, a onda se i sam završi. Proces-dete radi to isto (i tako rekurzivno). Napisati ispravnu implementaciju ovog programa.

```c++
void main (int argc, char* const argv[]){
if (argc<2) return; // Exception!
// Get the argument value: 
int myArg = 0; // treba da ucita jedini arg koji predst id processa
sscanf(argv[1],“%d“,&myArg);
if (myArg<=0) return; 

// Prepare the arguments for the child:
char childArg[10]; // The value of the second argument sistemskog poziva execvp
sprintf(childArg,“%d“,myArg-1); // u childArg prekopiramo vrednost myArg umanjenu za 1 tj id procesa deteta
char* childArgs[3];
childArgs[0] = argv[0];//naziv programa
childArgs[1] = childArg;//id
childArgs[2] = NULL;// u teksu zadatka kaze da niz tj argument mora da se zsavrsi sa NULL

// Create a child: 
execvp(argv[0],childArgs);//problem u tome sto se dole dve fje nikad nece izvrsiti ako uspe poziv execvp; execvp samo kreira novi program gde krece izvrsavanje ispocetka ostaje jedan tok kontrole, nama trebaju 2 toka kontrole
printf(“%s\n“,myArg); // ispisi argumentroditelja
wait(NULL);//sacekamo da se dete zavrsi

}
```

### Resenje:
```c++
// Create a child: 
int status = fork();
if (status<0) exit(); // Exception!
if (status==0) // Child's context
	execvp(argv[0],childArgs);
else { // Parent's context
	printf(“%d\n“,myArg);
	wait(NULL); }
}
```