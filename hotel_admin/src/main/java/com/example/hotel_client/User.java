package com.example.hotel_client;

public class User {
    public String email;
    public String password;
    public String phone_num;
    public String generation;
    public String isAppUser;
    public String adresse;
    public String prénom;
    public String sexe;


    public User(String email, String password, String phone_num, String generation, String isAppUser, String adresse, String prénom, String sexe) {
        this.email = email;
        this.password = password;
        this.phone_num = phone_num;
        this.generation = generation;
        this.isAppUser = isAppUser;
        this.adresse = adresse;
        this.prénom = prénom;
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getGeneration() {
        return generation;
    }

    public String getIsAppUser() {
        return isAppUser;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPrénom() {
        return prénom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public void setIsAppUser(String isAppUser) {
        this.isAppUser = isAppUser;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setPrénom(String prénom) {
        this.prénom = prénom;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}
