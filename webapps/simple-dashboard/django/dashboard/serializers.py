from rest_framework import serializers
from dashboard.models import Kunde, Produkt

class KundeSerializer(serializers.ModelSerializer):
    class Meta:
        model=Kunde
        fields=('id', 'name')

class ProduktSerializer(serializers.ModelSerializer):
    class Meta:
        model=Produkt
        fields=('id', 'name', 'bestand')