package ru.inno.pro.homework_4.dto;

public class User {
  Long id;
  String userName;

  public User(Long id, String userName) {
    this.id = id;
    this.userName = userName;
  }

  public User() {
  }

  public Long getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        '}';
  }
}
