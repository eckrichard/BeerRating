package hu.bme.aut.android.hf.beerrating.data.database.query;

import java.sql.*;

import hu.bme.aut.android.hf.beerrating.data.DataFromDB;
import hu.bme.aut.android.hf.beerrating.data.database.dbHelper;
import hu.bme.aut.android.hf.beerrating.data.model.Reviews;
import hu.bme.aut.android.hf.beerrating.data.model.Users;

public class DBDelete extends DBQuery{

    public DBDelete(dbHelper dbHelper) {
        super(dbHelper);
    }

    public void deleteReview(int id){
        String sql = "DELETE FROM reviews WHERE id = ?;";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            this.getDbHelper().disconnect();
        });
        dbThread.start();
        try
        {
            dbThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
