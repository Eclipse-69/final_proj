package proj.proj2;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static Connection databaseLink;


    public static Connection getConnection() {
        String databaseURL = "jdbc:sqlite:C:\\Users\\Warlord\\InfoSystem.db";

        try{
            if(databaseLink == null || databaseLink.isClosed()) {
                databaseLink = DriverManager.getConnection(databaseURL);
                System.out.println("Connected to database successfully.");

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;
    }
}
