# Programm-Module laden
import json # JSON-Modul laden
from flask import Flask, render_template, request # Flask-Modul (u.Ä.) laden
from flask_mongoengine import MongoEngine # Datenbank-Modul laden

# Eigene Module und Klassen laden
import config
from controller import *
from model import *


# Applikationskontext festlegen
# =============================
app = Flask(__name__)
application = app # benötigt, um die Anwendung auch über WSGI staren zu können
app.config.from_object('config') # Konfiguration (in config.py) laden


# Datenbank
# =========
db = MongoEngine()
db.init_app(app)

# Das verwendete Modul zur Datenhaltung (mongoengine) verwendet intern das Feld "id" und "_id" größtenteils analog.
# Damit es hier nicht zu Fehlern kommt, wird die Datenbank beim ersten Aufruf neu und mit leicht anderen
# Collection (Kunde statt Kunden und Pordukt statt Produkte) sowie Feld-Schlüsseln (key statt id) angelegt
# ==> Datenbasis befüllen, wenn noch keine Einträge
if len(list(Kunde.objects)) == 0 and len(list(Produkt.objects)) == 0:
    print("Datenbank leer -> Füge Standardeinträge ein")
    Kunde(key=1, name="maier").save()
    Kunde(key=2, name="wimmer").save()
    Produkt(key=1, name="VGA Kabel", bestand=3).save()
    Produkt(key=2, name="Monitor", bestand=12).save()


# Hilfsmethoden
# =============

def db_output_to_json(output):
    # '''
    # Umwandeln der Datenbank-Objekte ins JSON-Format für die API
    # :param output: Das umzuwandelnde Datenbank-Objekt
    # :return: JSON formatiertes Datenbank-Objekt
    # '''

    return json.loads(output.to_json())


# Web-Routen
# ==========

@app.route('/', methods=['GET'])
def index():
    '''
    Gibt die Indexseite des Dashboards mit der Kunden- und Produktübersicht sowie dem Witz zurück
    :return: Gerendertes Template als HTML-Response
    '''

    # Kunden, Produkte und Witz abfragen (hier über Controller)
    kunden = KundeController.get_all()
    produkte = ProduktController.get_all()
    witz = WitzController.get_witz()

    # Template rendern und zurückgeben
    return render_template('index.html', texts=config.TEXTS, kunden=kunden, produkte=produkte, witz=witz)


@app.route(f'/', methods=['POST'])
def index_post():
    add_kunde() # Benutze Funktion der API
    return index() # Führe wieder die Index-Funktion aus um die Seite mit neuem Inhalt anzuzeigen




# API-Routen
api_base_url = '/api'


@app.route(f'{api_base_url}/kunden', methods=['GET'])
def get_kunden():
    kunden = KundeController.get_all()
    return db_output_to_json(kunden)


@app.route(f'{api_base_url}/kunden', methods=['POST'])
def add_kunde():
    form = request.form.to_dict()
    key = form.get("key")
    name = form.get("name")

    kunde = Kunde(key=key, name=name)
    kunde.save()
    return "Success"


@app.route(f'{api_base_url}/kunden/<int:key>', methods=['GET'])
def get_kunde_by_key(key):
    kunde = KundeController.get_by_key(key)
    return db_output_to_json(kunde)


@app.route(f'{api_base_url}/produkte', methods=['GET'])
def get_produkte():
    produkte = ProduktController.get_all()
    return db_output_to_json(produkte)


@app.route(f'{api_base_url}/produkte/<int:key>', methods=['GET'])
def get_produkt_by_key(key):
    produkt = ProduktController.get_by_key(key)
    return db_output_to_json(produkt)


@app.route(f'{api_base_url}/produkte/<int:key>', methods=['POST'])
def update_produkt_bestand(key):
    form = request.form.to_dict()
    bestand_neu = form.get("bestand")

    produkt = ProduktController.get_by_key(key)
    produkt.bestand = bestand_neu
    produkt.save()

    return "Success"



if __name__ == '__main__':
    from os import environ
    app.run(host="0.0.0.0")
