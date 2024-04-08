package ru.inno.pro.homework_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.inno.pro.homework_2.Employee.JobTitle;

public class StreamApiHomeWork {

  public static void main(String[] args) {

    // Реализуйте удаление из листа всех дубликатов
    List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4, 5, 6, 6, 7, 8, 9, 10);
    numbers = numbers.stream()
        .distinct()
        .collect(Collectors.toList());

    List<Integer> integerList = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);

    // Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
    int thirdMax = integerList.stream()
        .sorted(Collections.reverseOrder())
        .skip(2)
        .findFirst().get();

    // Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9)
    int thirdUniqueMax = integerList.stream()
        .distinct()
        .sorted(Collections.reverseOrder())
        .skip(2)
        .findFirst().get();

    // ==============================================================================================
    List<Employee> employees = Arrays.asList(
        new Employee("Иванов Иван Иванович", 35, JobTitle.Инженер),
        new Employee("Петров Иван Александрович", 21, JobTitle.Инженер),
        new Employee("Сидоров Иван Петрович", 52, JobTitle.Инженер),
        new Employee("Караваев Денис Викторович", 36, JobTitle.Бухгалтер),
        new Employee("Карасева Евгения Валерьевна", 29, JobTitle.Менеджер),
        new Employee("Михайлов Иван Михайлович", 56, JobTitle.Инженер),
        new Employee("Кривцов Иван Алексеевич", 27, JobTitle.Инженер)
    );

    //  Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список
    //  имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
    List<Employee> threeOldEmployees = employees.stream()
        .filter(e -> e.jobTitle == JobTitle.Инженер)
        .sorted((e1, e2) -> Integer.compare(e2.getAge(), e1.getAge()))
        .limit(3)
        .collect(Collectors.toList());

    //   Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст
    //   сотрудников с должностью «Инженер»
    double engineerAverageAge = employees.stream()
        .filter(e -> e.jobTitle == JobTitle.Инженер)
        .mapToInt(Employee::getAge)
        .average()
        .getAsDouble();

    // ==============================================================================================
    List<String> words = Arrays.asList("корова", "вор", "кабриолет", "вьюга", "постель");

    // Найдите в списке слов самое длинное
    String maxLengthWord = words.stream()
        .sorted(Comparator.comparing(String::length).reversed())
        .findFirst().get();

    // Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы,
    // в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
    String str = "видно небо посерело и спустилась мгла на мир и не видно ни зги";
    Map<String, Long> wordMap = Arrays.stream(str.split(" "))
        .collect(Collectors.groupingBy(String::valueOf, Collectors.counting()));

    // Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
    // если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
    List<String> strList = Arrays.asList("мгла", "враг", "вселенная", "на", "привет", "отпуск",
        "работа", "карта", "приключения");
    strList.stream()
        .sorted(Comparator.comparing(String::length).thenComparing(String::compareTo))
        .forEach(System.out::println);

    //Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом,
    // найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
    String[] wordArray = new String[]{
        "инициатива инициирует инициатора это точно",
        "программист часто кодит по ночам",
        "телефон разрядился и я отключился"
    };
    String maxLengthWordInArray =
        Arrays.stream(wordArray)
            .map(it -> it.split(" "))
            .flatMap(Arrays::stream).sorted(Comparator.comparing(String::length).reversed())
            .findFirst().get();
  }

}
