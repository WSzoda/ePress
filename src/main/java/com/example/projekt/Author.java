package com.example.projekt;

public class Author {
    private int id;
    private String name;
    private String surname;
    private String birthdate;

    public Author(int id, String name, String surname, String birthdate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public String prepareToSaveToFile() {
        return String.format("%d;%s;%s;%s", id, name, surname, birthdate);
    }

    @Override
    public String toString() {
        return id + " " + name + " " + surname;
    }

}
