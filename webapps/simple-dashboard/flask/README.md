# Flaks-Dashboard

Die nachfolgende Anleitung ist wieder für Ubuntu verfasst. Auf anderen Betriebssystem funktioniert das Development und Deployment jedoch analog.

## Voraussetzungen

- Pyhton (3.x) muss installiert sein (sollte bei Ubuntu bereits vorinstalliert sein)
  
  ```bash
  sudo apt install python3
  python3 --version
  ```

- Zur Installation von Python-Modulen erleichter "pip" die Arbeit ungemein. Pip kann man installieren mit:
  
  ```bash
  sudo apt-get install python3-pip
  ```
  
  (ggf. den Neustart der Dienste bestätigen)

- Um das Virtual Environment nutzen zu können, wird außerdem "virtualenv" benötigt:
  
  ```bash
  sudo pip3 install virtualenv
  ```

- Nach dem Serverpraktikum konfigurierte Mongo-DB

### Datenbank-Anpassungen

_Das verwendete Modul zur Datenhaltung (MongoEngine) verwendet intern das Feld "id" und "_id" größtenteils analog.  Damit es hier nicht zu Fehlern kommt, wird die Datenbank beim ersten Aufruf neu und mit leicht anderen Collection (Kunde statt Kunden und Pordukt statt Produkte) sowie Feld-Schlüsseln (key statt id) angelegt._

# Starten der Anwendung

## Im Development-Modus

- In Dashboard-Ordner wechseln:
  
  ```bash
  cd ~/server-uebungen/webapps/simple-dashboard/flask
  ```

- Virtuelles Environment erstellen und aktivieren:
  
  ```bash
  virtualenv venv
  source venv/bin/activate
  ```
  
  (ganz vorne in der Konsole sollte nun `(venv) ` angezeigt werden)

- Requirements der Anwendung installieren:
  
  ```bash
  pip install -r requirements.txt
  ```

- Starten der Anwendung:
  
  ```bash
  python3 app.py
  ```

- Ausgabe:
  
  ```bash
  * Serving Flask app 'app'
  * Debug mode: off
  WARNING: This is a development server. Do not use it in a production deployment. Use a production WSGI server instead.
  * Running on all addresses (0.0.0.0)
  * Running on http://127.0.0.1:5000
  * Running on http://server_ip:5000
  ```
  
  Die Applikation ist nun unter http://<server_ip>:5000/ zugänglich

- Anwendung mit Strg+C wieder beenden

- Virutelles Environment schließen:
  
  ```bash
  deactivate
  ```

## Im Deployment-Modus

<b style="color:red">Noch nicht getestet! -> Bitte gedulden Sie sich noch</b>

- Erstellt nach https://www.digitalocean.com/community/tutorials/how-to-serve-flask-applications-with-gunicorn-and-nginx-on-ubuntu-18-04

- Zum Deployment wird im folgenden "Gunicorn", ein sog. WSGI-HTTP-Server (_Web Server Gateway Interface HTTP-Server_) verwendet.
  Dieser wird meist in der Kombination mit dem Webserver "nginx" eingesetzt, um Load-Balancing zu ermöglichen oder DOS-Attacken zu verhindern, kann jedoch auch autark betrieben werden. _(Für das Serverpraktikum verzichten wir der Einfachheit halber auf diese Kombination, vor allem da bereits Apache fertig konfiguriert wurde. Eine ebenso gängige Methode ist die Nutzung des WSGI-Moduls für Apache. Dessen Konfiguration ist initial jedoch eher aufwändig.)_

- In den Ordner des Flask Dashboards wechseln:
  ```bash
  cd ~/server-uebungen/webapps/simple-dashboard/flask/
  ```

- Virtuelles Environment erstellen (falls oben noch nicht geschehen):
  
  ```bash
  virtualenv venv
  ```
  
- Virtuelles Environment aktivieren:
  
  ```bash
  source venv/bin/activate
  ```
  
  (ganz vorne in der Konsole sollte nun `(venv) ` angezeigt werden)

- Requirements der Anwendung installieren (falls oben noch nicht geschehen)
  
  ```bash
  pip install -r requirements.txt
  ```

- Gunicorn installieren:
  ```bash
  pip install gunicorn
  ```
  
- Deployment mit Gunicorn testen:
  ```bash
  gunicorn --bind 0.0.0.0:8000 wsgi:application
  ```
  
  Ausgabe:
  ```bash
  [2022-10-19 11:39:30 +0000] [20642] [INFO] Starting gunicorn 20.1.0
  [2022-10-19 11:39:30 +0000] [20642] [INFO] Listening at: http://0.0.0.0:8000 (20642)
  [2022-10-19 11:39:30 +0000] [20642] [INFO] Using worker: sync
  [2022-10-19 11:39:30 +0000] [20643] [INFO] Booting worker with pid: 20643
  ```
  Die Anwendung sollte jetzt unter http://<server_ip>:8000/ erreichbar sein.
  (Mit `Strg+C` den Server wieder beenden)
  
- Dienst für das Dashboard anlegen:
  ```bash
  sudo nano /etc/systemd/system/flaskdashboard.service
  ```
  
  Folgenden Inhalt einfügen:
  ```bash
  [Unit]
  Description=Gunicorn instance to serve flaskdashboard
  After=network.target
  
  [Service]
  User=user
  Group=www-data
  WorkingDirectory=/home/user/server-uebungen/webapps/simple-dashboard/flask
  Environment="PATH=/home/user/server-uebungen/webapps/simple-dashboard/flask/venv/bin"
  ExecStart=/home/user/server-uebungen/webapps/simple-dashboard/flask/venv/bin/gunicorn --workers 3 --bind 0.0.0.0:8000 -m 007 wsgi:application
  
  [Install]
  WantedBy=multi-user.target
  ```
  
