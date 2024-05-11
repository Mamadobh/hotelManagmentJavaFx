import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Recevoir  extends  Thread{

    Socket sk;
    String msg="socket !";
    BufferedReader br;
    Recevoir(Socket sk){
        this.sk=sk;
        try {
            br=new BufferedReader(new InputStreamReader(sk.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void run(){
        while(true){
            try {
                br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
