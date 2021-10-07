// Funktion für den '-'-Button.
function reduziereBestand(produkt_id){
	let bestand = getBestandFromPage(produkt_id);
	aktualisiereBestand(produkt_id, bestand-1);
}

// Funktion für den '+'-Button.
function erhoeheBestand(produkt_id){
	let bestand = getBestandFromPage(produkt_id);
	aktualisiereBestand(produkt_id, bestand+1);
}

// Funtkion, die den Bestand direkt von der Webseite ausliest
// (benötigt um auch nach AJAX-Veränderung immer den aktuellen Bestand zu erhalten, ohne die API bemühen zu müssen)
function getBestandFromPage(produkt_id){
	let bestand_html = document.querySelector('div[id=bestand_'+produkt_id);
	let bestand_int = parseInt(bestand_html.innerHTML);

	return bestand_int;
}


// Funtkion, die die API zur Aktualisierung des Bestands aufruft und auch die Anzeige auf der Webseite verändert
function aktualisiereBestand(produkt_id, bestand) {
	//Erzeuge AJAX-Anfrage:
	let ajaxurl = 'updateBestand',
		data = "id="+produkt_id+"&bestand="+bestand;

	var request = new XMLHttpRequest();
	request.onreadystatechange = function () {
		if (request.readyState == 4 && request.status == 200) {
			console.log(request.responseText);
			//Verringere die Anzahl auf dem Bildschirm um 1,
			//nachdem die Server-Anfrage ausgeführt wurde.
			let bestand_html_element = document.querySelector('div[id="bestand_'+produkt_id+'"]');
			bestand_html_element.innerHTML = bestand;
		}
	};

	//Führe Anfrage aus.
	request.open('POST', ajaxurl, true);
	request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
	request.send(data);
}

