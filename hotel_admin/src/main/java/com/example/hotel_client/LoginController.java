package com.example.hotel_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
 public  String AdName;
    @FXML
    private TextField tf1;

    @FXML
    private TextField tf2;
    @FXML
    private Label l;
    @FXML
    private PasswordField tf3;



    @FXML
    private Button tf5;

        @FXML
    void mthd5(ActionEvent event) {
        if(tf5.getText().equals("S'inscrire")){
            try {
                Connection con= MaConnection.conncet();
                Statement s = con.createStatement();
                s.executeUpdate("insert into admin values ('"+tf1.getText()+"','"+tf2.getText()+"','"+tf3.getText()+"')");
                System.out.println("inscris");
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                Connection con= MaConnection.conncet();
                Statement s = con.createStatement();
                ResultSet r=s.executeQuery("select * from admin where username='"+tf1.getText()+"' and password='"+tf3.getText()+"'");
                if(r.next()) {
                    System.out.println("connecté");
                    AdName=tf1.getText();
                    l.getScene().getWindow().hide();
                    Stage home_admin = new Stage();
                    try {
                      HBox fxml_admin = FXMLLoader.load(getClass().getResource("Admin_interface.fxml"));

                        Scene scene =new Scene(fxml_admin);
                        home_admin.setScene(scene);


                        home_admin.show();
                        System.out.println("valide passage ");
                    } catch (IOException e) {
                        System.out.println(" no no valide passage ");
                        throw new RuntimeException(e);

                    }
                }else
                    new Alert(Alert.AlertType.WARNING,"Vérifiez vos informations").showAndWait();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection con= MaConnection.conncet();
            Statement s=con.createStatement();
            ResultSet r=s.executeQuery("select * from admin");
            if (r.next()) {
                tf5.setText("Se connecter");
                l.setVisible(false);
                tf2.setVisible(false);
            } else {
                tf5.setText("S'inscrire");
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Image im=new Image(getClass().getResourceAsStream("logo_hotel.png"));
        img1.setImage(im);
        img2.setImage(im);
    }
}
