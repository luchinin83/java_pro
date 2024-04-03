package ru.inno.pro.lesson2;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.inno.pro.lesson2.annotations.Test;
import ru.inno.pro.lesson2.enums.TestAnnotationEnum;

public class TestRunner {

  /**
   * Универсальный метод для запуска тестов
   *
   * @param c - Класс, в котором лежат тесты для запуска
   */
  static void runTests(Class c) {
    int beforeSuiteSize; // количество аннотаций @BeforeSuite
    int afterSuiteSize; // количество аннотаций @AfterSuite
    int testSize; // количество аннотаций @Test
    int afterTestSize; // количество аннотаций @AfterTest
    int beforeTestSize; // количество аннотаций @BeforeTest
    List<Method> beforeSuiteMethods;
    List<Method> afterSuiteMethods;
    List<Method> testMethods = null;
    List<Method> afterTestMethods = null;
    List<Method> beforeTestMethods = null;
    Method beforeTestMethod = null;
    Method afterTestMethod = null;

    try {
      Method[] declaredMethods = c.getDeclaredMethods();
      Map<TestAnnotationEnum, List<Method>> annoMethodsMap = new HashMap<>(); // Map для связки аннотации и списка методов с ней
      List<String> errorsList = new ArrayList<>(); // массив для записи всех найденных ошибок

      // Заполним Map связки имени аннотации и методов с ней
      for (Method declaredMethod : declaredMethods) {
        Arrays.stream(TestAnnotationEnum.values())
            .forEach(val -> fillAnnoMethodsMap(declaredMethod, val, annoMethodsMap));
      }

      // получили количество методов с аннотацией AfterSuite
      afterSuiteSize = annoMethodsMap.get(TestAnnotationEnum.AfterSuite) == null ? 0
          : annoMethodsMap.get(TestAnnotationEnum.AfterSuite).size();
      // Если список аннотаций по AfterSuite не пуст, то проходим по нему
      if (afterSuiteSize > 0) {
        afterSuiteMethods = annoMethodsMap.get(TestAnnotationEnum.AfterSuite);

        if (afterSuiteSize > 1) {
          String error = "Ожидалось не более одного метода с аннотацией AfterSuite.\n"
              + "Методы с аннотацией AfterSuite: "
              + afterSuiteMethods.stream().map(Method::getName).toList()
              + "\n--------------------------------------------------------";
          errorsList.add(error);
        }

        // Получим нестатические методы с аннотацией AfterSuite
        List<String> nonStaticAfterSuiteMethods = getOnlyNonStaticMethods(afterSuiteMethods);
        if (!nonStaticAfterSuiteMethods.isEmpty()) {
          String error = "Методы с аннотацией AfterSuite должны быть статическими.\n"
              + "Нестатические методы с аннотацией AfterSuite: "
              + nonStaticAfterSuiteMethods
              + "\n--------------------------------------------------------";
          errorsList.add(error);
        }
      }

      // получаем количество методов с аннотацией BeforeSuite
      beforeSuiteSize = annoMethodsMap.get(TestAnnotationEnum.BeforeSuite) == null ? 0
          : annoMethodsMap.get(TestAnnotationEnum.BeforeSuite).size();
      // Если список аннотаций по BeforeSuite не пуст, то проходим по нему
      if (beforeSuiteSize > 0) {
        beforeSuiteMethods = annoMethodsMap.get(TestAnnotationEnum.BeforeSuite);

        if (beforeSuiteSize > 1) {
          String error = "Ожидалось не более одного метода с аннотацией BeforeSuite.\n"
              + "Методы с аннотацией BeforeSuite: "
              + beforeSuiteMethods.stream().map(Method::getName).toList()
              + "\n--------------------------------------------------------";
          errorsList.add(error);
        }

        // Получим нестатические методы с аннотацией BeforeSuite
        List<String> nonStaticBeforeSuiteMethods = getOnlyNonStaticMethods(beforeSuiteMethods);
        if (!nonStaticBeforeSuiteMethods.isEmpty()) {
          String error = "Методы с аннотацией BeforeSuite должны быть статическими.\n"
              + "Нестатические методы с аннотацией BeforeSuite: "
              + nonStaticBeforeSuiteMethods
              + "\n--------------------------------------------------------";
          errorsList.add(error);
        }
      }

      // получим количество методов с аннотацией Test
      testSize = annoMethodsMap.get(TestAnnotationEnum.Test) == null ? 0
          : annoMethodsMap.get(TestAnnotationEnum.Test).size();
      if (testSize > 0) {
        testMethods = annoMethodsMap.get(TestAnnotationEnum.Test);
        // Получим статические методы с аннотацией @Test
        List<String> testStaticMethods = getOnlyStaticMethods(testMethods);
        if (!testStaticMethods.isEmpty()) {
          String error = "Методы с аннотацией Test не должны быть статическими.\n"
              + "Статические методы с аннотацией Test: "
              + testStaticMethods
              + "\n--------------------------------------------------------";
          errorsList.add(error);
        }

        List<String> invalidPriorityMethods = testMethods.stream().filter(val -> {
          Test anno = val.getAnnotation(Test.class);
          int priority = anno.priority();
          boolean isPriorityIncorrect = false;
          if (priority < 1 || priority > 10) {
            isPriorityIncorrect = true;
          }
          return isPriorityIncorrect;
        }).map(Method::getName).toList();

        if (!invalidPriorityMethods.isEmpty()) {
          String error = "Методы с аннотацией Test должны иметь приоритет от 1 до 10.\n"
              + "Методы с некорректным приоритетом: "
              + invalidPriorityMethods
              + "\n--------------------------------------------------------";
          errorsList.add(error);
        }

        // получим количество методов с аннотацией @BeforeTest
        beforeTestSize = annoMethodsMap.get(TestAnnotationEnum.BeforeTest) == null ? 0
            : annoMethodsMap.get(TestAnnotationEnum.BeforeTest).size();

        if (beforeTestSize > 0) {
          beforeTestMethods = annoMethodsMap.get(TestAnnotationEnum.BeforeTest);
          // Получим статические методы с аннотацией @BeforeTest
          List<String> beforeTestStaticMethods = getOnlyStaticMethods(beforeTestMethods);
          if (!beforeTestStaticMethods.isEmpty()) {
            String error = "Методы с аннотацией BeforeTest не должны быть статическими.\n"
                + "Статические методы с аннотацией BeforeTest: "
                + beforeTestStaticMethods
                + "\n--------------------------------------------------------";
            errorsList.add(error);
          }

          if (beforeTestSize > 1) {
            String error = "Ожидалось не более одного метода с аннотацией BeforeTest.\n"
                + "Методы с аннотацией BeforeTest: "
                + beforeTestMethods.stream().map(Method::getName).toList()
                + "\n--------------------------------------------------------";
            errorsList.add(error);
          }

          if (beforeTestSize == 1) {
            beforeTestMethod = annoMethodsMap.get(TestAnnotationEnum.BeforeTest).stream()
                .findFirst()
                .get();
          }
        }

        // получим количество методов с аннотацией @AfterTest
        afterTestSize = annoMethodsMap.get(TestAnnotationEnum.AfterTest) == null ? 0
            : annoMethodsMap.get(TestAnnotationEnum.AfterTest).size();
        if (afterTestSize > 0) {
          afterTestMethods = annoMethodsMap.get(TestAnnotationEnum.AfterTest);
          // Получим статические методы с аннотацией @AfterTest
          List<String> afterTestStaticMethods = getOnlyStaticMethods(afterTestMethods);
          if (!afterTestStaticMethods.isEmpty()) {
            String error = "Методы с аннотацией AfterTest не должны быть статическими.\n"
                + "Статические методы с аннотацией AfterTest: "
                + afterTestStaticMethods
                + "\n--------------------------------------------------------";
            errorsList.add(error);
          }
          if (afterTestSize > 1) {
            String error = "Ожидалось не более одного метода с аннотацией AfterTest.\n"
                + "Методы с аннотацией AfterTest: "
                + afterTestMethods.stream().map(Method::getName).toList()
                + "\n--------------------------------------------------------";
            errorsList.add(error);
          }
          if (afterTestSize == 1) {
            afterTestMethod = annoMethodsMap.get(TestAnnotationEnum.AfterTest).stream().findFirst()
                .get();
          }
        }

        if (errorsList.isEmpty()) {
          // Сначала выполняем BeforeSuite
          if (beforeSuiteSize == 1) {
            Method beforeSuiteMethod = annoMethodsMap.get(TestAnnotationEnum.BeforeSuite).stream()
                .findFirst().get();
            beforeSuiteMethod.invoke(null);
          }

          // Выполняем методы Test, а также для каждого BeforeTest и AfterTest (при наличии)
          if (testSize > 0) {
            testMethods = testMethods.stream().sorted(
                (m1, m2) -> Integer.compare(m2.getDeclaredAnnotation(Test.class).priority(),
                    m1.getDeclaredAnnotation(Test.class).priority())).collect(Collectors.toList());
            for (Method testMethod : testMethods) {
              if (beforeTestSize == 1) {
                beforeTestMethod.invoke(c.getConstructor().newInstance());
              }
              testMethod.invoke(c.getConstructor().newInstance());
              if (afterTestSize == 1) {
                afterTestMethod.invoke(c.getConstructor().newInstance());
              }
            }
          }

          if (afterSuiteSize == 1) {
            Method afterSuiteMethod = annoMethodsMap.get(TestAnnotationEnum.AfterSuite).stream()
                .findFirst().get();
            afterSuiteMethod.invoke(null);
          }
        } else {
          System.out.println("Тестовый набор не будет выполнен. Найдены следующие ошибки: \n");
          errorsList.forEach(System.out::println);
        }
      }
    } catch (Exception e) {
      System.out.println("Шеф, усе пропало...");
      e.printStackTrace();
    }
  }

