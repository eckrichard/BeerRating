package hu.bme.aut.android.hf.beerrating.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbHelper {

    private String url = "jdbc:postgresql://ec2-99-81-16-126.eu-west-1.compute.amazonaws.com:" +
            "5432/" +
            "dekh8gssak4h18?" +
            "user=jrnubjniriokcu&" +
            "password=e2f6be7458319d7d66f7c266a1967cab77af3f723040609af9af71c69667e067";

    private Connection connection = null;

    public void connect(){
        Thread dbThread = new Thread(() -> {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        dbThread.start();
        try
        {
            dbThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        Thread dbThread = new Thread(() -> {
            try {
                Class.forName("org.postgresql.Driver");
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        dbThread.start();
        try
        {
            dbThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
