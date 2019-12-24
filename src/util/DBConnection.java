package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
    Connection connection = null;

    // მონაცემთა ბაზასთან დაკავშირება
    public static Connection connectDB() {
        try{
            Class.forName("org.h2.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./database/myDB", "root", "123");
            return con;
        } catch (Exception ex){
            System.out.println("Connection: " + ex.getMessage());
            return null;
        }
    }
}
