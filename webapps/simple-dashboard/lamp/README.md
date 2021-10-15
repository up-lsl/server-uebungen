# Dashboard in LAMP

Dieser Branch enthält die fertige Dashboard-Aplikation in LAMP.

## Hinweise 

* Achten Sie darauf, dass die Informationen für den Datenbank Login in __dbconn.php__ eingetragen werden müssen!
    * __$dbname__  -  Der Name der Datenbank.
	* __$user__    -  Der Benutzername des Datenbanknutzers.
	* __$pwd__     -  Das Passwort des Datenbanknutzers.
* Damit die Tabellen aus der Datenbank abgefragt werden können müssen diese zunächst angelegt werden.
    * __kundentabelle__   -  Enthält die Spalten __id__, name
	* __produkttabelle__  -  Enthält die Spalten __id__, name, bestand
* Auf Ihrem Server müssen die Komponenten des LAMP-Stacks installiert sein.
    * __Linux__   -  Im Serverpraktikum setzen wir auf [Ubuntu Server](https://ubuntu.com/download/server).
	* __Apache__  -  [Apache](https://httpd.apache.org/) ist ein open-source HTTP server.
	* __mySQL__   -  [MySQL](https://www.mysql.com/) ist ein open-source Datenbankmanagementsystem für relationale Datenbanken.
	* __PHP__     -  [PHP](https://www.php.net/) ist eine Skriptsprache für die serverseitige Anwendung.



## Struktur

Dieser Branch enthält folgende Dateien:


    ../dashboard_lamp
    ├── api.php					# Interagiert mit der Datenbank
    ├── build_data_table.php	# Bereitet Daten für die Darstellung vor
    ├── dbconn.php				# Enthält die Datenbank-Verbindungs-Informationen
    ├── index.php				# Hauptdatei, die Funktionen aufruft
    ├── README.md				# Einfache LiesMich-Datei
    ├── script.js				# Enthält Skripts, die clientseitig ausgeführt werden
    ├── style.css				# Enthält Designinformationen zur Darstellung
    └── witz.php				# Wickelt den Aufruf einer externen Witz-API ab
