package com.example.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Authors {
    List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public Authors() throws FileNotFoundException {
        this.authors = loadAuthorsFromFile();
    }

    private List<Author> loadAuthorsFromFile() throws FileNotFoundException {
        List<Author> authors = new ArrayList<Author>();
        File authorsFile = new File("src/main/resources/database/authors.txt");
        Scanner scanner = new Scanner(authorsFile);
        scanner.nextLine();
        while(scanner.hasNextLine()){
            String authorString = scanner.nextLine();
            String[] authorElements = authorString.split(";");
            authors.add(new Author(Integer.parseInt(authorElements[0]), authorElements[1], authorElements[2], authorElements[3]));
        }
        scanner.close();
        return authors;
    }
}

class Author {
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

    public String prepareToSaveToFile(){
        return String.format("%d;%s;%s;%s", id, name, surname, birthdate);
    }

    @Override
    public String toString() {
        return name + " " +surname;
    }
}
