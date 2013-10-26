package com.example.xmlpullparserexample;

/**
 * Created by ebernie on 10/26/13.
 */
public class Person {

    private final String email;
    private final String id;
    private final String name;

    public Person(String email, String id, String name) {
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
