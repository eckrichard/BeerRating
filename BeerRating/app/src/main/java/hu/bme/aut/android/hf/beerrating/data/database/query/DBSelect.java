package hu.bme.aut.android.hf.beerrating.data.database.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hu.bme.aut.android.hf.beerrating.data.DataFromDB;
import hu.bme.aut.android.hf.beerrating.data.database.dbHelper;
import hu.bme.aut.android.hf.beerrating.data.model.Beers;
import hu.bme.aut.android.hf.beerrating.data.model.Brewerys;
import hu.bme.aut.android.hf.beerrating.data.model.Category;
import hu.bme.aut.android.hf.beerrating.data.model.Reviews;
import hu.bme.aut.android.hf.beerrating.data.model.Sex;
import hu.bme.aut.android.hf.beerrating.data.model.Users;

public class DBSelect extends DBQuery {

    public DBSelect(dbHelper dbHelper) {
        super(dbHelper);
    }

    public void checkLogin(String username, String password, DataFromDB data) {
        String sql = "SELECT * FROM users u JOIN sex s ON u.sex_id = s.id " +
                "WHERE username = ?";

        Thread dbThread = new Thread(() -> {
            this.getDbHelper().connect();
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    if (rs.getString("password").equals(password)){
                        Users user = new Users();
                        user.setId(rs.getInt(1));
                        user.setDob(rs.getDate(5));
                        user.setEmail(rs.getString(2));
                        user.setUsername(rs.getString(3));
                        user.setForename(rs.getString(6));
                        user.setSurename(rs.getString(7));
                        user.setSexId(rs.getInt(8));
                        user.setPassword(rs.getString(4));

                        Sex sex = new Sex();
                        sex.setId(rs.getInt(9));
                        sex.setName(rs.getString(10));

                        user.setSex(sex);
                        data.setUser(user);
                    }
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        dbThread.start();
        try
        {
            dbThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.getDbHelper().disconnect();
    }

    public void LoadData(DataFromDB data){
        data.clear();
        this.getDbHelper().connect();
        selectSex(data);
        selectBrewery(data);
        selectCategory(data);
        selectBeer(data);
        selectReview(data);
        this.getDbHelper().disconnect();
    }

    private void selectSex(DataFromDB data) {
        String sql = "SELECT * FROM sex";

        Thread dbThread = new Thread(() -> {
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    Sex sex = new Sex();
                    sex.setId(rs.getInt("id"));
                    sex.setName(rs.getString("name"));

                    data.getSexs().add(sex);
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

    private void selectBeer(DataFromDB data) {
        String sql = "SELECT * FROM beers";

        Thread dbThread = new Thread(() -> {

            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    Beers beer = new Beers();
                    beer.setId(rs.getInt("id"));
                    beer.setAlc(rs.getFloat("alc"));
                    beer.setBreweryId(rs.getInt("brewery_id"));
                    beer.setCategoryId(rs.getInt("category_id"));
                    beer.setDetails(rs.getString("details"));
                    beer.setName(rs.getString("name"));

                    for (int i = 0; i < data.getBrewerys().size(); i++) {
                        if(beer.getBreweryId() == data.getBrewerys().get(i).getId())
                            beer.setBrewery(data.getBrewerys().get(i));
                    }

                    for (int i = 0; i < data.getCategorys().size(); i++) {
                        if(beer.getCategoryId() == data.getCategorys().get(i).getId())
                            beer.setCategory(data.getCategorys().get(i));
                    }

                    data.getBeers().add(beer);
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

    private void selectBrewery(DataFromDB data) {
        String sql = "SELECT * FROM brewerys";

        Thread dbThread = new Thread(() -> {
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    Brewerys brewery = new Brewerys();
                    brewery.setId(rs.getInt("id"));
                    brewery.setPostcode(rs.getInt("postcode"));
                    brewery.setName(rs.getString("name"));
                    brewery.setAddress(rs.getString("address"));
                    brewery.setCity(rs.getString("city"));
                    brewery.setCountry(rs.getString("country"));

                    data.getBrewerys().add(brewery);
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

    private void selectCategory(DataFromDB data) {
        String sql = "SELECT * FROM category";

        Thread dbThread = new Thread(() -> {
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));

                    data.getCategorys().add(category);
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

    private void selectReview(DataFromDB data) {
        String sql = "SELECT * FROM reviews";

        Thread dbThread = new Thread(() -> {
            try {
                Connection connection = this.getDbHelper().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    Reviews review = new Reviews();
                    review.setId(rs.getInt("id"));
                    review.setBeerId(rs.getInt("beer_id"));
                    review.setUserId(rs.getInt("user_id"));
                    review.setRating(rs.getFloat("rating"));
                    review.setOpinion(rs.getString("opinion"));
                    review.setLastChange(rs.getDate("last_change"));

                    for (int i = 0; i < data.getBeers().size(); i++) {
                        if(review.getBeerId() == data.getBeers().get(i).getId()) {
                            review.setBeer(data.getBeers().get(i));
                            review.getBeer().addRating(review.getRating());
                        }
                    }

                    if (review.getUserId() == data.getUser().getId())
                        review.setUser(data.getUser());

                    data.getReviews().add(review);
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
}
