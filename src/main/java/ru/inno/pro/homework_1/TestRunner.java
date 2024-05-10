package ru.inno.pro.homework_1;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.inno.pro.homework_1.annotations.AfterSuite;
import ru.inno.pro.homework_1.annotations.AfterTest;
import ru.inno.pro.homework_1.annotations.BeforeSuite;
import ru.inno.pro.homework_1.annotations.BeforeTest;
import ru.inno.pro.homework_1.annotations.Test;

public class TestRunner {

  static void runTests(Class c) {
    // Получим все методы, а также создадим списки для хранения по каждой аннотации
    Method[] declaredMethods = c.getDeclaredMethods();
    List<Method> beforeSuiteMethods = new ArrayList<>();
    List<Method> beforeTestMethods = new ArrayList<>();
    List<Method> afterSuiteMethods = new ArrayList<>();
    List<Method> afterTestMethods = new ArrayList<>();
    List<Method> testMethods = new ArrayList<>();

    // Пройдемся в цикле по всем методам и сохраним их в отдельные списки по аннотациям
    for (Method declaredMethod : declaredMethods) {
      if (declaredMethod.isAnnotationPresent(Test.class)) {
        testMethods.add(declaredMethod);
      } else if (declaredMethod.isAnnotationPresent(AfterSuite.class)) {
        afterSuiteMethods.add(declaredMethod);
      } else if (declaredMethod.isAnnotationPresent(AfterTest.class)) {
        afterTestMethods.add(declaredMethod);
      } else if (declaredMethod.isAnnotationPresent(BeforeSuite.class)) {
        beforeSuiteMethods.add(declaredMethod);
      } else if (declaredMethod.isAnnotationPresent(BeforeTest.class)) {
        beforeTestMethods.add(declaredMethod);
      }
    }

    // Обработка BeforeSuite если соответствующий список не пуст
    try {
      if (!beforeSuiteMethods.isEmpty()) {
        if (beforeSuiteMethods.size() > 1) {
          throw new Exception("Ожидалось не более одного метода с аннотацией BeforeSuite");
        } else {
          Method beforeSuiteMethod = beforeSuiteMethods.get(0);
          if (Modifier.isStatic(beforeSuiteMethod.getModifiers())) {
            beforeSuiteMethod.invoke(null);
          } else {
            throw new Exception("Метод BeforeSuite должен быть статическим");
          }
        }
      }

      // Отсортируем тестовые методы по приоритету в порядке убывания
      testMethods = testMethods.stream().sorted(
          (m1, m2) -> Integer.compare(m2.getAnnotation(Test.class).priority(),
              m1.getAnnotation(Test.class).priority())).collect(Collectors.toList());

      // Если есть хоть один тестовый метод, то начинаем обработку
      if (!testMethods.isEmpty()) {
        // Перебираем в цикле все тестовые методы
        for (Method testMethod : testMethods) {

          // Обработка BeforeTest перед выполнением теста
          if (!beforeTestMethods.isEmpty()) {
            for (Method beforeTestMethod : beforeTestMethods) {
              if (!Modifier.isStatic(beforeTestMethod.getModifiers())) {
                beforeTestMethod.invoke(c.getConstructor().newInstance());
              } else {
                throw new Exception("Метод BeforeTest не должен быть статическим");
              }
            }
          }

          // Обработка тестового метода, полученного из цикла
          int testPriority = testMethod.getAnnotation(Test.class).priority();
          if (testPriority < 1 || testPriority > 10) {
            throw new Exception("Приоритет должен быть от 1 до 10");
          }
          if (!Modifier.isStatic(testMethod.getModifiers())) {
            testMethod.invoke(c.getConstructor().newInstance());
          } else {
            throw new Exception("Метод Test не должен быть статическим");
          }

          // Обработка AfterTest после выполнения теста
          if (!afterTestMethods.isEmpty()) {
            for (Method afterTestMethod : afterTestMethods) {
              if (!Modifier.isStatic(afterTestMethod.getModifiers())) {
                afterTestMethod.invoke(c.getConstructor().newInstance());
                System.out.println("*****************************************************");
              } else {
                throw new Exception("Метод AfterTest не должен быть статическим");
              }
            }
          }
        }
      }

      if (!afterSuiteMethods.isEmpty()) {
        if (afterSuiteMethods.size() > 1) {
          throw new Exception("Ожидалось не более одного метода с аннотацией AfterSuite");
        } else {
          Method afterSuiteMethod = afterSuiteMethods.get(0);
          if (Modifier.isStatic(afterSuiteMethod.getModifiers())) {
            afterSuiteMethod.invoke(null);
          } else {
            throw new Exception("Метод AfterSuite должен быть статическим");
          }
        }
      }

    } catch (Exception e) {
      System.out.println("Шеф, усе пропало ...");
      System.out.println(e.getMessage());
    }
  }
}
