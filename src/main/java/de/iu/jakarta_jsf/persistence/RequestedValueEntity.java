package de.iu.jakarta_jsf.persistence;

import jakarta.persistence.*;

/**
 * This data-entities are the rows a researcher can add and modify when
 * requesting changes in the database (the lower table on management.xhtml).
 * When the RequestEntity the RequestedValueEntities are linked to is approved,
 * the RequestedValueEntities will be transferred into MainCo2DataEntities.
 */
@Entity
@Table(name = "SUB_REQUEST")
public class RequestedValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String country;
    private int year;
    private float emission;

    /**
     * getter and setter
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getEmission() {
        return emission;
    }

    public void setEmission(float emission) {
        this.emission = emission;
    }
}
