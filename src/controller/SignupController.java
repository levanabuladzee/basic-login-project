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

public class SignupController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txt_user;
    @FXML
    private PasswordField txt_psw;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_gender;
    @FXML
    private TextField txt_age;
    @FXML
    private Button btn_sign_up;
    @FXML
    private Button btn_back;

    Connection con = null;
    PreparedStatement insertStatement = null;

    // ღილაკზე დაჭერის მეთოდი
    public void handleButtonAction(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btn_sign_up){
            if (signUp().equals("Success")){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
                    AnchorPane pane = (AnchorPane) fxmlLoader.load();
                    rootPane.getChildren().setAll(pane);
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        } else if (actionEvent.getSource() == btn_back){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
            AnchorPane pane = (AnchorPane) fxmlLoader.load();
            rootPane.getChildren().setAll(pane);
        }
    }

    // მონაცემთა ბაზის კონსტრუქტორი
    public SignupController() throws Exception{
        con = DBConnection.connectDB();
    }

    // რეგისტრაციის მეთოდი
    private String signUp(){
        String status = "Success";
        String username = txt_user.getText();
        String password = txt_psw.getText();
        String email = txt_email.getText();
        String gender = txt_gender.getText();
        String age = txt_age.getText();

        // ამოწმებს არის თუ არა ცარიელი შესავსები ველი
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || gender.isEmpty() || age.isEmpty()){
            txt_user.setStyle("-fx-text-box-border: red;");
            txt_psw.setStyle("-fx-text-box-border: red;");
            txt_email.setStyle("-fx-text-box-border: red;");
            txt_gender.setStyle("-fx-text-box-border: red;");
            txt_age.setStyle("-fx-text-box-border: red;");
            status = "Error";
        } else {
            // შეყავს მომხმარებლის მონაცემები მონაცემთა ბაზაში
            try{
                String sqlInsert = "INSERT INTO USERS (USERNAME, PASSWORD, EMAIL, GENDER, AGE) VALUES (?, ?, ?, ?, ?)";
                insertStatement = con.prepareStatement(sqlInsert);
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                insertStatement.setString(4, gender);
                insertStatement.setString(5, age);
                insertStatement.execute();
            } catch (SQLException ex){
                ex.printStackTrace();
                status = "Exception";
            }
        }
        return status;
    }
}
