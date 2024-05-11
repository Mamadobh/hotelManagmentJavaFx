package com.example.hotel_client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurHotel extends Thread{
    public  void run(){
        try {
            ServerSocket server_socket=new ServerSocket(2001);
            Socket soket ;
            while(true){
                soket=server_socket.accept();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