- Dienst starten:
  ```bash
  sudo systemctl start flaskdashboard
  ```

- Autostart für den Dienst aktivieren:
  ```bash
  sudo systemctl enable flaskdashboard
  ```
  
- Status überprüfen:
  ```bash
  sudo systemctl status flaskdashboard
  ```
  
  Ausgabe:
  ```bash
  ● flaskdashboard.service - Gunicorn instance to serve flaskdashboard
     Loaded: loaded (/etc/systemd/system/flaskdashboard.service; enabled; vendor preset: enabled)
     Active: active (running) since Wed 2022-10-19 11:48:23 UTC; 10s ago
   Main PID: 20821 (gunicorn)
      Tasks: 13 (limit: 4575)
     Memory: 99.9M
        CPU: 732ms
     CGroup: /system.slice/flaskdashboard.service
             ├─20821 /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/python /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/gunicorn --workers 3 --bind 0.0.0.0:8000 -m 0>
             ├─20822 /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/python /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/gunicorn --workers 3 --bind 0.0.0.0:8000 -m 0>
             ├─20823 /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/python /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/gunicorn --workers 3 --bind 0.0.0.0:8000 -m 0>
             └─20824 /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/python /home/ls1/server-uebungen/webapps/simple-dashboard/flask/venv/bin/gunicorn --workers 3 --bind 0.0.0.0:8000 -m 0>

  Oct 19 11:48:23 lehner-test01 systemd[1]: Started Gunicorn instance to serve flaskdashboard.
  Oct 19 11:48:23 lehner-test01 gunicorn[20821]: [2022-10-19 11:48:23 +0000] [20821] [INFO] Starting gunicorn 20.1.0
  Oct 19 11:48:23 lehner-test01 gunicorn[20821]: [2022-10-19 11:48:23 +0000] [20821] [INFO] Listening at: http://0.0.0.0:8000 (20821)
  Oct 19 11:48:23 lehner-test01 gunicorn[20821]: [2022-10-19 11:48:23 +0000] [20821] [INFO] Using worker: sync
  Oct 19 11:48:23 lehner-test01 gunicorn[20822]: [2022-10-19 11:48:23 +0000] [20822] [INFO] Booting worker with pid: 20822
  Oct 19 11:48:23 lehner-test01 gunicorn[20823]: [2022-10-19 11:48:23 +0000] [20823] [INFO] Booting worker with pid: 20823
  Oct 19 11:48:23 lehner-test01 gunicorn[20824]: [2022-10-19 11:48:23 +0000] [20824] [INFO] Booting worker with pid: 20824
  ```

  (Ggf. mit `Strg+C` verlassen)

## Im Deployment-Modus mit Apache
<b style="color:red">Nicht durchführen! Zeigt nur das ungefähre Vorgehen auf und ist bisher nicht getestet!</b>

- Erstellt nach https://www.bogotobogo.com/python/Flask/Python_Flask_HelloWorld_App_with_Apache_WSGI_Ubuntu14.php und https://www.codementor.io/@abhishake/minimal-apache-configuration-for-deploying-a-flask-app-ubuntu-18-04-phu50a7ft
- Für das Deplyoment einer Python-Webapplikation wird diese in der Regel an das Web Server Gateway Interface (kurz WSGI) von Apache angeschlossen
- Das Modul ist in der Regel bereits installiert und aktiviert. Zur Sicherheit kann dies mit den folgenden Befehlen nochmals überprüft werden:
  ```bash
  sudo apt-get install libapache2-mod-wsgi-py3 libapache2-mod-wsgi-py3
  sudo a2enmod wsgi 
  ```

- Applikations-Dateien kopieren:
  
  ```bash
  sudo mkdir /var/www/FLASKAPPS
  sudo chmod -R 750 /var/www/FLASKAPPS
  sudo chown -R user:www-data /var/www/FLASKAPPS
  mkdir /var/www/FLASKAPPS/flaskdashboard
  cp -r ~/server-uebungen/webapps/simple-dashboard/flask/* /var/www/FLASKAPPS/flaskdashboard/
  cd /var/www/FLASKAPPS/flaskdashboard/
  ```

- Virtuelles Environment erstellen und Module installieren (**Nur falls oben noch nicht erledigt!**):
  
  ```bash
  sudo virtualenv venv
  source venv/bin/activate
  pip install -r requirements.txt
  deactivate
  ```

- Apache-Konfigurationsdatei anlegen:
  
  ```bash
  sudo nano /etc/apache2/sites-available/flaskdashboard.conf
  ```
  
  und folgenden Inhalt einfügen:
  
  ```conf
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

- Ordner für Logs anlegen:
  
  ```bash
  mkdir -p /var/www/FLASKAPPS/flaskdashboard/logs
  -----chown -R www-data:www-data /var/www/FLASKAPPS/flaskdashboard
  ```

- Konfiguration für WSGI anlegen:
  ```bash
  nano wsgi.config
  ```
  
  Folgenden Inhalt einfügen:
  ```bash
  #!/usr/bin/python
  import sys
  sys.path.insert(0,"/var/www/FLASKAPPS/")
  from flaskdashboard import app as application
  ```

- Konfigration für Apache aktivieren:
  
  ```bash
  cd /etc/apache2/sites-available
  sudo a2ensite flaskdashboard.conf
  cd /var/www/FLASKAPPS/flaskdashboard
  ```

- Konfigurationen durch Apache neu laden:
  
  ```bash
  sudo /etc/init.d/apache2 reload
  sudo service apache2 restart
  ```

- Das Dashboard kann nun über die URL http://<server_ip>/ aufgerufen werden