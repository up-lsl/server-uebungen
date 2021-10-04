<?php

function get_db_conn() {
	$host = 'localhost-test';
	$port = 3306;
	$dbname = 'dashboarddb';
	$user = 'dbuser';
	$pwd = 'dbuser123';

	$conn = new PDO("mysql:host=$host;port=$port;dbname=$dbname", "$user", "$pwd");

	return $conn;
}

function close_db_conn($conn) {
	$conn=null;
}

