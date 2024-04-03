package ru.inno.pro.lesson2;

import static ru.inno.pro.lesson2.TestRunner.runTests;

public class Main {

  public static void main(String[] args){
    System.out.println("Выполняем позитивные тесты");

    runTests(PositiveTests.class);

    System.out.println("\n\n\nВыполняем негативные тесты");
    System.out.println("-----------------------------------------------------");

    runTests(NegativeTests.class);
  }

}
