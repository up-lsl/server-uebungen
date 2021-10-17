package com.example.SpringApp.model;

import javax.persistence.*;

/**
 * Datenmodell des Kunden
 */

@Entity
@Table(name = "kundentabelle")  // Da schon eine Datenbanktabelle mit den Kunden vorhanden ist,
                                // diese aber nicht den Namen "Kunde" trägt, kann der zu verwendende
                                // Name hier angegeben werden
public class Kunde {
    @Id // Kennzeichnung der id als Identifier
    private Integer id;
    private String name;

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
}
