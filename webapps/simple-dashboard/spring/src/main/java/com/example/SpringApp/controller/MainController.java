package com.example.SpringApp.controller;

import com.example.SpringApp.model.Kunde;
import com.example.SpringApp.model.Produkt;
import com.example.SpringApp.repository.KundeRepository;
import com.example.SpringApp.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private KundeRepository kundeRepository;
    @Autowired
    private ProduktRepository produktRepository;


    @PostMapping(path="/addKunde") // Map ONLY POST Requests
    public @ResponseBody
    String addNewKunde (@RequestParam int id, @RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Kunde k = new Kunde();
        k.setId(id);
        k.setName(name);
        kundeRepository.save(k);
        return "Saved";
    }

    @GetMapping(path="/getAllKunden")
    public @ResponseBody Iterable<Kunde> getAllKunden() {
        // This returns a JSON or XML with the users
        return kundeRepository.findAll();
    }

    @GetMapping("/getAllKunden2")
    public ModelAndView getResultBySearchKey()
    {
        List<Kunde> kunden= new ArrayList<>();//results from db
        ModelAndView mv= new ModelAndView("index::kunden");
        mv.addObject("kunden", kunden);

        return mv;
    }

    @PostMapping(path="/updateBestand") // Map ONLY POST Requests
    public @ResponseBody
    String updateProduktBestand (@RequestParam int id, @RequestParam int bestand) {
        Optional<Produkt> op = produktRepository.findById(id);

        if(op.isPresent()) {
            Produkt p = op.get();
            p.setBestand(bestand);
            produktRepository.save(p);
            return "Updated";
        }
        else {
            return "Product with id " + id + " not in database!";
        }
    }

    @PostMapping(path="/updateBestandAjax") // Map ONLY POST Requests
    public @ResponseBody
    String updateProduktBestandAjax (@RequestParam int id, @RequestParam int bestand, ModelMap map) {
        Optional<Produkt> op = produktRepository.findById(id);

        if(op.isPresent()) {
            Produkt p = op.get();
            p.setBestand(bestand);
            produktRepository.save(p);

            map.addAttribute("produkt.bestand", bestand);

            return "index :: #bestand_"+id;
        }
        else {
            return "Product with id " + id + " not in database!";
        }
    }

    @GetMapping(path="/getAllProdukte")
    public @ResponseBody Iterable<Produkt> getAllProdukte() {
        // This returns a JSON or XML with the users
        return produktRepository.findAll();
    }

    @GetMapping(path="/witz")
    public @ResponseBody String getWitz() {
        String url = "https://sv443.net/jokeapi/v2/joke/Programming?lang=de&blacklistFlags=nsfw,religious,political,racist,sexist&format=txt";
        String witz = "";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            witz = response.body().toString();
        } catch (Exception e) {
            witz = "Den kenn' ich schon ...";
            e.printStackTrace();
        }

        return witz;
    }


    @GetMapping("/")
    public String main(Model model) {
        List<Kunde> kunden = (List<Kunde>) kundeRepository.findAll();
        List<Produkt> produkte = (List<Produkt>) produktRepository.findAll();
        String witz = getWitz();

        model.addAttribute("kunden", kunden);
        model.addAttribute("produkte", produkte);
        model.addAttribute("witz", witz);

        return "index"; //view
    }

    @PostMapping("/")
    public String main2(@RequestParam String type, @RequestParam int id, @RequestParam String name, Model model) {
        switch (type) {
            case "addKunde":
                addNewKunde(id, name);
                break;
        }

        return main(model);
    }
}
