package com.example.SpringApp.controller;

import com.example.SpringApp.model.Kunde;
import com.example.SpringApp.model.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller für das Handling der Ablauflogik innerhalb des angezeigten Dashboards
 */

@Controller
public class MainController {
    @Autowired
    private ApiController apiController; // Verweis, um auf den anderen Controller zuzugreifen


    /**
     * GET-Route und Methode für die Index-Seite (= Startseite des Dashboards)
     * @param model Das Model (hierin werden die Variablen eingefügt, die Thymeleaf anschließend auswertet)
     * @return Ausgewertetes Index-Template (index.html)
     */
    @GetMapping("/")
    public String main(Model model) {
        Iterable<Kunde> kunden = apiController.getKunden(); // Hole Kunden
        Iterable<Produkt> produkte = apiController.getProdukte(); // Hole Produkte
        String witz = apiController.getWitz(); // Hole Witz

        // Füge die Variablen in das Thymeleaf-Template bzw. das Model ein
        // Syntax: model.addAttribute("Name_im_Template", Variablenname_im_Programm);
        model.addAttribute("kunden", kunden);
        model.addAttribute("produkte", produkte);
        model.addAttribute("witz", witz);

        return "index";  // Gib die mit den Daten angereicherte index.html zurück
    }

    /**
     * POST-Route und Methode, um POST-Anfragen an die Hauptseite (die in diesem Fall nur vom Formular kommen, das neue
     * Kunden erstellt kommen) zu verarbeiten.
     * @param type Angeforderte Aktion
     * @param id id des betreffenden Objekts
     * @param name name des betreffenden Objekts
     * @param model das Model
     * @return
     */
    @PostMapping("/")
    public String main_post(@RequestParam String type, @RequestParam int id, @RequestParam String name, Model model) {
        switch (type) {
            case "addKunde": // Neuer Kunde soll erstellt werden
                apiController.addNewKunde(id, name); // Aufruf der entsprechenden Methode im API-Controller
                break;
        }

        return main(model); // Rückgabe des(selben) Models, um auf der Startseite zu bleiben bzw. diese neu zu laden
    }
}
