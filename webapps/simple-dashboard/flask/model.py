import mongoengine as me

# Kunde
class Kunde(me.Document):
    key = me.IntField()
    name = me.StringField()
    meta = {'strict': False} # Damit das Schema nicht erzwungen wird

# Produkt
class Produkt(me.Document):
    key = me.IntField()
    name = me.StringField()
    bestand = me.IntField()
    meta = {'strict': False} # Damit das Schema nicht erzwungen wird
