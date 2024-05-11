package com.example.hotel_client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnection {
    public static Connection conncet() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException var2) {
            throw new RuntimeException(var2);
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1/hotelproject", "root", "");
        } catch (SQLException var1) {
            return null;
        }
    }
}