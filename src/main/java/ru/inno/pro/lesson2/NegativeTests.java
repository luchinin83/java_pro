package ru.inno.pro.lesson2;

import ru.inno.pro.lesson2.annotations.AfterSuite;
import ru.inno.pro.lesson2.annotations.AfterTest;
import ru.inno.pro.lesson2.annotations.BeforeSuite;
import ru.inno.pro.lesson2.annotations.BeforeTest;
import ru.inno.pro.lesson2.annotations.Test;

public class NegativeTests {

  @BeforeSuite
  static void beforeSuiteMethod() {
  }

  @BeforeSuite
  void nonStaticBeforeSuiteMethod() {
  }

  @AfterSuite
  static void afterSuiteMethod() {
  }

  @AfterSuite
  void nonStaticAfterSuiteMethod() {
  }

  @BeforeTest
  void beforeTest(){}

  @BeforeTest
  static void staticBeforeTest(){}

  @AfterTest
  void afterTest(){}

  @AfterTest
  static void staticAfterTest(){}

  @Test
  void test1_defaultPriority() {
  }

  @Test(priority = 10)
  void test2_priority_10() {
  }

  @Test(priority = 3)
  static void static_test3_priority_3() {
  }

  @Test(priority = 7)
  void test4_priority_7() {
  }

  @Test(priority = 8)
  static void static_test5_priority_8() {
  }

  @Test
  void test6_defaultPriority() {
  }

  @Test
  void test7_defaultPriority() {
  }

  @Test(priority = 25)
  void test8_invalidPriority() {
  }

  @Test(priority = 0)
  void test9_invalidPriority() {
  }

}
