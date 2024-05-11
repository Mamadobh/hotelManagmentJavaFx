package com.example.hotel_client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.UnaryOperator;

public class HelloController implements Initializable {
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private Button update;
    @FXML
    private Button demande_button;
    @FXML
    private TextField search_demande;
    @FXML
    private TableView<?> demande_tabView;
    @FXML
    private TableColumn<?,?>  numDemande_demande;
    @FXML
    private TableColumn<?,?>  type_demande;
    @FXML
    private TableColumn<?,?>  email_demande;
    @FXML
    private TableColumn<?,?>  date_debutDemande;
    @FXML
    private TableColumn<?,?>  nbj_demande;

    @FXML
    private Button refuse;
    @FXML
    private Label admin_name;
    @FXML
    private TableView<User> user_tabView;
    @FXML
    private Button btn_add;
    @FXML
    private DatePicker date_debut_reservation;
    @FXML
    private TextField searchUser;
    @FXML
    private Button delete_btn;

    @FXML
    private TextField input_id_reservation;
    @FXML
    private TableColumn<?, ?> total_column;
    @FXML
    private ComboBox<String> choice_sexe;

    @FXML
    private ComboBox<String> choice_user;

    @FXML
    private ComboBox<String> type_choice;
    @FXML
    private TextField input_price;

    @FXML
    private TextField input_room;
    @FXML
    private TableColumn<?, ?> num_room_column;

    @FXML
    private TableColumn<?, ?> price_column;

    @FXML
    private TableColumn<?, ?> type_column;
    @FXML
    private TableView<Room> room_tabView;

    @FXML
    private TextField search;
    @FXML
    private Label total_res;

    @FXML
    private Label total_room;

    @FXML
    private TableColumn<?, ?> id_reservation_column;
    @FXML
    private Label total_users;

    @FXML
    private AnchorPane container;

    @FXML
    private AnchorPane dash_continer;
    @FXML
    private AnchorPane room_container;

    @FXML
    private Button dashboard_interface;
    @FXML
    private Button room_iterface;
    @FXML
    private AnchorPane user_container;
    @FXML
    private Button Clear_user;

    @FXML
    private TableColumn<?, ?> adresse_column;

    @FXML
    private Button btn_add_user;

    @FXML
    private Button btn_import;
    @FXML
    private Button delete_btn_user;

    @FXML
    private TableColumn<?, ?> email_column;

    @FXML
    private TableColumn<?, ?> generation_column;

    @FXML
    private ImageView img_import;

    @FXML
    private TextField input_adresse;

    @FXML
    private ComboBox<String> input_generation;

    @FXML
    private TextField input_name;

    @FXML
    private BarChart<?, ?> chart;

    @FXML
    private TextField input_password;

    @FXML
    private TableColumn<?, ?> name_column;

    @FXML
    private TableColumn<?, ?> password_column;

    @FXML
    private TableColumn<?, ?> phone_column;


    @FXML
    private TextField input_email;
    @FXML
    private Button user_button;
    @FXML
    private Label total_reservation;
    @FXML
    private TableColumn<?, ?> sexe_column;
    @FXML
    private Button Clear_btn_reservation;
    @FXML
    private Button btn_add_reservation;
    @FXML
    private TableColumn<?, ?> date_debut_column;
    @FXML
    private TableColumn<?, ?> date_fin_column;
    @FXML
    private Button delete_btn_reservation;
    @FXML
    private TableColumn<?, ?> email_reservation_column;
    @FXML
    private ComboBox<String> input_num_room_reservation;
    @FXML
    private Button reservation_button;

    @FXML
    private AnchorPane reservation_container;
    @FXML
    private TextField search_reservation;
    @FXML
    private TableView<Rservation> reservation_tabView;

    @FXML
    private TableColumn<?, ?> num_room_reservation_column;
    @FXML
    private ComboBox<String> choice_email_reservation;
    @FXML
    private DatePicker date_fin_reservation;


    @FXML
    private TextField input_phone;
    @FXML
    private TableColumn<?, ?> appUser_column;
    @FXML
    void show_interface(ActionEvent event) {
        AnchorPane [] interface_con={room_container,dash_continer,user_container,reservation_container};
        String ch = ((Button) event.getSource()).getText();
        for(int i=0;i<interface_con.length;i++){

         if(interface_con[i]!=null)
         {
             interface_con[i].setVisible(false);

         }  }
        if (ch.equals("Dashborad")) {
            dash_continer.setVisible(true);
            dash_chart();
            dash_totalReservation();
            dash_totalUsers();
            dash_totalRoom();
        }
        if (ch.equals("Room")) {
            room_container.setVisible(true);
        }
        if (ch.equals("Users")) {
            user_container.setVisible(true);
        }
        if(ch.equals("Reservations")){
reservation_container.setVisible(true);
        }

    }

