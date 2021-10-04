<?php
//Hier werden Datenbankinhalte verwaltet.

//einbinden der Dankenbank-Verbindungsdatei
include_once "dbconn.php";

//Überprüfe den Grund für den Aufruf:
if($_POST['id'] && $_POST['name']){
	//Aufruf, um Zeile in Kundentabelle zu schreiben.
	insert_data_kunden($_POST);
	header('location: index.php');
}else if($_POST['id'] && $_POST['bestand']){
	//Aufruf, um Zeile in Produkttabelle zu schreiben.
	update_bestand_produkte($_POST);
}else{
	//Grund für Aufruf nicht ersichtlich.
	//Schreibt Zeile in die Datei '/var/log/apache2/error.log' auf Ihrem Server.
	error_log("Es ist ein Fehler aufgetreten:".$_POST['data']);	
}

//Lese Daten aus Datenbank.
function get_data($table_name){
	try{
		//Datenbankverbindung hestellen.
		$conn = get_db_conn();

		//Sorgt für lesbare Fehlermeldung.
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

		//SQL Befehl vorbereiten.
		$selectQuery = 'SELECT * FROM '.$table_name;
		//$selectQuery = 'SELECT * FROM :table_ame';

		$selectStatement = $conn->prepare($selectQuery);
		//$selectStatement->bindParam(':tableName', $table_name, PDO::PARAM_STR);

		$selectStatement->execute();
		//$selectStatement->execute([$table_name]);

	    //Wenn das Ergebnis Zeilen enthält, solle die Ergebisse zurückgegeen werden.
		if($selectStatement->rowCount()){
			//Speichere alle Zeilen in result.
			$result = $selectStatement->fetchAll();
		}else{
			print "Keine Ergebnisse";
		}

		//Datenbankverbindung trennen.
		$conn = null;

		//Ergebnis zurückgeben.
		return $result;

	} catch(PDOException $e){
		print "Connection failed: " . $e->getMessage();
	}
}

function insert_data_kunden($data){
	try{
		//Datenbankverbindung hestellen.
		$conn = get_db_conn();
	 
		//SQL Befehl ausführen.
		$insert_query = "INSERT INTO kundentabelle (id,name) VALUES(".$data['id'].",'".$data['name']."')";

		$conn->query($insert_query);
	
		//Datenbankverbindung trennen.
		close_db_conn($conn);
	
	} catch(Exception $e){
		echo "Insert failed: " . $e->getMessage();
	}
}

function update_bestand_produkte($data){
	try{
		//Datenbankverbindung hestellen.
		$conn = get_db_conn();

		//SQL Befehl ausführen.
		$insert_query = "UPDATE produkttabelle SET bestand =".$data["bestand"]." WHERE id = ".$data["id"];

		$conn->query($insert_query);

		//Datenbankverbindung trennen.
		close_db_conn($conn);
	
	} catch(Exception $e){
		echo "Insert failed: " . $e->getMessage();
	}
}

?>
