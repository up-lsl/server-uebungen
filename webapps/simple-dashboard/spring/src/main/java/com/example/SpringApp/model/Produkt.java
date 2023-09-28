package com.example.SpringApp.model;

import jakarta.persistence.*;

/**
 * Datenmodell des Produkts
 */

@Entity
@Table(name = "produkttabelle")
public class Produkt {
    @Id // Kennzeichnung der id als Identifier
    private Integer id;
    private String name;
    private Integer bestand;

    // Einfache Getter- und Setter-Methoden
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getBestand() {
        return bestand;
    }
    public void setBestand(int bestand) {
        this.bestand = bestand;
    }
}
