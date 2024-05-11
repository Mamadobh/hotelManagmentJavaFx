package com.example.client_hotel_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Envoyer extends  Thread{
   Socket sk;
   String msg="socket !";
   PrintWriter pw;
   Envoyer(Socket sk,String msg){
       this.sk=sk;
       this.msg=msg;
       try {
           pw=new PrintWriter(sk.getOutputStream());
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }
   public void run(){
       while(true){

           pw.write(msg);
       }
   }
}
