<?php
//Hier werden andere APIs aufgerufen.

//Diese Funktion ruft Inhalte einer externen API ab.
function get_joke(){
	//Die Dokumentation der Joke-API finden Sie hier: https://sv443.net/jokeapi/v2/
	try{
		$newJoke = file_get_contents("https://sv443.net/jokeapi/v2/joke/Programming?lang=de&blacklistFlags=nsfw,religious,political,racist,sexist&format=txt");
		echo $newJoke;

	}catch(Exception $e){
		echo "Den kenn' ich schon ...";
	}
}

?>