from model import Kunde, Produkt
import requests


class KundeController:
    @staticmethod
    def get_all():
        kunden = Kunde.objects
        return kunden

    @staticmethod
    def get_by_key(key):
        kunde = Kunde.objects(key=key).first()
        return kunde


class ProduktController:
    @staticmethod
    def get_all():
        produkte = Produkt.objects
        return produkte

    @staticmethod
    def get_by_key(key):
        produkt = Produkt.objects(key=key).first()
        return produkt


class WitzController:
    @staticmethod
    def get_witz():
        witz_response = requests.get("https://sv443.net/jokeapi/v2/joke/Programming?lang=de&blacklistFlags=nsfw,religious,political,racist,sexist&format=txt")
        if witz_response.ok:
            witz = witz_response.content.decode()
        else:
            witz = "Den kannte ich schon..."

        return witz