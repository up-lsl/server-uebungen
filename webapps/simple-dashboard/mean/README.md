# Dashboard in MEAN

Dieser Branch enthält die fertige Dashboard-Aplikation in MEAN.

## Hinweise 

* Achten Sie darauf, dass die Informationen für den Datenbank Login in __server.js__ eingetragen werden müssen!
	* __user__    -  Der Benzutername des Datenbanknutzers.
	* __pass__     -  Das Passwort des Datenbanknutzers.
* Damit die Daten aus der Datenbank abgefragt werden können müssen die benötigten Collections zunächst angelet werden.
    * __kunden__   -  Enthält Dokumente mit den Einträgen id, name
	* __produkte__  -  Enthält Dokumente mit den Einträgen id, name, bestand
* Auf Ihrem Server müssen die notwendigen Komponenten des MEAN-Stacks installiert sein.
    * __mongoDB__   -  [mongoDB](https://www.mongodb.com/) ist ein Datenbanksystem für dokumentenbasierte Datenbanken.
	* __express__  -  [express](https://expressjs.com/) ist ein open-source backend Application-Framework für node.js.
	* __Angular__   -  [Angular](https://angular.io/) ist ein open-source frontend Application-Framework.
	* __node.js__     -  [node.js](https://www.php.net/) ist eine open-source Laufzeitumgebung für JavaScript die serverseitig instaliert wird.


## Struktur

Dieser Branch enthält folgende Dateien:


    .
    ├── models                    # Definition der Dokuments und ihren Collections analog zur DB.
	│     ├── Kunde.js            # Definiert das Objekt "Kunde".
	│     └── Produkt.js          # Definiert das Objekt "Produkt".
    ├── static	                  # Die statischen Dokumente die der node-Server ausliefern soll.
	│     ├── app.js              # Enthält das Angular-Modul mit den benötigten Services für das Dashboard.
	│     ├── index.html          # Die HTML-Struktur. Enthält Angular-Anweisungen.      
	│     └── style.css           # Enthält Informationen zur Darstellung der HTML-Elemente.
    ├── package.json              # Enthält Meta-Informationen zur App, insbesondere zu den benötigten externen node-Modulen.
    ├── package-lock.json         # Enthält Meta-Informationen zu den Abhängigkeiten der benötigten externen node-Modulen.
    ├── server.js                 # Enthält das Setup für den node-Server mit express-Routes für die API zur DB.
    └── README.md