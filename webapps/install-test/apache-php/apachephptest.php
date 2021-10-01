<!DOCTYPE html>
<html>
	<head>
		<title>Apache-PHP-Test</title>
	</head>
	<body>
		<?php
			echo "<h2>Der Test von ";
			echo $_SERVER["SERVER_SOFTWARE"]." ";
			echo "und ";
			echo "PHP ".phpversion()." ";
			echo "am ".date('d.m.Y')." ";
			echo "war erfolgreich!</h2>";
		?>
    </body>
</html>
