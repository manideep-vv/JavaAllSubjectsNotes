package edureka;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Student {
    private String name;
    private int id;
    private String subject;

    double percentage;

    public Student(String name, int id, String subject, double percentage) {
        this.name = name;
        this.id = id;
        this.subject = subject;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return name + "-" + id + "-" + subject + "-" + percentage;
    }

    public static void main(String[] args) {
        Student student1 = new Student("Dinesh", 1, "Algorithms", 75);
        Student student2 = new Student("Arnav", 2, "Automata", 55);
        Student student3 = new Student("Anamika", 3, "Databases", 80);
        Student student4 = new Student("Vishal", 4, "Networking", 40);


        List<Student> list= Arrays.asList(student1,student2,student3,student4);
        Predicate<Student> p= s->s.getPercentage()>60;
        Map<Boolean, List<Student>> segregatedListWithPercentage60 = list.stream().collect(Collectors.partitioningBy(p));
        System.out.println(segregatedListWithPercentage60);

        Comparator<Student> c= (s1,s2)-> Double.compare(s2.getPercentage() ,s1.getPercentage());
        List<Student> sortedByPercentage = list.stream().sorted(c).collect(Collectors.toList()).subList(0,3);
        System.out.println(sortedByPercentage);

        Map<String, Double> nameAndPErcentageOfEachStudent = list.stream().collect(Collectors.toMap(Student::getName, Student::getPercentage));
        System.out.println(nameAndPErcentageOfEachStudent);

        List<String> subjectsOffered = list.stream().map(Student::getSubject).collect(Collectors.toList());
        System.out.println(subjectsOffered);

        OptionalDouble highestPercentage = list.stream().mapToDouble(Student::getPercentage).max();
        System.out.println("Highest Percentage: "+highestPercentage.getAsDouble());

        OptionalDouble lowestPercentage = list.stream().mapToDouble(Student::getPercentage).min();
        System.out.println("Lowest Percentage: "+lowestPercentage.getAsDouble());

        OptionalDouble averagePercentage = list.stream().mapToDouble(Student::getPercentage).average();
        System.out.println("Average Percentage: "+averagePercentage.getAsDouble());

        System.out.println(list.stream().count());

        Map<String, List<Student>> groupBySubject = list.stream().collect(Collectors.groupingBy(Student::getSubject));
        System.out.println(groupBySubject);

    }
}
