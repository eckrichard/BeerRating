package hu.bme.aut.android.hf.beerrating.data.database.query;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hu.bme.aut.android.hf.beerrating.data.DataFromDB;
import hu.bme.aut.android.hf.beerrating.data.database.dbHelper;

public class DBInsert extends DBQuery{
    public DBInsert(dbHelper dbHelper) {
        super(dbHelper);
    }

    public void insertReview(float rating, String opinion, String date, int beer_id, DataFromDB data){
        String sql = "INSERT INTO reviews (beer_id, rating, opinion, user_id, last_change) " +
                "VALUES (?, ?, ?, ?, ?)";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, beer_id);
                statement.setFloat(2, rating);
                statement.setString(3, opinion);
                statement.setInt(4, data.getUser().getId());
                statement.setDate(5, Date.valueOf(date));
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

    public void insertBeer(String name, int category_id, float alc, int brewery_id, String details){
        String sql = "INSERT INTO beers (name, category_id, alc, brewery_id, details) " +
                "VALUES (?, ?, ?, ?, ?)";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setInt(2, category_id);
                statement.setFloat(3, alc);
                statement.setInt(4, brewery_id);
                statement.setString(5, details);
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

    public void insertCategory(String name){
        String sql = "INSERT INTO category (name) " +
                "VALUES (?)";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
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

    public void insertBrewery(String name, String country, int postcode, String city, String address){
        String sql = "INSERT INTO brewerys (name, country, postcode, city, address) " +
                "VALUES (?, ?, ?, ?, ?)";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, country);
                statement.setInt(3, postcode);
                statement.setString(4, city);
                statement.setString(5, address);
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

    public void insertUser(String email, String username, String password, String dob, String forename, String surename, int sexId){
        String sql = "INSERT INTO users (email, username, password, dob, forename, surename, sex_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setDate(4, Date.valueOf(dob));
                statement.setString(5, forename);
                statement.setString(6, surename);
                statement.setInt(7, sexId);
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
