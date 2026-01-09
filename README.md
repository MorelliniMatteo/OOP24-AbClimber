# Abyss Climber

## Membri del gruppo

- Andrea Ciani — [andrea.ciani9@studio.unibo.it](mailto:andrea.ciani9@studio.unibo.it)  
- Artur Turchyn — [artur.turchyn@studio.unibo.it](mailto:artur.turchyn@studio.unibo.it)  
- Matteo Morellini — [matteo.morellini@studio.unibo.it](mailto:matteo.morellini@studio.unibo.it)

---

## Descrizione del progetto

**Abyss Climber** è un gioco di tipo *roguelike/RPG* in cui il giocatore deve affrontare una serie di piani sempre più difficili, sconfiggendo nemici e boss che custodiscono l’accesso al livello successivo.  
L’obiettivo finale è raggiungere e sconfiggere il boss dell’ultimo piano, completando così l’ascesa attraverso l’abisso.

Il progetto mira a sviluppare un prototipo funzionante con meccaniche di combattimento a turni, progressione del personaggio, gestione delle risorse e un’interfaccia utente chiara e intuitiva.

---

## Funzionalità principali

- Interfaccia di gioco e menu principale  
- Sistema di combattimento con:
  - intelligenza artificiale dei nemici  
  - gestione dei turni  
  - utilizzo di abilità e oggetti  
- Sistema di progressione del giocatore basato sugli oggetti raccolti  
- Gestione della difficoltà (scelta iniziale e incremento progressivo per piano)  
- Struttura a stanze con tipologie diverse:
  - combattimento  
  - negozio  
  - scelta porta  
- Definizione funzionale di abilità, oggetti e attributi del giocatore  

---

## Funzionalità opzionali

- Menu di cheat code  
- Supporto a metodi di input alternativi (es. controller)  
- Sistema audio con colonna sonora e effetti sonori di combattimento  
- Animazioni (immagini animate e piccoli movimenti delle entità)  
- Oggetti con abilità attive utilizzabili in combattimento  
- Tracciamento e collezione degli oggetti trovati nelle partite precedenti  

---

## Challenge previste

- Implementazione del sistema di combattimento e dell’intelligenza artificiale dei nemici  
- Gestione e bilanciamento degli oggetti e dei relativi attributi  
- Progettazione e realizzazione dell’interfaccia grafica  
- Corretta separazione tra logica di gioco e interfaccia utente  
- Integrazione coerente dei diversi sistemi e coordinamento tra i moduli  

---

## Suddivisione del lavoro

- **Andrea Ciani**  
  Schermata oggetti gratuiti (uno per piano), negozio per acquistare oggetti, gestione degli attributi di giocatore e mostri, lettura da file degli attributi di mostri e oggetti.  

- **Artur Turchyn**  
  Gestione della difficoltà, sviluppo dell’intelligenza artificiale dei nemici, sistema di combattimento e gestione dei turni.  

- **Matteo Morellini**  
  Gestione del caricamento delle immagini e degli assets, sviluppo del menu principale, schermata di scelta della stanza (percorso) e gestione dello stato del giocatore (morte e riavvio).  

---

## Tecnologie previste

- **Linguaggio:** Java (JDK 21)  
- **Build system:** Gradle  
- **IDE consigliato:** Visual Studio Code con estensioni Java  
- **Controllo di versione:** GitHub  

---

## Avvio del progetto

1. Clonare il repository:
   ```bash
   git clone https://github.com/MorelliniMatteo/abyss-climber.git
   cd abyss-climber
