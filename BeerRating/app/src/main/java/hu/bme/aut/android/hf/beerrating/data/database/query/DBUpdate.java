package hu.bme.aut.android.hf.beerrating.data.database.query;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hu.bme.aut.android.hf.beerrating.data.DataFromDB;
import hu.bme.aut.android.hf.beerrating.data.database.dbHelper;
import hu.bme.aut.android.hf.beerrating.data.model.Reviews;
import hu.bme.aut.android.hf.beerrating.data.model.Sex;
import hu.bme.aut.android.hf.beerrating.data.model.Users;

public class DBUpdate extends DBQuery {

    public DBUpdate(dbHelper dbHelper) {
        super(dbHelper);
    }

    public void updateUser(String email, String forename, String surename, String dob, int sexId, DataFromDB data) {
        String sql = "UPDATE users " +
                "SET email = ?, forename = ?, surename = ?, dob = ?, sex_id = ? " +
                "WHERE id = ?";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, forename);
                statement.setString(3, surename);
                statement.setDate(4, Date.valueOf(dob));
                statement.setInt(5, sexId);
                statement.setInt(6, data.getUser().getId());
                statement.executeUpdate();

                data.getUser().setEmail(email);
                data.getUser().setForename(forename);
                data.getUser().setSurename(surename);
                data.getUser().setDob(dob);
                data.getUser().setSexId(sexId);
                data.getUser().setSex(data.getSexs().get(sexId - 1));
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

    public void updatePass(String password, DataFromDB data){
        String sql = "UPDATE users " +
                "SET password = ? " +
                "WHERE id = ?";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, password);
                statement.setInt(2, data.getUser().getId());
                statement.executeUpdate();

                data.getUser().setPassword(password);
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

    public void updateReview(float rating, String opinion, String date, DataFromDB data){
        String sql = "UPDATE reviews " +
                "SET rating = ?, opinion = ?, last_change = ? " +
                "WHERE id = ?";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setFloat(1, rating);
                statement.setString(2, opinion);
                statement.setDate(3, Date.valueOf(date));
                statement.setInt(4, data.getReview().getId());
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
