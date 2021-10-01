# Dashboard in LAMP

Dieser Branch enthält die fertige Dashboard-Aplikation in LAMP.

## Hinweise 

* Achten Sie darauf, dass die Informationen für den Datenbank Login in __data.php__ eingetragen werden müssen!
    * __$dbname__  -  Der Name der Datenbank.
	* __$user__    -  Der Benzutername des Datenbanknutzers.
	* __$pwd__     -  Das Passwort des Datenbanknutzers.
* Damit die Tabellen aus der Datenbank abgefragt werden können müssen diese zunächst angelet werden.
    * __kundentabelle__   -  Enthält die Spalten __id__, name
	* __produkttabelle__  -  Enthält die Spalten __id__, name, bestand
* Auf Ihrem Server müssen die Komponenten des LAMP-Stacks installiert sein.
    * __Linux__   -  Im Serverpraktikum setzen wir auf [Ubuntu Server](https://ubuntu.com/download/server).
	* __Apache__  -  [Apache](https://httpd.apache.org/) ist ein open-source HTTP server.
	* __mySQL__   -  [MySQL](https://www.mysql.com/) ist ein open-source Datenbankmanagementsystem für relationale Datenbanken.
	* __PHP__     -  [PHP](https://www.php.net/) ist eine Skriptsprache für die serverseitige Anwendung.



## Struktur

Dieser Branch enthält folgende Dateien:


    .
    ├── api.php                   # Wickelt den Aufruf einer externen API ab.
    ├── build_data_table.php	  # Bereitet Daten für die Darstellung vor.
    ├── data.php                  # Hier werden Datenbankabfragen verwaltet.
    ├── index.php                 # Diese Datei wird beim initialen Aufruf abgefragt.   
    ├── script.js                 # Enthält Skripts, die clientseitig ausgeführt werden.
    ├── style.css                 # Enthält Informationen zur Darstellung der Webseite.
    └── README.md
