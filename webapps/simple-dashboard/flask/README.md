# Flaks-Dashboard

<b style="color:red">Bitte lesen!</b>

## Voraussetzungen
- Pyhton (3.x) muss installiert sein (in Ubuntu bereits vorinstalliert)
- Zum Deployment (siehe unten) wird das Modul _Web Server Gateway Interface (kurz WSGI)_ in Apache benötigt
- Nach dem Serverpraktikum konfigurierte Mongo-DB

### Datenbank-Anpassungen
_Das verwendete Modul zur Datenhaltung (MongoEngine) verwendet intern das Feld "id" und "_id" größtenteils analog.  Damit es hier nicht zu Fehlern kommt, wird die Datenbank beim ersten Aufruf neu und mit leicht anderen Collection (Kunde statt Kunden und Pordukt statt Produkte) sowie Feld-Schlüsseln (key statt id) angelegt._

# Starten der Anwendung
## Im Development-Modus
### Ubuntu
```bash
cd ~/server-uebungen/webapps/simple-dashboard/flask
python3 app.py
```

### Windows
```cmd
cd pfad_zum_flask_dashboard
python3 app.py
```

### Ausgabe:
```
* Serving Flask app 'app'
* Debug mode: off
WARNING: This is a development server. Do not use it in a production deployment. Use a production WSGI server instead.
* Running on http://127.0.0.1:5000
```

Die Applikation ist nun unter http://<server_ip>:5000/ zugänglich


## Im Deployment-Modus
<b style="color:red">Noch nicht getestet! -> Bitte gedulden Sie sich noch</b>
- Aus https://www.bogotobogo.com/python/Flask/Python_Flask_HelloWorld_App_with_Apache_WSGI_Ubuntu14.php
- Für das Deplyoment einer Python-Webapplikation wird diese in der Regel an das Web Server Gateway Interface (kurz WSGI) von Apache angeschlossen.
- Das Modul ist in der Regel bereits installiert und aktiviert. Zur Sicherheit kann dies mit den folgenden Befehlen nochmals überprüft werden:
  ```bash
  sudo apt-get install libapache2-mod-wsgi-py3 libapache2-mod-wsgi python-dev
  sudo a2enmod wsgi 
  ```
- Applikations-Dateien kopieren
  ```bash
  mkdir /var/www/FLASKAPPS
  mkdir /var/www/FLASKAPPS/flaskdashboard
  cp ~/server-uebungen/webapps/simple-dashboard/flask/ /var/www/FLASKAPPS/flaskdashboard/
  cd /var/www/FLASKAPPS/simple-dashboard/
  ```
- Virtuelles Environment erstellen
  ```bash
  sudo virtualenv venv
  source venv/bin/activate
  ```
- Requirements der Anwendung installieren
  ```bash
  pip install -r requirements.txt
  ```
- Apache-Konfigurationsdatei anlegen
  ```bash
  sudo nano /etc/apache2/sites-available/flaskdashboard.com.conf
  ```
  und folgenden Inhalt einfügen:
  ```
  <VirtualHost *:80>
    ErrorLog /var/www/flaskdashboard/logs/error.log
    CustomLog /var/www/flaskdashboard/logs/access.log combined

    WSGIDaemonProcess flaskdashboard user=www-data group=www-data threads=5
    WSGIProcessGroup flaskdashboard
    WSGIScriptAlias / /var/www/FLASKAPPS/flaskdashboard/conf.wsgi
    Alias /static/ /var/www/FLASKAPPS/flaskdashboard/static
    <Directory /var/www/FLASKAPPS/flaskdashboard/static>
        Order allow,deny
        Allow from all
    </Directory>
  </VirtualHost>
  ```
- Ordner für Logs anlegen
  ```bash
  sudo mkdir -p /var/www/flaskdashboard/logs
  sudo chown -R www-data:www-data /var/www/flaskdashboard
  ```
- Konfiguration für WSGI ist bereits im Repo enthalten (Datei `config.wsgi`)
- Konfigurationen durch Apache neu laden
  ```bash
  sudo /etc/init.d/apache2 reload
  ```
- Das Dashboard kann nun über die URL http://<server_ip>/ aufgerufen werden