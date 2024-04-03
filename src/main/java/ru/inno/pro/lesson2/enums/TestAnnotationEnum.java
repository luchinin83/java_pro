package ru.inno.pro.lesson2.enums;

public enum TestAnnotationEnum {
  AfterSuite(ru.inno.pro.lesson2.annotations.AfterSuite.class),
  AfterTest(ru.inno.pro.lesson2.annotations.AfterTest.class),
  BeforeSuite(ru.inno.pro.lesson2.annotations.BeforeSuite.class),
  BeforeTest(ru.inno.pro.lesson2.annotations.BeforeTest.class),
  Test(ru.inno.pro.lesson2.annotations.Test.class);

  Class annotationClass;

  TestAnnotationEnum(Class annotationClass) {
    this.annotationClass = annotationClass;
  }

  public Class getAnnotationClass() {
    return annotationClass;
  }
}
