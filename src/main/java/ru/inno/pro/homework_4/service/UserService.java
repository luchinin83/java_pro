package ru.inno.pro.homework_4.service;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.inno.pro.homework_4.dto.User;
import ru.inno.pro.homework_4.repository.UserDao;

@Service
public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public void createUser(Long id, String name) throws SQLException {
    userDao.create(id, name);
  }

  public void updateUserById(Long id, String newName) throws SQLException {
    userDao.updateById(id, newName);
  }

  public void deleteUserById(Long id) throws SQLException {
    userDao.deleteById(id);
  }

  public User getUserById(Long id) {
    try {
      return userDao.findById(id);
    } catch (Exception e) {
      throw new RuntimeException("User not found by id = " + id);
    }
  }

  public List<User> getAllUsers() throws SQLException {
    return userDao.findAll();
  }
}
