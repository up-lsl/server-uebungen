# Flaks-Dashboard

Die nachfolgende Anleitung ist wieder für Ubuntu verfasst. Auf anderen Betriebssystem funktioniert das Development und Deployment jedoch analog.

## Dateien und (Ordner-)Struktur

```bash
.
├── app.py
├── config.py
├── controller.py
├── model.py
├── requirements.txt
├── static
│   ├── css
│   │   └── style.css
│   └── scripts
│       └── script.js
├── templates
│   ├── base.html
│   └── index.html
└── wsgi.py
```

- `app.py` ist der Kern der Anwendung und enthält die Startparameter, die Initialisierung der Datenbankanbindung etc. Außerdem werden hier die einzelnen Routen (Web- und API-Pfade definiert)
  
  - **Beispiel:** Definition einer API-Route um einen Kunden abzufragen:
    
    ```python
    @app.route(f'{api_base_url}/kunden/<int:key>', methods=['GET'])
    def get_kunde_by_key(key):
        kunde = KundeController.get_by_key(key)
        return db_output_to_json(kunde)
    ```

- `config.py` enthält die Einstellungen der Webapplikation (z.B. bzgl. Datenbank oder fester Texte)

- `controller.py` beinhaltet die Controller der Anwendung, in diesem Fall den `KundeController`, `ProduktController` und `WitzController`, die sich jeweils um das Suchen der Datensätze in der Datenbank kümmern
  
  - **Beispiel:** `KundeController`
    
    ```python
    class KundeController:
        @staticmethod
        def get_all():
            kunden = Kunde.objects
            return kunden
    
        @staticmethod
        def get_by_key(key):
            kunde = Kunde.objects(key=key).first()
            return kunde
    ```

- `model.py` definiert das Datenmodell der Anwendung, beinhaltet also die Konfiguration von Kunden und Produkten auf Datenbankebene
  
  - **Beispiel:** Kunde
    
    ```python
    class Kunde(me.Document):
        key = me.IntField()
        name = me.StringField()
        meta = {'strict': False} # Damit das Schema nicht erzwungen wird
    ```

- `requirements.txt` ist eine Liste von Python-Modulen, die zur Ausführung der Webanwendung benötigt werden und nachzuinstallieren sind

- Der Ordner `static` enthält alle statischen Dateien, in diesem Fall die `style.css` und `script.js`, die bereits aus dem Spring-Dashboard bekannt sind und den identischen Inhalt haben

- Der Ordner `templates` ist Speicherort für die anzuzeigenden Web-HTML-Seiten. Ähnlich zu Thymeleaf für Spring, arbeitet Flask mit der Web-Template-Engine Jinja (https://flask.palletsprojects.com/en/0.12.x/tutorial/templates/), die ebenfalls einfach zu handhaben ist:
  
  - **Beispiel:** Verkürzter Auszug aus der übergeordneten `base.html` mit Laden der statischen CSS- und JS-Files und Definition des "Content"-Blocks.
    
    ```jinja2
    <!doctype html>
    <!-- Header -->
    <html lang="en">
      <head>
        <!-- Meta Tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
        <!-- Eigenes CSS -->
        <link rel="stylesheet" href="{{ url_for('static', filename='css/style.css') }}">
    
        <!-- Eigenes JavaScript -->
        <script src="{{ url_for('static', filename='scripts/script.js') }}"></script>
    
        <!-- Block um weitere Headerzeilen einzufügen -->
        {% block furtherhead %} {% endblock %}
    
        <!-- Block um Titel zu bearbeiten -->
        <title>{% block title %} {% endblock %}</title>
      </head>
      <body>
    
        <div>
            {% block content %} {% endblock %}
        </div>
    
      </body>
    
      <!-- Footer -->
        <footer>
    
      </footer>
    </html>
    ```
  
  - **Beispiel:** Verkürzter Auszug der `index.html`, die die `base.html` (und damit den "globalen" Header etc. übernimmt)
    
    ```jinja2
    <!-- Erben von base.html -->
    {% extends 'base.html' %}
    
    <!-- Füllen der Felder der base.html -->
    {% block title %}{{ texts.index.title }}{% endblock %}
    
    {% block content %}
        <h1>{{ texts.index.title }}</h1>
    
        <div class="tables">
            <div id="kunden" class="container">
                <h2>{{ texts.kunde.bezpl }}</h2>
    
                <!-- Erstellen der Kundentabelle, wenn die Variable "kunden" gefüllt ist -->
                {% if kunden is not none and kunden|length > 0 %}
                    <table>
                        <!-- Tabellenkopf -->
                        <thead>
                            <tr>
                                <th>{{ texts.kunde.key }}</th>
                                <th>{{ texts.kunde.name }}</th>
                            </tr>
                        </thead>
    
                        <tbody>
                            <!-- Erstellt mit einer foreach-Schleife die Zeilen der Tabelle -->
                            {% for kunde in kunden %}
                                <tr>
                                    <td>{{ kunde.key }}</td>
                                    <td>{{ kunde.name }}</td>
                                </tr>
                            {% endfor %}
                            <!--Formular, um einen neuen Kunden zu erstellen.-->
                            <form action='' method='post'>
                                <tr>
                                    <td> <input type='number' name='key'> </td>
                                    <td> <input type='text' name='name'> </td>
                                </tr>
                                <tr>
                                    <input type='hidden' name='type' value='addKunde'>
                                    <td colspan="2"> <input type='submit'> </td>
                                </tr>
                            </form>
                        </tbody>
                    </table>
                {% else %}
                    <p>Keine Kunden in Datenbank</p>
                {% endif %}
            </div>
    
            <div id="produkte" class="container">
                <h2>{{ texts.produkt.bezpl }}</h2>
                .....
            </div>
        </div>
    
        <div id="witz" class="container">
            <h2>Ein Witz</h2>
            <!-- Platzhalter für den Witz -->
            <div>{{witz}}</div>
        </div>
    {% endblock %}
    ```

- `wsgi.py` wird lediglich für das Deployment benötigt und verweist primär auf die `app.py` (siehe unten)

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

*Das verwendete Modul zur Datenhaltung (MongoEngine) verwendet intern das Feld "id" und "_id" größtenteils analog.  Damit es hier nicht zu Fehlern kommt, wird die Datenbank beim ersten Aufruf neu und mit leicht anderen Collection (Kunde statt Kunden und Pordukt statt Produkte) sowie Feld-Schlüsseln (key statt id) angelegt.*

# Starten der Anwendung

## In der IDE

Prinzipiell funktioniert dies analog zur Umetzung mit Ubuntu im nachfolgenden Unterkapitel, um vieles kümmert sich aber erneut die IDE. Bspw. erstellt die IDE "PyCharm" bei der Erstellung eines Flask-Projekts bereits das virutelle Environment etc. Außerdem ist es möglich, anstatt einer lokalen Installation von Python dies auch über Docker abzuwickeln, wie dieses Beispiel zeigt: https://blog.jetbrains.com/pycharm/2017/03/docker-compose-getting-flask-up-and-running/

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