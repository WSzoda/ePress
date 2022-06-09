package com.example.projekt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Authors {
    private final String FILE_NAME = "authors";
    List<Author> authors;

    public Authors() throws FileNotFoundException {
        this.authors = loadAuthorsFromFile();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    private List<Author> loadAuthorsFromFile() throws FileNotFoundException {
        List<Author> authors = new ArrayList<>();
        ArrayList<String> authorsArray = FileOperator.getFileDataAsArray(FILE_NAME);
        for (String s : authorsArray) {
            String[] authorElements = s.split(";");
            authors.add(new Author(Integer.parseInt(authorElements[0]), authorElements[1], authorElements[2], authorElements[3]));
        }
        return authors;
    }

    public int getIndexOfAuthor(int id) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void editAuthor(Author author) throws IOException {
        FileOperator.editInstanceInFile(author.getId(), author.prepareToSaveToFile(), FILE_NAME);
    }


    private int getNextId() throws IOException {
        return FileOperator.getNextId(FILE_NAME);
    }

    public void updateAuthors() throws FileNotFoundException {
        this.authors = loadAuthorsFromFile();
    }

    public void deleteAuthor(int id) throws IOException {
        int index = getIndexOfAuthor(id);
        if (index == -1) {
            return;
        }
        FileOperator.deleteInstanceFromFile(id, FILE_NAME);
    }

    public void addAuthor(String name, String surname, String birthdate) throws IOException {
        int id = getNextId();
        Author newAuthor = new Author(id, name, surname, birthdate);
        FileOperator.addNewInstanceToFile(newAuthor.prepareToSaveToFile(), FILE_NAME);
        authors.add(newAuthor);
    }

    public static class Author {
        private final int id;
        private final String name;
        private final String surname;
        private final String birthdate;

        public Author(int id, String name, String surname, String birthdate) throws FileNotFoundException {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.birthdate = birthdate;
        }

        public String getName() {
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

