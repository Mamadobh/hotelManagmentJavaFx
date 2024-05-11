package com.example.hotel_client;

import java.time.LocalDate;

public class Rservation {
    public String id_reservation;
    public LocalDate Date_debut;
    public LocalDate Date_fin;
    public double Total;
    public int Num_room;
    public String Email;

    public String getId_reservation() {
        return id_reservation;
    }

    public LocalDate getDate_debut() {
        return Date_debut;
    }

    public void setId_reservation(String id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setDate_debut(LocalDate date_debut) {
        Date_debut = date_debut;
    }

    public void setDate_fin(LocalDate date_fin) {
        Date_fin = date_fin;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public void setNum_room(int num_room) {
        Num_room = num_room;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public LocalDate getDate_fin() {
        return Date_fin;
    }

    public double getTotal() {
        return Total;
    }

    public int getNum_room() {
        return Num_room;
    }

    public String getEmail() {
        return Email;
    }

    public Rservation(String id_reservation, LocalDate date_debut, LocalDate date_fin, double total, int num_room, String email) {

        this.id_reservation = id_reservation;
        Date_debut = date_debut;
        Date_fin = date_fin;
        Total = total;
        Num_room = num_room;
        Email = email;
    }
}