    public void dash_totalUsers(){
        String req="select count(Email) AS count_res from client";
        connect=MaConnection.conncet();
        int count_users=0;
        try {
            prepare=connect.prepareStatement(req);
            result=prepare.executeQuery();
            while (result.next()){
                count_users=result.getInt("count_res");
            }
            total_users.setText(String.valueOf(count_users));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void dash_totalReservation(){
        String req="select count(Id_reservation) AS count_res from reservation";
        connect=MaConnection.conncet();
        int count_reservation=0;
        try {
            prepare=connect.prepareStatement(req);
            result=prepare.executeQuery();
            while (result.next()){
                count_reservation=result.getInt("count_res");
            }
            total_reservation.setText(String.valueOf(count_reservation));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void dash_totalRoom(){
        String req="select count(Num_room) AS count_res from room";
        connect=MaConnection.conncet();
        int count_Room=0;
        try {
            prepare=connect.prepareStatement(req);
            result=prepare.executeQuery();
            while (result.next()){
                count_Room=result.getInt("count_res");
            }
            total_room.setText(String.valueOf(count_Room));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dash_chart(){
     chart.getData().clear();
     String req="SELECT c.Generation,COUNT(r.id_reservation) As count_E FROM client c,reservation r where c.Email=r.Email GROUP BY c.Generation";
    connect=MaConnection.conncet();
        try {
            XYChart.Series cht=new XYChart.Series();
            statement=connect.createStatement();
            result= statement.executeQuery(req);
            System.out.println("test1");
            while (result.next()){
                cht.getData().add(new XYChart.Data(result.getString("Generation"),result.getInt("count_E")));
              //  System.out.println("generation = "+result.getString("Generation")+"count = "+result.getInt("count_E"));
            }
            chart.getData().add(cht);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void choose_type() {
    }

    void number_field(TextField numberField){
        UnaryOperator<Change> filter = change -> {
            String input = change.getText();
            if (input.matches("\\d*\\.?\\d*")){
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        numberField.setTextFormatter(textFormatter);
    }

    void load_type_room(){
      List<String> list_type=new ArrayList<>();
      String req ="select * from type";
      connect =MaConnection.conncet();
      try {
          prepare=connect.prepareStatement(req);
          result=prepare.executeQuery();
          while(result.next()){
              String type;
              type=result.getString("Type");
              list_type.add(type);
          }
          type_choice.getItems().addAll(list_type);
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }
  void load_sexe(){
      choice_sexe.getItems().addAll("male","female");
  }
  void load_generation(){input_generation.getItems().addAll("18-30","30-45","45-60","60<");}
    void load_user(){
        choice_user.getItems().addAll("Oui","Non");
    }
public void resetRoomData(){

    type_choice.getSelectionModel().clearSelection();
    input_room.clear();
    input_price.clear();
    search.clear();
    type_choice.setPromptText("Choose");


}
  public void addRoom(){
       String req="insert into room values(?,?,?)";
       connect = MaConnection.conncet();
      Alert alert;
   if(input_room.getText().isEmpty() || input_price.getText().isEmpty() || type_choice.getSelectionModel().getSelectedItem()==null){
       alert =new Alert(Alert.AlertType.ERROR);
       alert.setTitle("Missing Data");
       alert.setHeaderText(null);
       alert.setContentText("Please fill  all the all the fields ");
       alert.showAndWait();
   }else{
       String checkData_req="select Num_room from room where Num_room= '"+input_room.getText()+"'";
       try {
           statement=connect.createStatement();
           result=statement.executeQuery(checkData_req);
           if(result.next()){
               alert =new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Existing Data");
               alert.setHeaderText(null);
               alert.setContentText("Num room : "+input_room.getText()+" was already exist ! ");
               alert.showAndWait();
           }
          else{
               prepare=connect.prepareStatement(req);
               prepare.setInt(1,Integer.parseInt(input_room.getText()));
               prepare.setDouble(2,Double.parseDouble(input_price.getText()));
               prepare.setString(3,type_choice.getSelectionModel().getSelectedItem());
               prepare.executeUpdate();
               alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Data Added Successfully");
               alert.setHeaderText(null);
               alert.setContentText("Your data has been added successfully.");
               alert.showAndWait();
               resetRoomData();
               show_RoomList();
               input_num_room_reservation.getSelectionModel().clearSelection();
               loadNumroom();
               dash_totalRoom();

           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
  }
  private ObservableList<Room>lis_rooom=null;
  void show_RoomList(){

      try {
          lis_rooom= FXCollections.observableArrayList();
          String req ="select * from room";
          connect=MaConnection.conncet();
          prepare=connect.prepareStatement(req);
          result= prepare.executeQuery();
          Room room;
          while(result.next()){
              room=new Room(result.getInt("Num_room"),result.getDouble("price"),result.getString("Type"));
              lis_rooom.add(room);

          }
          num_room_column.setCellValueFactory(new PropertyValueFactory<>("numRoom"));
          price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
          type_column.setCellValueFactory(new PropertyValueFactory<>("type"));
          room_tabView.setItems(lis_rooom);
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }
    @FXML
    void updateRoom(ActionEvent event) {
      String req="update room set Price=?,Type=? where Num_room=?";
      connect=MaConnection.conncet();
      try {
          Alert alert;
          if(input_room.getText().isEmpty() || input_price.getText().isEmpty() || type_choice.getSelectionModel().getSelectedItem()==null){
              alert =new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Missing Data");
              alert.setHeaderText(null);
              alert.setContentText("Please fill  all the all the fields ");
              alert.showAndWait();}
          else {
              String checkData_req="select Num_room from room where Num_room= '"+input_room.getText()+"'";

              statement=connect.createStatement();
              result=statement.executeQuery(checkData_req);
              if(!(result.next())){
                  alert =new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Not availble Data");
                  alert.setHeaderText(null);
                  alert.setContentText("Num room : "+input_room.getText()+" was already not exist !");
                  alert.showAndWait();

              }
              else{
              alert=new Alert(Alert.AlertType.CONFIRMATION);
              alert.setTitle("Confirmation request");
              alert.setHeaderText(null);
              alert.setContentText("Are you sure want to update room num "+input_room.getText() +"?");
              Optional<ButtonType> option=alert.showAndWait();
              if(option.get().equals(ButtonType.OK)){

                          prepare=connect.prepareStatement(req);
                          prepare.setDouble(1,Double.parseDouble(input_price.getText()));
                          prepare.setString(2,type_choice.getSelectionModel().getSelectedItem());
                          prepare.setInt(3,Integer.parseInt(input_room.getText()));
                          prepare.executeUpdate();
                          alert = new Alert(Alert.AlertType.INFORMATION);
                          alert.setTitle("Data update Successfully");
                          alert.setHeaderText(null);
                          alert.setContentText("Your data has been update successfully.");
                          alert.showAndWait();
                          resetRoomData();
                          show_RoomList();
                          input_num_room_reservation.getItems().clear();
                          loadNumroom();
                          showListReservation();
              }}
          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }

    @FXML
    void searchRoom(KeyEvent event) {


        FilteredList<Room> filter = new FilteredList<>(lis_rooom, e -> true);
        search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(item -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String current_val = newValue.toLowerCase();
                if (Integer.toString(item.getNumRoom()).toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (Double.toString(item.getPrice()).toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (item.getType().toLowerCase().contains(current_val)) {
                    return true;
                }
                else {
                    return false;
                }
            });
            SortedList<Room> sortList = new SortedList<>(filter);

            sortList.comparatorProperty().bind(room_tabView.comparatorProperty());
            room_tabView.setItems(sortList);
        });
    }
    @FXML
    void room_selected(MouseEvent event) {
      Room room=room_tabView.getSelectionModel().getSelectedItem();
      int index_selected=room_tabView.getSelectionModel().getSelectedIndex();
      if((index_selected - 1)<-1){
          return;
          //disable the null click
      }
      input_room.setText(String.valueOf((room.getNumRoom())));
      input_price.setText(String.valueOf((room.getPrice())));
      type_choice.setValue(room.getType());

    }

    @FXML
    void deleteRoom(ActionEvent event) {
     String req ="delete from room where Num_room=?";
      connect=MaConnection.conncet();
    try {
        Alert alert;
        if(input_room.getText().isEmpty() || input_price.getText().isEmpty() || type_choice.getSelectionModel().getSelectedItem()==null){
            alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Data");
            alert.setHeaderText(null);
            alert.setContentText("Please fill  all the all the fields ");
            alert.showAndWait();}
        else {
            String checkData_req="select Num_room from room where Num_room= '"+input_room.getText()+"'";

            statement=connect.createStatement();
            result=statement.executeQuery(checkData_req);
            if(!(result.next())){
                alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not availble Data");
                alert.setHeaderText(null);
                alert.setContentText("Num room : "+input_room.getText()+" was already not exist !");
                alert.showAndWait();

            }
            else{
                alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation request");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to update room num "+input_room.getText() +"?");
                Optional<ButtonType> option=alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){

                    prepare=connect.prepareStatement(req);
                    prepare.setInt(1,Integer.parseInt(input_room.getText()));
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data Delete it  Successfully");
                    alert.setHeaderText(null);
                    alert.setContentText("Your data has been Delete successfully.");
                    alert.showAndWait();
                    resetRoomData();
                    show_RoomList();
                    input_num_room_reservation.getItems().clear();
                    loadNumroom();
                    showListReservation();
                    //dash_totalReservation();
                    dash_totalRoom();
                   // dash_totalUsers();
                }}
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}
//---------------------------------------------------------------
private ObservableList<User>list_user=null;
private ArrayList<byte[]> liste_image =new ArrayList<>();

        /*
        byte []b=null;
        b=rs.getBlob(5).getBytes(1,(int)rs.getBlob(5).length());
        t5.setImage(new Image(new ByteArrayInputStream(b),t5.getFitHeight(),t5.getFitWidth(),true,true));
        * */
   public void  show_UserList(){

       try {
           list_user= FXCollections.observableArrayList();
           String req ="select * from client";
           connect=MaConnection.conncet();
           prepare=connect.prepareStatement(req);
           result= prepare.executeQuery();
           User room;
           while(result.next()){
               room=new User(result.getString("Email"),result.getString("password"),result.getString("Phone_number"),
                       result.getString("Generation"),result.getString("App_user"),result.getString("Adresse"),
                       result.getString("Name"),result.getString("Sexe"));
               list_user.add(room);
           }
           email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
           password_column.setCellValueFactory(new PropertyValueFactory<>("password"));
           phone_column.setCellValueFactory(new PropertyValueFactory<>("phone_num"));
           generation_column.setCellValueFactory(new PropertyValueFactory<>("generation"));
           appUser_column.setCellValueFactory(new PropertyValueFactory<>("isAppUser"));
           adresse_column.setCellValueFactory(new PropertyValueFactory<>("adresse"));
           name_column.setCellValueFactory(new PropertyValueFactory<>("prénom"));
           sexe_column.setCellValueFactory(new PropertyValueFactory<>("sexe"));
           appUser_column.setCellValueFactory(new PropertyValueFactory<>("isAppUser"));
           user_tabView.setItems(list_user);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    @FXML
    void user_selected(MouseEvent event) {
        User room=user_tabView.getSelectionModel().getSelectedItem();
        int index_selected=user_tabView.getSelectionModel().getSelectedIndex();
        if((index_selected - 1)<-1){
            return;
            //disable the null click
        }
        input_email.setText(room.email);
        input_name.setText(room.prénom);
        input_phone.setText(room.phone_num);
        input_adresse.setText(room.adresse);
        input_password.setText(room.password);
        input_generation.setValue(room.generation);
        choice_sexe.setValue(room.sexe);
        choice_user.setValue(room.isAppUser);
        String req ="select Avatar from client where email='"+input_email.getText()+"'";
        connect=MaConnection.conncet();
        try {
            statement=connect.createStatement();
            result= statement.executeQuery(req);
            result.next();
            Blob blob = result.getBlob(1);
            byte byteImage[]= blob.getBytes(1,(int)blob.length());
            Image img=new Image(new ByteArrayInputStream(byteImage), img_import.getFitWidth(), img_import.getFitHeight(), false, true);
            img_import.setImage(img);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
@FXML
void deleteUser(ActionEvent event) {
   String req ="delete from client where Email=?";
    connect=MaConnection.conncet();
    try {
        Alert alert;
        if(input_email.getText().isEmpty() ||input_password.getText().isEmpty() || input_phone.getText().isEmpty() || choice_sexe.getSelectionModel().getSelectedItem()==null
                || input_adresse.getText().isEmpty()|| input_generation.getSelectionModel().getSelectedItem()==null
                || input_name.getText().isEmpty() || choice_user.getSelectionModel().getSelectedItem()==null
        ){     alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Data");
            alert.setHeaderText(null);
            alert.setContentText("Please fill  all the all the fields ");
            alert.showAndWait();}
        else {
            String checkData_req="select Email from client where Email= '"+input_email.getText()+"'";

            statement=connect.createStatement();
            result=statement.executeQuery(checkData_req);
            if(!(result.next())){
                alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not availble Data");
                alert.setHeaderText(null);
                alert.setContentText("Email : "+input_email.getText()+" was already not exist !");
                alert.showAndWait();

            }
            else{
                alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation request");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to delete data for  client  "+input_room.getText() +"?");
                Optional<ButtonType> option=alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){

                    prepare=connect.prepareStatement(req);
                    prepare.setString(1,input_email.getText());
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data Delete it  Successfully");
                    alert.setHeaderText(null);
                    alert.setContentText("Your data has been Delete successfully.");
                    alert.showAndWait();
                    resetUser();
                    show_UserList();
                    choice_email_reservation.getItems().clear();
                    loadEmail();
                    showListReservation();
                    dash_totalUsers();
                }}
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}
    @FXML
    public void resetUser() {
        choice_user.getSelectionModel().clearSelection();
        choice_sexe.getSelectionModel().clearSelection();
        input_generation.getSelectionModel().clearSelection();
        input_email.clear();
        input_adresse.clear();
        input_password.clear();
        input_phone.clear();
        input_name.clear();
        img_import.setImage(null);
    }

    @FXML
    void addUser(ActionEvent event) {
        String req="insert into client values(?,?,?,?,?,?,?,?,?)";
        connect = MaConnection.conncet();
        Alert alert;
        if(input_email.getText().isEmpty() ||input_password.getText().isEmpty() || input_phone.getText().isEmpty() || choice_sexe.getSelectionModel().getSelectedItem()==null
           || input_adresse.getText().isEmpty()|| input_generation.getSelectionModel().getSelectedItem()==null
           || input_name.getText().isEmpty() || choice_user.getSelectionModel().getSelectedItem()==null
        ){
            alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Data");
            alert.setHeaderText(null);
            alert.setContentText("Please fill  all the all the fields ");
            alert.showAndWait();
        }else{
            String checkData_req="select Email from client where Email= '"+input_email.getText()+"'";
            try {
                statement=connect.createStatement();
                result=statement.executeQuery(checkData_req);
                if(result.next()){
                    alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Existing Data");
                    alert.setHeaderText(null);
                    alert.setContentText("Email : "+input_email.getText()+" was already exist ! ");
                    alert.showAndWait();
                }
                else{
                    byte[] imageBytes = Files.readAllBytes(Paths.get(img_import.getImage().getUrl().toString()));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes); prepare=connect.prepareStatement(req);
                    prepare.setString(1,input_email.getText());
                    prepare.setBinaryStream(2, imageStream, imageBytes.length);
                    prepare.setString(3,input_name.getText());
                    prepare.setString(4,input_password.getText());
                    prepare.setString(5,input_phone.getText());
                    prepare.setString(6,input_adresse.getText());
                    prepare.setString(7,choice_sexe.getSelectionModel().getSelectedItem());
                    prepare.setString(8,input_generation.getSelectionModel().getSelectedItem());
                    prepare.setString(9,choice_user.getSelectionModel().getSelectedItem());
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data Added Successfully");
                    alert.setHeaderText(null);
                    alert.setContentText("Your data has been added successfully.");
                    alert.showAndWait();
                    resetUser();
                    show_UserList();
                    choice_email_reservation.getItems().clear();
                    loadEmail();
                    dash_totalUsers();

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void updateUser(ActionEvent event) {
        String req;
       if(img_import.getImage().getUrl()!=null){
           req="update client set Name=?,password=?,Phone_number=?,Adresse=?,Sexe=?,Generation=?,App_user=?,Avatar=? where Email=? ";

       }
       else{
           req="update client set Name=?,password=?,Phone_number=?,Adresse=?,Sexe=?,Generation=?,App_user=?  where Email=?";
       }
                connect=MaConnection.conncet();
        try {
            Alert alert;
            if(input_email.getText().isEmpty() ||input_password.getText().isEmpty() || input_phone.getText().isEmpty() || choice_sexe.getSelectionModel().getSelectedItem()==null
                    || input_adresse.getText().isEmpty()|| input_generation.getSelectionModel().getSelectedItem()==null
                    || input_name.getText().isEmpty() || choice_user.getSelectionModel().getSelectedItem()==null
            ){   alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Data");
                alert.setHeaderText(null);
                alert.setContentText("Please fill  all the all the fields ");
                alert.showAndWait();}
            else {
                String checkData_req="select Email from client where Email= '"+input_email.getText()+"'";

                statement=connect.createStatement();
                result=statement.executeQuery(checkData_req);
                if(!(result.next())){
                    alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Not availble Data");
                    alert.setHeaderText(null);
                    alert.setContentText("Email : "+input_email.getText()+" was already not exist !");
                    alert.showAndWait();

                }
                else{
                    alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation request");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure want to update client  who has email = "+input_email.getText() +"?");
                    Optional<ButtonType> option=alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)){


                        prepare=connect.prepareStatement(req);

                        prepare.setString(1,input_name.getText());
                        prepare.setString(2,input_password.getText());
                        prepare.setString(3,input_phone.getText());
                        prepare.setString(4,input_adresse.getText());
                        prepare.setString(5,choice_sexe.getSelectionModel().getSelectedItem());
                        prepare.setString(6,input_generation.getSelectionModel().getSelectedItem());
                        prepare.setString(7,choice_user.getSelectionModel().getSelectedItem());
// req="update client set Name=?,password=?,Phone_number=?,Adresse=?,Sexe=?,Generation=?,App_user=?  where Email=?";


                        if(img_import.getImage().getUrl()!=null)
                        { System.out.println("moch null ,"+img_import.getImage().getUrl().toString()!=null);
                            byte[] imageBytes = new byte[0];
                        imageBytes = Files.readAllBytes(Paths.get(img_import.getImage().getUrl().toString()));

                        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
                        prepare.setBinaryStream(8, imageStream, imageBytes.length);
                        prepare.setString(9,input_email.getText());}
                        else{
                            prepare.setString(8,input_email.getText());

                        }
                        prepare.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Data update Successfully");
                        alert.setHeaderText(null);
                        alert.setContentText("Your data has been update successfully.");
                        alert.showAndWait();
                        resetUser();
                        show_UserList();
                        loadEmail();
                        showListReservation();
                    }}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchUser(KeyEvent event) {
        FilteredList<User> filter = new FilteredList<>(list_user, e -> true);
         searchUser.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(item -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String current_val = newValue.toLowerCase();
                if (item.email.toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (item.password.toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (item.adresse.toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (item.generation.toLowerCase().contains(current_val)) {
                    return true;
                }else if (item.prénom.toLowerCase().contains(current_val)) {
                    return true;
                }else if (item.phone_num.toLowerCase().contains(current_val)) {
                    return true;
                }else if (item.sexe.toLowerCase().contains(current_val)) {
                    return true;
                }else if (item.isAppUser.toLowerCase().contains(current_val)) {
                    return true;
                }
                else {
                    return false;
                }
            });
            SortedList<User> sortList = new SortedList<>(filter);

            sortList.comparatorProperty().bind(user_tabView.comparatorProperty());
            user_tabView.setItems(sortList);
        });

    }
    @FXML
    void import_mg(ActionEvent event) {
        FileChooser fc=new FileChooser();

        fc.setInitialDirectory(new File("/"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG","*.jpg"),new FileChooser.ExtensionFilter("PNG","*.png"));
        File f=fc.showOpenDialog(null);
        if(f!=null){
            if (f.length() > 600000) { // 1MB limit
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File too large");
                alert.setHeaderText(null);
                alert.setContentText("Please select a file smaller than 600ko.");
                alert.showAndWait();
            } else {
                img_import.setImage(new Image(f.getAbsolutePath(), img_import.getFitWidth(), img_import.getFitHeight(), false, true));
            }
            // img_import.setImage(new Image(f.getAbsolutePath(),img_import.getFitWidth(),img_import.getFitHeight(),false,true));
        }



    }

//----------------------------------------------------------------------------------------------
    public void loadEmail(){
        List<String> list_email=new ArrayList<>();
        String req ="select Email from client";
        connect =MaConnection.conncet();
        try {
            prepare=connect.prepareStatement(req);
            result=prepare.executeQuery();
            while(result.next()){
                String email;
                email=result.getString(1);
                list_email.add(email);
            }
            choice_email_reservation.getItems().addAll(list_email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
    public void loadNumroom(){
        List<String> list_r=new ArrayList<>();
        String req ="select Num_room from room";
        connect =MaConnection.conncet();
        try {
            prepare=connect.prepareStatement(req);
            result=prepare.executeQuery();
            while(result.next()){
                String rm;
                rm=result.getString(1);
                list_r.add(rm);
            }
            input_num_room_reservation.getItems().addAll(list_r);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
    private ObservableList<Rservation>list_reservation=null;
    public  void showListReservation(){
        try {
            list_reservation= FXCollections.observableArrayList();
            String req ="select * from reservation";
            connect=MaConnection.conncet();
            prepare=connect.prepareStatement(req);
            result= prepare.executeQuery();
            Rservation reservation;
            while(result.next()){
                reservation=new Rservation(result.getString(1),result.getDate(2).toLocalDate(),
                        result.getDate(3).toLocalDate(),result.getDouble(4),result.getInt(5),result.getString(6));
                list_reservation.add(reservation);
            }
            id_reservation_column.setCellValueFactory(new PropertyValueFactory<>("id_reservation"));
           date_debut_column.setCellValueFactory(new PropertyValueFactory<>("Date_debut"));
            date_fin_column.setCellValueFactory(new PropertyValueFactory<>("Date_fin"));
            total_column.setCellValueFactory(new PropertyValueFactory<>("Total"));
            num_room_reservation_column.setCellValueFactory(new PropertyValueFactory<>("Num_room"));
            email_reservation_column.setCellValueFactory(new PropertyValueFactory<>("Email"));

            reservation_tabView.setItems(list_reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void searchReservation(KeyEvent event) {
        FilteredList<Rservation> filter = new FilteredList<>(list_reservation, e -> true);
        search_reservation.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(item -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String current_val = newValue.toLowerCase();
                if (Integer.toString(item.Num_room).toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (Double.toString(item.Total).toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (item.getId_reservation().toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (item.Email.toLowerCase().contains(current_val)) {
                    return true;
                }
                else if (item.getDate_fin().toString().contains(current_val)) {
                    return true;
                }
                else if (item.getDate_debut().toString().contains(current_val)) {
                    return true;
                }
                else {
                    return false;
                }
            });
            SortedList<Rservation> sortList = new SortedList<>(filter);

            sortList.comparatorProperty().bind(reservation_tabView.comparatorProperty());
            reservation_tabView.setItems(sortList);
        });

    }

    @FXML
    void reservation_selected(MouseEvent event) {
        Rservation room=reservation_tabView.getSelectionModel().getSelectedItem();
        int index_selected=reservation_tabView.getSelectionModel().getSelectedIndex();
        if((index_selected - 1)<-1){
            return;
            //disable the null click
        }
        input_id_reservation.setText(room.id_reservation);
        date_debut_reservation.setValue(room.Date_debut);
        date_fin_reservation.setValue(room.Date_fin);
        total_res.setText(String.valueOf(room.Total));
        input_num_room_reservation.setValue(String.valueOf(room.Num_room));
        choice_email_reservation.setValue(String.valueOf(room.Email));
    }

    @FXML
    void addReservation(ActionEvent event) {
        String req="insert into reservation values(?,?,?,?,?,?)";
        connect = MaConnection.conncet();
        Alert alert;
        if(input_id_reservation.getText().isEmpty() || choice_email_reservation.getSelectionModel().getSelectedItem()==null
                || input_num_room_reservation.getSelectionModel().getSelectedItem()==null || date_fin_reservation.getValue()==null ||
        date_debut_reservation.getValue()==null){
            alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Data");
            alert.setHeaderText(null);
            alert.setContentText("Please fill  all the all the fields ");
            alert.showAndWait();
        }else{
            String checkData_req="select id_reservation from reservation where id_reservation= '"+input_id_reservation.getText()+"'";
            try {
                statement=connect.createStatement();
                result=statement.executeQuery(checkData_req);
                if(result.next()){
                    alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Existing Data");
                    alert.setHeaderText(null);
                    alert.setContentText("id reservation : "+input_id_reservation.getText()+" was already exist ! ");
                    alert.showAndWait();
                }
                else{
                    String total_req="select price from room where Num_room="+input_num_room_reservation.getSelectionModel().getSelectedItem();
                    statement=connect.createStatement();
                    result=statement.executeQuery(total_req);
                    result.next();
                    double price_r=result.getDouble(1);

                    prepare=connect.prepareStatement(req);
                    prepare.setInt(5,Integer.parseInt(input_num_room_reservation.getSelectionModel().getSelectedItem()));
                    prepare.setDouble(4,(Period.between(date_debut_reservation.getValue(),date_fin_reservation.getValue()).getDays()+1)*price_r);
                    prepare.setString(6,choice_email_reservation.getSelectionModel().getSelectedItem());
                    prepare.setString(1,input_id_reservation.getText());
                    prepare.setDate(2, Date.valueOf(date_debut_reservation.getValue()));
                    prepare.setDate(3, Date.valueOf(date_fin_reservation.getValue()));

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data Added Successfully");
                    alert.setHeaderText(null);
                    alert.setContentText("Your data has been added successfully.");
                    alert.showAndWait();
                 //   resetReservationData();
                    showListReservation();
                  //  l1.get(0).write("welcom confiramtion");
                    dash_totalReservation();
                    pw.write("");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void resetReservationData() {
        choice_email_reservation.getSelectionModel().clearSelection();
        input_num_room_reservation.getSelectionModel().clearSelection();
        input_id_reservation.clear();
        search_reservation.clear();
        total_res.setText("0");
        date_fin_reservation.setValue(null);
        date_debut_reservation.setValue(null);
    }
    @FXML
    void deleteReservation(ActionEvent event) {
        String req ="delete from reservation where id_reservation=?";
        connect=MaConnection.conncet();
        try {
            Alert alert;
            if(input_id_reservation.getText().isEmpty() || total_res.getText().isEmpty() || choice_email_reservation.getSelectionModel().getSelectedItem()==null
                    || input_num_room_reservation.getSelectionModel().getSelectedItem()==null || date_fin_reservation.getValue()==null || date_debut_reservation.getValue()==null){
                alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Data");
                alert.setHeaderText(null);
                alert.setContentText("Please fill  all the all the fields ");
                alert.showAndWait();}
            else {
                String checkData_req="select id_reservation from reservation where id_reservation= '"+input_id_reservation.getText()+"'";

                statement=connect.createStatement();
                result=statement.executeQuery(checkData_req);
                if(!(result.next())){
                    alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Not availble Data");
                    alert.setHeaderText(null);
                    alert.setContentText("id_reservation  : "+input_id_reservation.getText()+" was already not exist !");
                    alert.showAndWait();

                }
                else{
                    alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation request");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure want to delate reservation with  id "+input_id_reservation.getText() +"?");
                    Optional<ButtonType> option=alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)){

                        prepare=connect.prepareStatement(req);
                        prepare.setString(1,input_id_reservation.getText());
                        prepare.executeUpdate();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Data Deleted it  Successfully");
                        alert.setHeaderText(null);
                        alert.setContentText("Your data has been Delete successfully.");
                        alert.showAndWait();
                        dash_totalReservation();
                        showListReservation();
                    }}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    List<LocalDate> startDateList = new ArrayList<>();
    List<LocalDate> endDateList = new ArrayList<>();

    public void disabled_invalid_date(){
        input_num_room_reservation.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                connect=MaConnection.conncet();
                String sql = "SELECT Date_debut,Date_fin FROM reservation WHERE Num_room = ?";
                prepare=connect.prepareStatement(sql);
                prepare.setInt(1, Integer.parseInt(newValue));
                result = prepare.executeQuery();
                startDateList.clear();
                endDateList.clear();
                while (result.next()) {
                    startDateList.add(result.getDate(1).toLocalDate());
                    endDateList.add(result.getDate(2).toLocalDate());
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Collections.sort(startDateList);
            Collections.sort(endDateList);

            // Disable dates in date_debut_reservation
            Callback<DatePicker, DateCell> dayCellFactoryDebut = dp -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    for (int i = 0; i < startDateList.size(); i++) {
                        LocalDate startDate = startDateList.get(i);
                        LocalDate endDate = endDateList.get(i);
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now()) || !(item.isBefore(startDate) || item.isAfter(endDate))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                }
            };
            date_debut_reservation.setDayCellFactory(dayCellFactoryDebut);
            date_debut_reservation.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
                Callback<DatePicker, DateCell> dayCellFactoryFin = dp -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item.isBefore(newValue1.plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
                date_fin_reservation.setDayCellFactory(dayCellFactoryFin);
            });
        });
      /*  input_num_room_reservation.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                connect=MaConnection.conncet();
                String sql = "SELECT Date_debut,Date_fin FROM reservation WHERE Num_room = ?";
             //   PreparedStatement pstmt = conn.prepareStatement(sql);
                prepare=connect.prepareStatement(sql);
                prepare.setInt(1, Integer.parseInt(newValue));
                result = prepare.executeQuery();
               startDateList.clear();
               endDateList.clear();
                while (result.next()) {
                    startDateList.add(result.getDate(1).toLocalDate());
                    endDateList.add(result.getDate(2).toLocalDate());
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Collections.sort(startDateList);
            Collections.sort(endDateList);Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    for (int i = 0; i < startDateList.size(); i++) {
                        LocalDate startDate = startDateList.get(i);
                        LocalDate endDate = endDateList.get(i);
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now()) || !(item.isBefore(startDate) || item.isAfter(endDate))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                }};


                date_debut_reservation.setDayCellFactory(dayCellFactory);
        });
        date_debut_reservation.valueProperty().addListener((observable, oldValue, newValue) -> {
            int index = Collections.binarySearch(startDateList, newValue);
            Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if ((item.isBefore(newValue) || !(item.isBefore(startDateList.get(-(index+1)))))) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    }
                }
            };
            date_fin_reservation.setDayCellFactory(dayCellFactory);
        });*/
}
/*datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
    int index = Collections.binarySearch(startDateList, newValue);
    Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if ((item.isBefore(newValue) || !(item.isBefore(startDateList.get(-(index+1))))) {
                setDisable(true);
                setStyle("-fx-background-color: #ffc0cb;");
            }

    };
    datePicker.setDayCellFactory(dayCellFactory);
});*/
public void searchDemande(KeyEvent event){

}
    public void demande_selected(MouseEvent event){

    }

    public void refuseDemande(){

    }


Socket sk;
BufferedReader br;
PrintWriter pw;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            sk=new Socket("127.0.0.1",2002);
//            br=new BufferedReader(new InputStreamReader(sk.getInputStream()));
//            pw=new PrintWriter(sk.getOutputStream());
//            new Thread(()->{
//                try {
//                    while(true){
//                        String ch=br.readLine();
//                        System.out.println(ch);
//                    }
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }).start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    /* new Thread(()->{
 while (true){
     try {
         Socket s1=sv.accept();
         PrintWriter p1=new PrintWriter(s1.getOutputStream());
         l1.add(p1);
         BufferedReader b1=new BufferedReader(new InputStreamReader(s1.getInputStream()));
         new Thread(()->{
            while(true){
                try {
                   String ch1= b1.readLine();
                   System.out.println("ch1=====  "+ch1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
         }).start();

     } catch (IOException e) {
         throw new RuntimeException(e);
     }

 }
     }).start();**/
     //

    // -----------------------------------------------------------------
     dash_totalReservation();
     dash_totalRoom();
     dash_totalUsers();
      load_type_room();
      dash_chart();
//   ----------------------------------------------------------------------
     TextField[] list_field ={input_room,input_price,input_phone};
     for(TextField input:list_field){
         number_field(input);
     }
     show_RoomList();
//--------------------------------------------------------------------------------------
        load_sexe();
        load_user();
        show_UserList();
        load_generation();
//---------------------------------------------------------------------------------------------
loadEmail();
loadNumroom();
showListReservation();
disabled_invalid_date();
    }

        }