  /**
   * Метод служит для заполнения Map, содержащих связку аннотации и методов, где она установлена
   *
   * @param method                  - метод, отвечающий за выполнение теста
   * @param testAnnotationEnumValue - тестовая аннотация
   * @param annoMethodsMap          - Map, содержащая связку аннотации и методов, на которых она
   *                                установлена
   */
  private static void fillAnnoMethodsMap(Method method, TestAnnotationEnum testAnnotationEnumValue,
      Map<TestAnnotationEnum, List<Method>> annoMethodsMap) {
    if (method.isAnnotationPresent(testAnnotationEnumValue.getAnnotationClass())) {
      List<Method> list = new ArrayList<>();
      if (annoMethodsMap.get(testAnnotationEnumValue) != null) {
        list = annoMethodsMap.get(testAnnotationEnumValue);
      }
      list.add(method);
      annoMethodsMap.put(testAnnotationEnumValue, list);
    }
  }

  /**
   * Метод формирует список имен методов, которые являются статическими
   *
   * @param methods - список методов, которые будем проверять
   * @return список статических методов, содержащих аннотацию
   */
  private static List<String> getOnlyStaticMethods(List<Method> methods) {
    return methods.stream().filter(val -> Modifier.isStatic(val.getModifiers()))
        .map(Method::getName).collect(Collectors.toList());
  }

  /**
   * Метод формирует список имен методов, которые не являются статическими
   *
   * @param methods - список методов, которые будем проверять
   * @return список наименований нестатических методов, содержащих аннотацию
   */
  private static List<String> getOnlyNonStaticMethods(List<Method> methods) {
    return methods.stream().filter(val -> !Modifier.isStatic(val.getModifiers()))
        .map(Method::getName).collect(Collectors.toList());
  }

}
