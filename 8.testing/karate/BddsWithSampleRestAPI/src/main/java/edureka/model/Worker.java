package edureka.model;

public class Worker {
    private int age;
    private String name;
    private double salary;
    private String position;

    @Override
    public String toString() {
        return "Worker{" + "name='" + name + '\'' + ", age=" + age + ", salary=" + salary + ", position='" + position + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Worker(int age, String name, double salary, String position) {
        this.age = age;
        this.name = name;
        this.salary = salary;
        this.position = position;
    }
}
