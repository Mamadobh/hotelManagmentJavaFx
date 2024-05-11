package com.example.client_hotel_project;

import java.time.LocalDate;

public class Demande {
public int num_demande;

    public void setNum_demande(int num_demande) {
        this.num_demande = num_demande;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public void setNbj(int nbj) {
        this.nbj = nbj;
    }

    public void setType_room(String type_room) {
        this.type_room = type_room;
    }

    public Demande(int num_demande,String type_room,  LocalDate date_debut, int nbj ) {
        this.num_demande = num_demande;
        this.date_debut = date_debut;
        this.nbj = nbj;
        this.type_room = type_room;
    }


    public int getNum_demande() {
        return num_demande;
    }


    public LocalDate getDate_debut() {
        return date_debut;
    }

    public int getNbj() {
        return nbj;
    }

    public String getType_room() {
        return type_room;
    }

    public LocalDate date_debut;
public int nbj;
public String type_room;



}
