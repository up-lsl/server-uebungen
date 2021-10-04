package com.example.SpringApp.model;

import javax.persistence.*;

@Entity
@Table(name = "produkttabelle")
public class Produkt {
    @Id
    private Integer id;

    private String name;

    private Integer bestand;

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
