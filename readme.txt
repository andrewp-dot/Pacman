Popis hry:
Po spuštění se otevře hlavní menu.
* play - Otevře seznam jednotlivých map, vybráním mapy se dostanete do hry.
* settings - Otevře herní nastavení.
    * nastavení continuous movement - Zaškrtnutím políčka se pacman při
    udání směru bude pohybovat, tak dlouho než trefí stěnu nebo se udá nový směr.
* replays - Otevře seznam předchozích záznamů her.
* exit - Ukončí hru.

GUI:
Pro vytovření GUI byl použit modul javafx.

STRUKTURA:
Soubory jsou rozděleny do několika balíků:
* changelog - Nahrává záznam o předchozí hře.
* game - Obsahuje veškerou vnitřní logiku hry.
* styles - CSS soubory pro stylizaci GUI.
* utils - Obsahuje rozhraní pro model Observer a Observable.
* view - Obsahuje jednotlivá okna hry.

Kompilácia:
mvn install
mvn package

Spustenie:
mvn exec:java
