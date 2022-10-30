package hu.bme.aut.android.hf.beerrating.data.model;


import java.sql.Date;

public class Users {

  private int id;
  private String email;
  private String username;
  private String password;
  private java.sql.Date dob;
  private String forename;
  private String surename;
  private int sexId;

  private Sex sex;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public java.sql.Date getDob() {
    return dob;
  }

  public void setDob(java.sql.Date dob) {
    this.dob = dob;
  }


  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }


  public String getSurename() {
    return surename;
  }

  public void setSurename(String surename) {
    this.surename = surename;
  }


  public int getSexId() {
    return sexId;
  }

  public void setSexId(int sexId) {
    this.sexId = sexId;
  }

  public Sex getSex() {
    return sex;
  }

  public void setSex(Sex sex) {
    this.sex = sex;
  }

  public void setDob(String dob) {
    this.dob = Date.valueOf(dob);
  }
}
