package hu.bme.aut.android.hf.beerrating.data.model;


import java.sql.Date;

public class Reviews {

  private int id;
  private int beerId;
  private float rating;
  private String opinion;
  private int userId;
  private Date lastChange;

  private Beers beer;
  private Users user;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getBeerId() {
    return beerId;
  }

  public void setBeerId(int beerId) {
    this.beerId = beerId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String getOpinion() {
    return opinion;
  }

  public void setOpinion(String opinion) {
    this.opinion = opinion;
  }

  public Date getLastChange() {
    return lastChange;
  }

  public void setLastChange(Date lastChange) {
    this.lastChange = lastChange;
  }

  public Beers getBeer() {
    return beer;
  }

  public void setBeer(Beers beer) {
    this.beer = beer;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }
}
