package com.libraryapi.domain;

import javax.persistence.*;

@Entity(name = "patrons")
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

    public String getName() {
        return name;
    }

    public Double getFines() {
        return fines;
    }
}
