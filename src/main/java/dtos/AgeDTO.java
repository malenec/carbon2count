package dtos;

public class AgeDTO {
    private int age;
    private String name;

    public AgeDTO(int age) {
        this.age = age;
    }

    public AgeDTO(int age, String name) {
        this.age = age;
        this.name = name;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AgeDTO{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}