from django.db import models

class Kunde(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=25)

    class Meta:
        db_table = "kundentabelle"


class Produkt(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=25)
    bestand = models.IntegerField()

    class Meta:
        db_table = "produkttabelle"

