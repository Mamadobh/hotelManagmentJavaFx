package com.example.client_hotel_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class HelloController  implements Initializable {

    @FXML
    private Button Clear_btn;

    @FXML
    private TableColumn<?, ?> Date_debut;

    @FXML
    private TableView<Demande> Demande;

    @FXML
    private TableColumn<?, ?> Nb_jours;

    @FXML
    private TableColumn<?, ?> Num_demande;

    @FXML
    private TableColumn<?, ?> Type_chambre;

    @FXML
    private Label admin_name;

    @FXML
    private Button btn_add;

    @FXML
    private AnchorPane container;

    @FXML
    private TableColumn<?, ?> date_debut_column;

    @FXML
    private TableColumn<?, ?> date_fin_column;

    @FXML
    private Button demande_button;

    @FXML
    private TableColumn<?, ?> email_reservation_column;

    @FXML
    private TableColumn<?, ?> id_reservation_column;

    @FXML
    private DatePicker input_date;

    @FXML
    private TextField input_demande;

    @FXML
    private TextField input_nbjr;

    @FXML
    private TableColumn<?, ?> num_room_reservation_column;

    @FXML
    private Button reservation_button;

    @FXML
    private AnchorPane reservation_container;

    @FXML
    private TableView<?> reservation_tabView;

    @FXML
    private AnchorPane room_container;

    @FXML
    private TextField search;

    @FXML
    private TextField search_reservation;

    @FXML
    private TableColumn<?, ?> total_column;
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private ComboBox<String> type_choice;

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
    void number_field(TextField numberField){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String input = change.getText();
            if (input.matches("\\d*\\.?\\d*")){
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        numberField.setTextFormatter(textFormatter);
    }
    private ObservableList<Demande> list_demande=null;
    public  void showListDemande(){
        try {
            list_demande= FXCollections.observableArrayList();
            String req ="select * from demande";
            connect=MaConnection.conncet();
            prepare=connect.prepareStatement(req);
            result= prepare.executeQuery();
            Demande demande;
            while(result.next()){
                demande=new Demande(result.getInt(1),result.getString(2),
                        result.getDate(4).toLocalDate(),result.getInt(5));
                list_demande.add(demande);
            }
            Num_demande.setCellValueFactory(new PropertyValueFactory<>("num_demande"));
            Type_chambre.setCellValueFactory(new PropertyValueFactory<>("type_room"));
            Date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            Nb_jours.setCellValueFactory(new PropertyValueFactory<>("nbj"));

            Demande.setItems(list_demande);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void addDemande(ActionEvent event) {
        String req="insert into demande  values(?,?,?,?,?)";
        connect = MaConnection.conncet();
        Alert alert;
        if(input_demande.getText().isEmpty() || type_choice.getSelectionModel().getSelectedItem()==null
                || input_date.getValue()==null || input_nbjr.getText().isEmpty()  ){
            alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Data");
            alert.setHeaderText(null);
            alert.setContentText("Please fill  all the all the fields ");
            alert.showAndWait();
        }else{
            String checkData_req="select Num_demande from demande  where Num_demande= '"+input_demande.getText()+"'";
            try {
                statement=connect.createStatement();
                result=statement.executeQuery(checkData_req);
                if(result.next()){
                    alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Existing Data");
                    alert.setHeaderText(null);
                    alert.setContentText("Num demande  : "+input_demande.getText()+" was already exist ! ");
                    alert.showAndWait();
                }
                else{

                    prepare=connect.prepareStatement(req);
                    prepare.setInt(5,Integer.parseInt(input_nbjr.getText()));
                    prepare.setDate(4,Date.valueOf(input_date.getValue()));
                    prepare.setInt(1,Integer.parseInt(input_demande.getText()));
                    prepare.setString(2, type_choice.getSelectionModel().getSelectedItem());
                    prepare.setString(3,"me@@.com");

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data Added Successfully");
                    alert.setHeaderText(null);
                    alert.setContentText("Your data has been added successfully.");
                    alert.showAndWait();
                    showListDemande();

                    //   resetReservationData();
                    //showListReservation();
                    //  l1.get(0).write("welcom confiramtion");
                   // dash_totalReservation();

                   pw.println(input_demande.getText()+" "+type_choice.getSelectionModel().getSelectedItem()+" "+"me@@.com"+" "+input_date.getValue().toString()+" "+input_nbjr.getText());pw.flush();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void clearDemande(ActionEvent event) {

    }

    @FXML
    void reservation_selected(MouseEvent event) {

    }

    @FXML
    void room_selected(MouseEvent event) {

    }

    @FXML
    void searchReservation(KeyEvent event) {

    }

    @FXML
    void searchRoom(KeyEvent event) {

    }

    @FXML
    void show_interface(ActionEvent event) {

    }
    Socket sk;
    PrintWriter pw;
    BufferedReader br;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       load_type_room();
        try {
             sk=new Socket("127.0.0.1",2002);
            br=new BufferedReader(new InputStreamReader(sk.getInputStream()));
            pw=new PrintWriter(sk.getOutputStream());
            pw.println("me@@.com");pw.flush();
            new Thread(()->{
                try {
                    while(true){
                        String ch=br.readLine();
                        System.out.println(ch);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TextField[] list_field ={input_demande,input_nbjr};
        for(TextField input:list_field){
            number_field(input);
        }
       showListDemande();
        Callback<DatePicker, DateCell> dayCellFactoryDebut = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
        input_date.setDayCellFactory(dayCellFactoryDebut);
    }
}
/*
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
* */