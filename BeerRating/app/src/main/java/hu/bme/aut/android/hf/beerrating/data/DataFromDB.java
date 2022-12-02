package hu.bme.aut.android.hf.beerrating.data;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.android.hf.beerrating.data.model.Beers;
import hu.bme.aut.android.hf.beerrating.data.model.Brewerys;
import hu.bme.aut.android.hf.beerrating.data.model.Category;
import hu.bme.aut.android.hf.beerrating.data.model.Reviews;
import hu.bme.aut.android.hf.beerrating.data.model.Sex;
import hu.bme.aut.android.hf.beerrating.data.model.Users;

public class DataFromDB {
    private Users user;
    private List<Category> categorys;
    private List<Brewerys> brewerys;
    private List<Beers> beers;
    private List<Reviews> reviews;
    private List<Sex> sexs;
    private Reviews review;

    public DataFromDB() {
        this.categorys = new ArrayList<>();
        this.brewerys = new ArrayList<>();
        this.beers = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.sexs = new ArrayList<>();
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Category> categorys) {
        this.categorys = categorys;
    }

    public List<Brewerys> getBrewerys() {
        return brewerys;
    }

    public void setBrewerys(List<Brewerys> brewerys) {
        this.brewerys = brewerys;
    }

    public List<Beers> getBeers() {
        return beers;
    }

    public void setBeers(List<Beers> beers) {
        this.beers = beers;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Reviews getReview() {
        return review;
    }

    public void setReview(Reviews review) {
        this.review = review;
    }

    public List<Sex> getSexs() {
        return sexs;
    }

    public void setSexs(List<Sex> sexs) {
        this.sexs = sexs;
    }

    public void clear(){
        categorys.clear();
        reviews.clear();
        brewerys.clear();
        beers.clear();
        sexs.clear();
    }
}
