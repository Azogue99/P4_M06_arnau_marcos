package models;

import java.io.Serializable;

public class Producte implements Serializable {
    private int id;
    private String nom;
    private double preu;

    public Producte() {
    }

    public Producte(int id, String nom, double preu) {
        this.id = id;
        this.nom = nom;
        this.preu = preu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    @Override
    public String toString() {
        return "Producte{id=" + id + ", nom='" + nom + "', preu=" + preu + "}";
    }
}
