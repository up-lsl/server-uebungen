<?php
//Angabe der Verbindungsdaten zur MySQL-Datenbank
$mysqlhost="localhost"; //Als IP-Adresse wird "localhost" angegeben, da sich Apache und MySQL auf demselben Server befinden.
$dbname="dashboarddb";
$dbuser="dbuser";
$dbpass="dbuser123";



// Die Verbindung zur Datenbank wird aufgebaut.
$pdo = new PDO("mysql:host=".$mysqlhost.";port=3306;dbname=".$dbname, $dbuser, $dbpass);

// Alle Tabelleneinträge werden selektiert und in die Variable "result" gespeichert
// (Der SQL-Befehl wird mit "$pdo->query()" an die Datenbank gesendet).
$result = $pdo->query("SELECT id, name FROM kundentabelle");

// Als Antwort werden die Zeilen der Tabelle zurückgeliefert.
// Mittels Arrays können die Zeilen jeweils durchlaufen und die Werte ausgegeben werden.

foreach ($result as $row) {
	//Nach dem Eintrag der Spalte "id" wird ein Leerzeichen eingefügt; erst danach wird der Eintrag der Spalte "name" ausgegeben.
	print $row["id"]. "\t"; print $row["name"];

	// Eine neue Zeile wird immer zwischen unterschiedlichen Datensätzen eingefügt.
	print "<br/>"; 
}
?>
