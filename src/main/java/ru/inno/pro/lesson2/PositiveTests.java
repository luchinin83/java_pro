package ru.inno.pro.lesson2;

import ru.inno.pro.lesson2.annotations.AfterSuite;
import ru.inno.pro.lesson2.annotations.AfterTest;
import ru.inno.pro.lesson2.annotations.BeforeSuite;
import ru.inno.pro.lesson2.annotations.BeforeTest;
import ru.inno.pro.lesson2.annotations.Test;

public class PositiveTests {

  @BeforeSuite
  static void beforeSuiteMethod() {
    System.out.println("-----------------------------------------------------");
    System.out.println("Execute BeforeSuite method");
    System.out.println("-----------------------------------------------------");
  }

  @AfterSuite
  static void afterSuiteMethod() {
    System.out.println("-----------------------------------------------------");
    System.out.println("Execute AfterSuite method");
    System.out.println("-----------------------------------------------------");
  }

  @BeforeTest
  void beforeTestMethod() {
    System.out.println("Execute BeforeTest method");
  }

  @AfterTest
  void afterTestMethod() {
    System.out.println("Execute AfterTest method");
    System.out.println("-----------------------------------------------------");
  }

  @Test
  void test1_defaultPriority() {
    System.out.println("Execute test 1. Priority = default (5)");
  }

  @Test(priority = 10)
  void test2_priority_10() {
    System.out.println("Execute test 2. Priority = 10");
  }

  @Test(priority = 3)
  void test3_priority_3() {
    System.out.println("Execute test 3. Priority = 3");
  }

  @Test(priority = 7)
  void test4_priority_7() {
    System.out.println("Execute test 4. Priority = 7");
  }

  @Test(priority = 8)
  void test5_priority_8() {
    System.out.println("Execute test 5. Priority = 8");
  }

  @Test
  void test6_defaultPriority() {
    System.out.println("Execute test 6. Priority = default (5)");
  }

  @Test(priority = 9)
  void test7_defaultPriority() {
    System.out.println("Execute test 7. Priority = 9");
  }

}
