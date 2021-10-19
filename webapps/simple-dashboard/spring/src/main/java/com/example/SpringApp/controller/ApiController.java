package com.example.SpringApp.controller;

import com.example.SpringApp.model.Kunde;
import com.example.SpringApp.model.Produkt;
import com.example.SpringApp.repository.KundeRepository;
import com.example.SpringApp.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * Controller für die API des Projektes
 * ====================================
 * Hierbei handelt es sich um eine sog. REST-API (auch bekannt als RESTful API oder Web-API)
 */

@RestController
public class ApiController {
    /**
     * Klassenvariablen für die Repositories
     * Diese werden dank der @Autowired-Annotation automatisch initialisiert
     */
    @Autowired
    private KundeRepository kundeRepository;
    @Autowired
    private ProduktRepository produktRepository;

    /**
     * POST-Route und Methode, um einen neuen Kunden anzulegen
     * @param id id des neuen Kunden
     * @param name name des neuen Kunden
     * @return gibt bei Erfolg "success" zurück
     */
    @PostMapping(path="api/addKunde")
    public @ResponseBody // @ResponseBody bedeutet, dass der Rückgabewert ("Saved") direkt zurückgegeben wird
                         // und sich nicht auf eine View, also einen Dateinamen, bezieht.
    String addNewKunde (@RequestParam int id, @RequestParam String name) {
        // @RequestParam bedeutet, dass es sich um einen Parameter aus der GET- oder POST-Anfrage handelt

        Kunde k = new Kunde();
        k.setId(id);
        k.setName(name);
        kundeRepository.save(k);
        return "Saved";  //gibt bei Erfolg "success" zurück
    }

    /**
     * GET-Route und Methode, um alle Kunden zu erhalten
     * @return gibt alle Kunden im JSON- oder XML-Format zurück
     */
    @GetMapping(path= "api/getKunden")
    public @ResponseBody Iterable<Kunde> getKunden() {
        return kundeRepository.findAll();
    }

    /**
     * GET-Route und Methode, um den Kunden mit gegebener id zu erhalten
     * @param id id des gewünschten Kunden
     * @return der gefundene Kunde, oder null, falls nicht gefunden
     */
    @GetMapping(path= "api/getKunden/{id}")
    public Kunde getKunde(@PathVariable int id) {
        // @PathVariable bedeutet, dass es sich um einen Parameter handelt, der sich innerhalb des "URL-Pfads" befindet

        Optional<Kunde> op = kundeRepository.findById(id); //Suche den Kunden mithilfe seiner id

        if(op.isPresent()) { //Kunde gefunden?
            Kunde k = op.get(); //Gib Kunde
            return k;
        }
        else {
            return null;
        }
    }

    /**
     * POST-Route und Methode, um den Bestand eines gegebenen Produkts (id) zu aktualisieren
     * @param id id des Produkts
     * @param bestand neuer Bestand des Produkts
     * @return Meldung für erfolgreiches Update oder Fehlermeldung
     */
    @PostMapping(path="api/updateBestand/{id}")
    public @ResponseBody
    String updateProduktBestand (@PathVariable int id, @RequestParam int bestand) {
        Optional<Produkt> op = produktRepository.findById(id); // Suche das Produkt mithilfe seiner id

        if(op.isPresent()) { // Produkt gefunden?
            Produkt p = op.get(); //Gib Produkt
            p.setBestand(bestand); // Bestand des Produkt-Objekts aktualisieren
            produktRepository.save(p); // Geändertes Produkt-Objekt (in Datenbank) abspeichern
            return "Updated";
        }
        else {
            return "Product with id " + id + " not in database!";
        }
    }

    /**
     * GET-Route und Methode, um alle Produkte zu erhalten
     * @return gibt alle Kunden im JSON- oder XML-Format zurück
     */
    @GetMapping(path="api/getProdukte")
    public @ResponseBody Iterable<Produkt> getProdukte() {
        // Suche alle Produkte im Repository "produktRepository"
        return produktRepository.findAll();
    }

    /**
     * GET-Route und Methode, die einen Witz zurückgibt, der zuvor bei einer externen API abgefragt wurde
     * @return der Witz als String
     */
    @GetMapping(path="api/witz")
    public @ResponseBody String getWitz() {
        String url = "https://sv443.net/jokeapi/v2/joke/Programming?lang=de&blacklistFlags=nsfw,religious,political,racist,sexist&format=txt";
        String witz = "";

        //Overhead, um eine externe http-Anfrage zu stellen
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse response = null;
        try {
            //Stellen des http-Requests bei der externen API
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //Konvertieren der Antwort (response) in ein String-Element
            witz = response.body().toString();
        } catch (Exception e) {
            witz = "Den kenn' ich schon ...";
            e.printStackTrace();
        }

        return witz;
    }
}
