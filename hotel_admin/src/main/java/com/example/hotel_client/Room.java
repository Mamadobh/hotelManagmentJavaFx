package com.example.hotel_client;
public class Room {
   int numRoom;
    Double price;
    String type;

    public Room(int numRoom, double price, String type) {
        this.numRoom = numRoom;
        this.price = price ;
        this.type = type;
    }

    public int getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(int numRoom) {
        this.numRoom=numRoom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price=price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }
}
