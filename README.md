# Progetto di ingegneria del software AA 2019/20 - Gruppo PSP38

## Componenti del gruppo
* Maximilien Groh (10683107)
* Davide Mantegazza (10568661)
* Matteo Mita (10487862)

## Funzionalità implementate
- Regole complete
- CLI
- Socket
- GUI
- Divinità avanzate (Ares, Charon, Hera, Hestia, Zeus)

##Istruzioni per l'esecuzione
Per eseguire il programma è necessario Java Developement Kit 13 o superiore

###Avvio del server
Il server deve essere avviato da terminale tramite il comando

    java -jar SantoriniServer.jar

###Avvio del client
Il client può essere eseguito sia con un'interfaccia grafica (GUI) che da linea di comando (CLI). In quest'ultimo caso il client va 
necessariamente lanciato da terminale con il comando

    java -jar SantoriniClient.jar cli
    
Per un'esecuzione ottimale della CLI su Windows è raccomandato l'uso di [Windows Terminal](https://www.microsoft.com/it-it/p/windows-terminal/9n0dx20hk701?activetab=pivot:overviewtab),
adoperando la Powershell come terminale
##Note
Client e server comunicano tra di loro attraverso la porta 3457. Eventualmente è possibile cambiare il numero di porta
ricompilando il codice 