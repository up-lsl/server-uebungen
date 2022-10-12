import datetime

# Einstellungen zur Applikation selbst
FLASK_APP = "Flask-App"
SECRET_KEY = 'aPrettyRandomKey'

# Caching- und Session-Einstellungen
PERMANENT_SESSION_LIFETIME = datetime.timedelta(days=365)

# Datenbank-Einstellungen
MONGODB_SETTINGS = {
    'db': 'dashboarddb',
    'authentication_source': 'dashboarddb',
    'host': 'localhost',
    'port': 27017,
    'username':'dbuser',
    'password':'dbuser123',
}

# Eigene Einstellungen
TEXTS = {
    "index": {
        "title": "Flask-Dashboard",
        "welcome": "Willkommen zur Spring-App!"
    },
    "kunde": {
        "bezsi": "Kunde",
        "bezpl": "Kunden",
        "key": "key",
        "name": "name"
    },
    "produkt": {
        "bezsi": "Produkt",
        "bezpl": "Produkte",
        "key": "key",
        "name": "name",
        "bestand": "bestand"
    }
}
