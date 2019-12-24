package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btn_click;
    @FXML
    private Label lbl_txt;
    @FXML
    private Button btn_back;

    String username;
    String age;
    String password;
    String gender;
    String email;

    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    // ღილაკზე დაჭერის მეთოდი
    public void handleButtonAction(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btn_click){
            selectQuery();
            lbl_txt.setText("Hello " + username + " You are " + age + " years old!\n " +
                    "Your password is " + password + "\n Your gender is " + gender + "\n and your email is " + email);
        } else if (actionEvent.getSource() == btn_back){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
            AnchorPane pane = (AnchorPane) fxmlLoader.load();
            rootPane.getChildren().setAll(pane);
        }
    }

    // მონაცემთა ბაზის კონსტრუქტორი
    public DashboardController() throws Exception{
        con = DBConnection.connectDB();
    }

    // მოაქვს ავტორიზებული მომხმარებლის მონაცემები
    private void selectQuery(){
        try {
            String query = String.format("SELECT * FROM USERS WHERE USERNAME = '%s'", LoginController.username);
            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()){
                username = rs.getString("USERNAME");
                age = rs.getString("AGE");
                password = rs.getString("PASSWORD");
                gender = rs.getString("GENDER");
                email = rs.getString("EMAIL");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}