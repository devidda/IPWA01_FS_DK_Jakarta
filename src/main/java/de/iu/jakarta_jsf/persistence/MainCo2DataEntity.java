package de.iu.jakarta_jsf.persistence;

import jakarta.persistence.*;

/**
 * This data is accessed and displayed on the main-page (index.xhtml)
 * and has been previously approved by a peer-reviewer.
 */
@Entity
@Table(name="main")
public class MainCo2DataEntity {

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
