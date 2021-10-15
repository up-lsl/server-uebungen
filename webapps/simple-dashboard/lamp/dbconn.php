<?php

//Funktion, die die Datenbankverbindung zurückliefert
function get_db_conn() {
	$host = 'localhost';
	$port = 3306;
	$dbname = 'dashboarddb';
	$user = 'dbuser';
	$pwd = 'dbuser123';

	//Verbindung aufbauen
	$conn = new PDO("mysql:host=$host;port=$port;dbname=$dbname", "$user", "$pwd");

	return $conn;
}

//Verbindung schließen (hier nur angedeutet)
function close_db_conn($conn) {
	$conn=null;
}

?>