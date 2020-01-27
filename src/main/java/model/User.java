package model;

public class User {
    private String name;
    private int age;
    private String address;
    private String date;

    public User(String name, int age, String address, String date) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }
}
