<html>
  <head>
    <title>
	LAMP-Dashboard
	</title>
	<script src="script.js"></script>
	<link rel="stylesheet" href="style.css">
  </head>
  <body>
	<h1>
	  Willkommen zur LAMP-App! 
	</h1>
	<div class="tables">
		<div id="kunden" class="container">
			<h2>Kunden</h2>
			<?php
			  //Um die Funktion 'erstelle_tabelle_kunden' aufzurufen,
			  //muss zuerst das Skript ausgeführt werden, in dem die Funktion definiert wird.
			  
			  //PHP wird serverseitig ausgeführt!
			  include 'build_data_table.php';
			  erstelle_tabelle_kunden('kundentabelle');
			?>
		</div>
		
		<div id="produkte" class="container">
			<h2>Produkte</h2>
			<?php
			//'build_data_table.php' wurde in diesem Skript bereits zuvor ausgeführt
			//und würde bei enem weiteren Aufruf einen Fehler verursachen.
			erstelle_tabelle_produkte('produkttabelle');
			?>
		</div>
	</div>
	
	<div id="witz" class="container">
		<h2>Ein Witz</h2>
		<?php
		//Die Funktion 'get_witz' ist in 'witz.php' definiert.
		include 'witz.php';
		print get_witz();
		?>
	</div>
  </body>
</html>
