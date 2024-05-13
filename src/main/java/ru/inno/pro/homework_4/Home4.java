package ru.inno.pro.homework_4;

import java.sql.SQLException;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.inno.pro.homework_4.dto.User;
import ru.inno.pro.homework_4.service.UserService;

@ComponentScan
public class Home4 {
  public static void main(String[] args) throws SQLException {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Home4.class);
    UserService userService = context.getBean(UserService.class);
    // Добавим двух пользователей
    userService.createUser(555L, "John Unknown");
    userService.createUser(777L, "John Wick");
    userService.createUser(888L, "Tony Stark");
    // Удаляем пользователя
    userService.deleteUserById(888L);
    // Проверка получения пользователей
    User user = userService.getUserById(555L);
    System.out.println("Получили одного пользователя:\n" + user);
    // Делаем Update
    userService.updateUserById(555L, "Bruce Wayne");
    // Получаем всех пользователей
    List<User> userList = userService.getAllUsers();
    System.out.println("*****************************");
    System.out.println("Получили всех пользователей:");
    userList.forEach(it -> System.out.println(it.toString()));
  }

}
