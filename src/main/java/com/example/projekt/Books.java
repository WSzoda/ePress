package com.example.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Books {
    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public Books() throws FileNotFoundException {
        this.books = loadBookFromFile();
    }

    private List<Book> loadBookFromFile() throws FileNotFoundException {
        List<Book> books = new ArrayList<Book>();
        File booksFile = new File("src/main/resources/database/books.txt");
        Scanner scanner = new Scanner(booksFile);
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String booksString = scanner.nextLine();
            String[] bookElements = booksString.split(";");
            books.add(new Book(Integer.parseInt(bookElements[0]), Integer.parseInt(bookElements[1]), bookElements[2], bookElements[3], bookElements[4], bookElements[5]));
        }
        scanner.close();
        return books;
    }

    private int getIndexOfBook(int id) {
        //TOOD logick
        return 1;
    }

    private int getNextID() {
        //TOOD logick
        int id = 0;
        return id;
    }

    private void deleteBook(int id) {
    }

    private void addBook(Book book) {
        // book comes from ui
    }

    public static class Book {
        private int id;
        private int authorsID;
        private String title;
        private String genre;
        private String type; // book or magazine
        private String frequency; // one time/weekly/monthly

        public Book(int id, int authorsID, String title, String genre, String type, String frequency) {
            this.id = id;
            this.authorsID = authorsID;
            this.title = title;
            this.genre = genre;
            this.type = type;
            this.frequency = frequency;
        }

        public int getId() {
            return id;
        }

        public int getAuthorsID() {
            return authorsID;
        }

        public String prepareToSaveToFile() {
            return String.format("%d;%d;%s;%s;%s;%s", id, authorsID, title, genre, type, frequency);
        }

        public String getRequiredQualityOfPrint() {
            if (type.equals("album")) {
                return "high";
            }
            return "low";
        }

        @Override
        public String toString() {
            return id + " " + title + " " + frequency;
        }
    }
}
