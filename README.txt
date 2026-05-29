Tommaso Ruzzante mat.2109912
Progetto Programmazione di Sistemi Embedded
Ingegneria Informatica UNIPD 2025/26

Modello usato per i test:
Samsung A14
Android Versione 15 -> API Level 35

Panoramica sul progetto:
Il progetto consiste nella realizzazione di un'app Android che riproduca il gioco Simon. A differenza dell'originale questa versione ha 6 tasti colorati. L'app implementa inoltre uno storico in cui vedere tutte le partite giocate da quando l'app è stata installata.

Scelte implementative:
Il database è stato creato con Room, che supporta l'esecuzione asincrona garantendo che gli accessi al database non bloccassero il thread principale della UI. Database e logica di gioco sono tra loro separate e le interazioni avvengono mediante una Repository.
Il cuore operativo della logica di gioco è rappresentato dal ViewModel, il quale estende AndroidViewModel per poter accedere in modo sicuro all'Application Context. La comunicazione tra interfaccia (realizzata in Compose) e ViewModel avviene tramite StateFlow: il ViewModel espone gli stati dei dati rilevanti in lettura e l'interfaccia osserva questi flussi, reagendo di conseguenza ai cambiamenti di stato. Come richiesto dalle specifiche è stato integrato il SavedStateHandle per gestire la distruzione dell'Activity causata dal recupero risorse di Android. I dati principali vengono scritti nel bundle di persistenza a basso livello e recuperati al ripristino dell'app riprendendo questi dati, qualora ci fosse effettivamente una partita in corso, nel blocco init del ViewModel.
Il ciclo di gameplay è gestito tramite le coroutine di kotlin che coordinano feedback visivi e uditivi quando i pulsanti vengono mostrati dalla cpu o premuti dall'utente. Il salvataggio dei progressi dell'utente permette inoltre coerenza con la stringa della partita che viene mostrata a schermo e con  quella che verrà poi mostrata al termine della partita nella schermata principale e di dettaglio partita. In queste schermate la stringa rappresenta la sequenza di bottoni che l'utente avrebbe dovuto premere in quello che è stato il suo ultimo turno di gioco. La parte colorata di verde rappresenta i bottoni che l'utente ha premuto correttamente, mentre la parte restante, colorata di rosso, sono i bottoni che l'utente ha sbagliato e/o non ha premuto.
Per la gestione dell'audio è stato scelta la classe SoundPool per la bassa latenza, che permette all'audio di partire subito dopo il click, la facilità d'uso, sia per quanto riguarda l'utilizzo di semplici file mp3 che per l'implementazione, e per la gestione risorse, ottima per file di breve durata come in questo caso.