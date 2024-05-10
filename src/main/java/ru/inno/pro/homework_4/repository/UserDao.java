package ru.inno.pro.homework_4.repository;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.inno.pro.homework_4.dto.User;

@Repository
public class UserDao {

  private final HikariDataSource hikariDataSource;

  public UserDao(HikariDataSource hikariDataSource) {
    this.hikariDataSource = hikariDataSource;
  }

  public void create(Long id, String name) throws SQLException {
    try (Statement statement = hikariDataSource.getConnection().createStatement()) {
      statement.execute("INSERT INTO users(id, username) VALUES(" + id + ", '" + name + "')");
    }
  }

  public void updateById(Long id, String newName) throws SQLException {
    try (Statement statement = hikariDataSource.getConnection().createStatement()) {
      statement.execute("UPDATE users SET username = '" + newName + "' WHERE id = " + id);
    }
  }

  public void deleteById(Long id) throws SQLException {
    try (Statement statement = hikariDataSource.getConnection().createStatement()) {
      statement.execute("DELETE FROM users WHERE id = " + id);
    }
  }

  public User findById(Long userId) throws SQLException {
    User user = new User();
    try (Statement statement = hikariDataSource.getConnection().createStatement()) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id = " + userId);
      while (resultSet.next()) {
        Long id = resultSet.getLong("id");
        String userName = resultSet.getString("username");
        user.setId(id);
        user.setUserName(userName);
      }
    }
    return user;
  }

  public List<User> findAll() throws SQLException {
    List<User> userList = new ArrayList<>();
    try (Statement statement = hikariDataSource.getConnection().createStatement()) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
      while (resultSet.next()) {
        User user = new User(resultSet.getLong("id"), resultSet.getString("username"));
        userList.add(user);
      }
    }
    return userList;
  }
}
