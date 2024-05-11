import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String,Socket> list=new HashMap<>();
        try {
            ServerSocket server_socket=new ServerSocket(2002);
            Socket soket ;
            while(true){
                soket=server_socket.accept();
                BufferedReader br=new BufferedReader(new InputStreamReader(soket.getInputStream()));
                String s=br.readLine();
                PrintWriter pw=new PrintWriter(soket.getOutputStream());
                list.put(s,soket);
                new Thread(()->{
                    while(true){
                        try {
                            String ch=br.readLine();
                            System.out.println(ch);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}