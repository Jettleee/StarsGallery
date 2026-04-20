package com.example.starsgallery.beans;

public class Star {
    private int id;
    private String nom;
    private String image;
    private float note;
    private static int increment = 0;

    public Star(String nom, String image, float note) {
        this.id = ++increment;
        this.nom = nom;
        this.image = image;
        this.note = note;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getImage() { return image; }
    public float getNote() { return note; }

    public void setNom(String nom) { this.nom = nom; }
    public void setImage(String image) { this.image = image; }
    public void setNote(float note) { this.note = note; }
}