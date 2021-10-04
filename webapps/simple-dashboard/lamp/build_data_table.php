<?php
//Datenbankzugriffe werden in api.php verwaltet.
include_once "api.php";

//Hier werden HTML-Tabellen zum Anzeigen von Datenbankinhalten erstellt.

//Erstellt eine Tabelle aus Kundendaten aus der Tabelle mit dem übergebenen Namen.
function erstelle_tabelle_kunden($name_tabelle) {
	//Frage benötigte Daten aus Datenbank ab.
	$data = get_data($name_tabelle);
	
	//Ergebnis afbereiten.
	print "<table class='datatable'>";
	//Tabellenkopf erzeugen.
	print "	<thead>";
	print "		<tr>";
	print "			<th> ID </th>";
	print "			<th> Name </th>";
	print "		</tr>";
	print "	</thead>";
	//Hier werden die einzelnen Zeilen des enmpfangenen Datensatzes durchlaufen.
	print "	<tbody>";
	foreach ($data as $row) {
		//Zeile ausgeben.
		print "	<tr>";
		//Strings können mit '.' zusammengefügt werden.
		//Dies ist vor allem dann nützlich wenn mit Variablen gearbeitet wird.
		print "		<td>".$row["id"]."</td>";
		print "		<td>".$row['name']."</td>";
		print "	</tr>";
	}
	//Ein Forumlar kann mithilfe von 'action' direkt Anfragen an ein PHP-Skript stellen.
	print "		<form action='api.php' method='post'>";
	//Füge eine Zeile hinzu, um die Eingabe weiterer Kundendaten zu ermöglichen.
	print "			<tr>";
	//Die eingegebenen Werte aus den input feldern werden dabei der Anfrage hinzugefügt.
	print "				<td> <input type='number' name='id'> </td>";
	print "				<td> <input type='text' name='name'> </td>";
	print "			</tr>";
	print "			<tr>";
	//Dazu wird das input-Element mit type='submit' benötigt, um die Anfrage zu versenden.
	print "				<td colspan='2'> <input type='submit'> </td>";
	print "			</tr>";
	print "		</form>";
	print "	</tbody>";
	print "</table>";
}

//Erstellt eine Tabelle aus Produktdaten aus der Tabelle mit dem übergebenen Namen.
function erstelle_tabelle_produkte($name_tabelle){
	//Frage Daten aus Datenbank ab.
	$data = get_data($name_tabelle);
	
	//Ergebnis aufbereiten.
	print "<table class='datatable'>";
	//Tabellenkopf erzeugen.
	print "	<thead>";
	print "		<tr>";
	print "			<th> Name </th>";
	print "			<th> Verfügbar </th>";
	print "		</tr>";
	print "	</thead>";
	print "	<tbody>";
	foreach ($data as $row) {
		//Zeile ausgeben.
		print "	<tr>";
		print "		<td>".$row['name']."</td>";
		print "		<td class='wide'>";
		//Füge Buttons hinzu, um den Lagerbestand zu senken, bzw. zu erhöhen.
		print '         <button onclick="reduziereBestand('.$row["id"].')">-</button>';
		print '         <div id=bestand_'.$row["id"].'>'.$row["bestand"].'</div>';
		print '         <button onclick="erhoeheBestand('.$row["id"].')">+</button>';
		print"		</td>";
		print "	</tr>";
	}
	print "	</tbody>";
	print "</table>";
}
?>
