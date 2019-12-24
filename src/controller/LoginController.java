package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.DBConnection;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txt_user;
    @FXML
    private PasswordField txt_psw;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_sign_up;

    public static String username;

    Connection con = null;
    Statement statement = null;
    ResultSet resultSet = null;

    // ღილაკზე დაჭერის მეთოდი
    public void handleButtonAction(ActionEvent actionEvent){
        if (actionEvent.getSource() == btn_login){
            if (logIn().equals("Success")) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/dashboard.fxml"));
                    AnchorPane pane = (AnchorPane) fxmlLoader.load();
                    rootPane.getChildren().setAll(pane);
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        else if (actionEvent.getSource() == btn_sign_up){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/signup.fxml"));
                AnchorPane pane = (AnchorPane) fxmlLoader.load();
                rootPane.getChildren().setAll(pane);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    // მონაცემთა ბაზის კონსტრუქტორი
    public LoginController() throws Exception{
        con = DBConnection.connectDB();
    }

    // ავტორიზაციის მეთოდი
    private String logIn(){
        String status = "Success";
        username = txt_user.getText();
        String password = txt_psw.getText();

        // ამოწმებს არის თუ არა ცარიელი შესავსები ველი
        if(username.isEmpty() || password.isEmpty()) {
            txt_user.setStyle("-fx-text-box-border: red;");
            txt_psw.setStyle("-fx-text-box-border: red;");
            status = "Error";
        } else {
            String sql = String.format("SELECT * FROM USERS where USERNAME = '%s' and PASSWORD = '%s';", username, password);
            try {
                statement = con.createStatement();
                resultSet = statement.executeQuery(sql);

                if (!resultSet.next()) {
                    txt_user.setStyle("-fx-text-box-border: red;");
                    txt_psw.setStyle("-fx-text-box-border: red;");
                    status = "Error";
                } else {
                    txt_user.setStyle("-fx-text-box-border: green;");
                    txt_psw.setStyle("-fx-text-box-border: green;");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                status = "Exception";
            }
        }

        return status;
    }
}
