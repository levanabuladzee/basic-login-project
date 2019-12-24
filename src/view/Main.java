package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 800, 550));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:h2:" + "./database/myDB", "root", "123");
        System.out.println("Database Created!");
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS USERS ("
                + "ID INTEGER NOT NULL AUTO_INCREMENT,"
                + "USERNAME VARCHAR(45) NOT NULL,"
                + "PASSWORD VARCHAR(45) NOT NULL,"
                + "EMAIL VARCHAR(45) NOT NULL,"
                + "GENDER VARCHAR(45) NOT NULL,"
                + "AGE INTEGER NOT NULL,"
                + "UNIQUE (USERNAME),"
                + "UNIQUE (EMAIL),"
                + "PRIMARY KEY (ID))";
        statement.executeUpdate(sql);
        System.out.println("Table Created!");
        System.out.println("Welcome to the application!");

        launch(args);
    }
}
