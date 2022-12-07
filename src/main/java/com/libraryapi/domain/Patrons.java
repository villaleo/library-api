package com.libraryapi.domain;

import javax.persistence.*;

@Entity
public class Patrons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patron_id")
    private Integer patronId;

    private String name;
    private Double fines;

    public Integer getPatronId() {
        return patronId;
    }

    public Patrons() {
        super();
    }

    public void setPatronId(Integer patron_id) {
        this.patronId = patron_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFines() {
        return fines;
    }

    public void setFines(double fines) {
        this.fines = fines;
    }
}
