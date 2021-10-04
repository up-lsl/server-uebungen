from django.shortcuts import render
from django.template import loader
from django.views.decorators.csrf import csrf_exempt
from rest_framework.parsers import JSONParser
from django.http.response import JsonResponse, HttpResponse

from dashboard.models import Kunde, Produkt
from dashboard.serializers import KundeSerializer, ProduktSerializer

from django.core.files.storage import default_storage

@csrf_exempt
def kundeApi(request,id=0):
    if request.method=='GET':
        kunden = Kunde.objects.all()
        kunde_serializer=KundeSerializer(kunden, many=True)
        return JsonResponse(kunde_serializer.data, safe=False)
    elif request.method=='POST':
        kunden_data=JSONParser().parse(request)
        kunden_serializer=KundeSerializer(data=kunden_data)
        if kunden_serializer.is_valid():
            kunden_serializer.save()
            return JsonResponse("Added Successfully", safe=False)
        return JsonResponse("Failed to Add", safe=False)
    elif request.method=='PUT':
        kunden_data=JSONParser().parse(request)
        kunde=Kunde.objects.get(id=kunden_data['id'])
        kunde_serializer=KundeSerializer(kunde, data=kunden_data)
        if kunde_serializer.is_valid():
            kunde_serializer.save()
            return JsonResponse("Updated Successfully", safe=False)
        return JsonResponse("Failed to Update")
    elif request.method=='DELETE':
        kunde=Kunde.objects.get(id=id)
        kunde.delete()
        return JsonResponse("Deleted Successfully",safe=False)

@csrf_exempt
def produktApi(request,id=0):
    if request.method == 'GET':
        produkte = Produkt.objects.all()
        produkt_serializer = ProduktSerializer(produkte, many=True)
        return JsonResponse(produkt_serializer.data, safe=False)
    elif request.method == 'POST':
        produkte_data = JSONParser().parse(request)
        produkte_serializer = ProduktSerializer(data=produkte_data)
        if produkte_serializer.is_valid():
            produkte_serializer.save()
            return JsonResponse("Added Successfully", safe=False)
        return JsonResponse("Failed to Add", safe=False)
    elif request.method == 'PUT':
        produkte_data = JSONParser().parse(request)
        produkt = Produkt.objects.get(id=produkte_data['id'])
        produkt_serializer = ProduktSerializer(produkt, data=produkte_data)
        if produkt_serializer.is_valid():
            produkt_serializer.save()
            return JsonResponse("Updated Successfully", safe=False)
        return JsonResponse("Failed to Update")
    elif request.method == 'DELETE':
        produkt = Produkt.objects.get(id=id)
        produkt.delete()
        return JsonResponse("Deleted Successfully", safe=False)



def index(request):
    tmp_kunden = Kunde.objects.all()
    kunde_serializer = KundeSerializer(tmp_kunden, many=True)
    kunden = kunde_serializer.data[1:]

    tmp_produkte = Produkt.objects.all()
    produkt_serializer = ProduktSerializer(tmp_produkte, many=True)
    produkte = produkt_serializer.data[1:]

    template = loader.get_template('index.html')
    context = {
        'kunden': kunden,
        'produkte': produkte
    }
    return HttpResponse(template.render(context, request))
