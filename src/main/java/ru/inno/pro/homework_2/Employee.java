package ru.inno.pro.homework_2;

public class Employee {
  String name;
  int age;
  JobTitle jobTitle;

  public Employee(String name, int age, JobTitle jobTitle) {
    this.name = name;
    this.age = age;
    this.jobTitle = jobTitle;
  }

  enum JobTitle{
    Инженер, Менеджер, Бухгалтер;
  }

  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", jobTitle=" + jobTitle +
        '}';
  }
}
