package sk.tmconsulting.evidenciavydavkovswingmysql.model;

import java.sql.Date;

public class Vydavok {
    private int id;
    private String nazov;
    private double cena;
    private Date datum;
    private Kategoria kategoria;
    private String poznamka;

    public Vydavok(int id, String nazov, double cena, Date datum, Kategoria kategoria, String poznamka) {
        this.id = id;
        this.nazov = nazov;
        this.cena = cena;
        this.datum = datum;
        this.kategoria = kategoria;
        this.poznamka = poznamka;
    }

    public Vydavok(String nazov, double cena, Date datum, Kategoria kategoria, String poznamka) {
        this.nazov = nazov;
        this.cena = cena;
        this.datum = datum;
        this.kategoria = kategoria;
        this.poznamka = poznamka;
    }

    public String getPoznamka() {
        return poznamka;
    }

    public void setPoznamka(String poznamka) {
        this.poznamka = poznamka;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public String toString() {
        return nazov + " " + cena + " " + kategoria + " " + datum + " ";
    }
}
