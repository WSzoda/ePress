package com.example.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Authors {
    private final String FILE_NAME = "authors";
    List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public Authors() throws FileNotFoundException {
        this.authors = loadAuthorsFromFile();
    }

    private List<Author> loadAuthorsFromFile() throws FileNotFoundException {
        List<Author> authors = new ArrayList<Author>();
        ArrayList<String> authorsArray = FileOperator.getFileDataAsArray(FILE_NAME);
        for(int i = 0 ; i < authorsArray.size(); i++){
            String[] authorElements = authorsArray.get(i).split(";");
            authors.add(new Author(Integer.parseInt(authorElements[0]), authorElements[1], authorElements[2], authorElements[3]));
        }
        return authors;
    }

    private int getIndexOfAuthor(int id){
        for(int i = 0; i < authors.size(); i++){
            if(authors.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getNextId() throws IOException {
        return FileOperator.getNextId(FILE_NAME);
    }
    public void updateAuthors() throws FileNotFoundException {
        this.authors = loadAuthorsFromFile();
    }

    public void deleteAuthor(int id) throws IOException {
        int index = getIndexOfAuthor(id);
        if(index == -1){
            return;
        }
        FileOperator.deleteInstanceFromFile(id, FILE_NAME);
    }

    public void addAuthor (String name, String surname, String birthdate) throws IOException {
        int id = getNextId();
        Author newAuthor = new Author(id, name, surname, birthdate);
        FileOperator.addNewInstanceToFile(newAuthor.prepareToSaveToFile(), FILE_NAME);
        authors.add(newAuthor);
    }

    public static class Author {
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
        public String getName(){
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getBirthdate() {
            return birthdate;
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
}

