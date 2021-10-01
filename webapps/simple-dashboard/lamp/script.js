//Definiere die onload-Funktion, die nach dem Laden des erhaltenen HTML ausgeführt werden soll.
function onload(){
	//Greife auf das DOM zu, um Buttons in Variable abzulegen.
	let increaseButtons = document.querySelectorAll(".increase");
	let decreaseButtons = document.querySelectorAll(".decrease");
	
	//Durchlauf alle vorhandenen 'increase'-Buttons.
	for (let button of increaseButtons){
		//Füge dem aktuellen Button einen 'click-Listener' hinzu, 
		//eine Funktion die ausgeführt wird, wenn der Nutzer auf den Button clickt.
		button.addEventListener("click", function(){
			//Speichere die Produkt ID und die aktuelle Anzahl.
			let pid = button.getAttribute('pid');
			let stock = document.querySelector('div[pid="'+pid+'"]');
			
			//Wandle die Anzahl in einen integer um und erhöhe diesen um 1.
			let value = parseInt(stock.innerHTML)+1;
			
			//Erzeuge AJAX-Anfrage, um die Daten an data.php zu senden.
			
			//Speichere die URL.
			let ajaxurl = 'data.php',
			//Stelle die Daten zusammen, die übertragen werden sollen.
			data =  "pid= "+pid+"& stock="+value; 
			
			//Erstelle die HTML-Anfrage
			var request = new XMLHttpRequest();
			//Erstelle die Funktion, den HTTP-Response empfängt.
			request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200) {
                    console.log(request.responseText);
					//Erhöhe die Anzahl auf dem Bildschirm um 1,
					//nachdem die Server-Anfrage ausgeführt wurde.
					stock.innerHTML = parseInt(stock.innerHTML)+1;
                }
            };
			
			//Führe Anfrage aus.
			request.open('POST', ajaxurl, true);
			request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
			request.send(data);
		
		});
	}
	
	for (let button of decreaseButtons){
		button.addEventListener("click", function(){
			//Speichere die Produkt ID und die Anzahl.
			let pid = button.getAttribute('pid');
			let stock = document.querySelector('div[pid="'+pid+'"]');
			
			//Wandle die Anzahl in einen integer um, und verringere um 1.
			let value = parseInt(stock.innerHTML)-1;
			
			//Erzeuge AJAX-Anfrage:
			let ajaxurl = 'data.php',
			data =  "pid= "+pid+"& stock="+value; 
						
			var request = new XMLHttpRequest();
			request.onreadystatechange = function () {
				if (request.readyState == 4 && request.status == 200) {
					console.log(request.responseText);
					//Verringere die Anzahl auf dem Bildschirm um 1,
					//nachdem die Server-Anfrage ausgeführt wurde.
					stock.innerHTML = parseInt(stock.innerHTML)-1;
				}
			};
			
			//Führe Anfrage aus.
			request.open('POST', ajaxurl, true);
			request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
			request.send(data);
		});
	}
}
