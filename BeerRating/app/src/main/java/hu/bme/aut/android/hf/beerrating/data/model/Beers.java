package hu.bme.aut.android.hf.beerrating.data.model;


import java.util.ArrayList;

public class Beers {

  private int id;
  private String name;
  private int categoryId;
  private float alc;
  private int breweryId;
  private String details;
  private float globalRating;
  private int ratingCount;
  private ArrayList<Float> ratings;

  private Category category;
  private Brewerys brewery;

  public Beers() {
    globalRating = 0f;
    ratingCount = 0;
    ratings = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }


  public float getAlc() {
    return alc;
  }

  public void setAlc(float alc) {
    this.alc = alc;
  }


  public int getBreweryId() {
    return breweryId;
  }

  public void setBreweryId(int breweryId) {
    this.breweryId = breweryId;
  }


  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Brewerys getBrewery() {
    return brewery;
  }

  public void setBrewery(Brewerys brewery) {
    this.brewery = brewery;
  }

  public float getGlobalRating() {
    globalRating = 0;
    ratingCount = ratings.size();
    for (float r : ratings) {
      globalRating += r;
    }
    return globalRating / ratingCount;
  }

  public void addRating(float rating){
    this.ratings.add(rating);
  }

  public void removeRating(float rating){
    this.ratings.remove(rating);
  }

  public ArrayList<Float> getRatings() {
    return ratings;
  }

  public void setRatingCount(int ratingCount) {
    this.ratingCount = ratingCount;
  }
}
