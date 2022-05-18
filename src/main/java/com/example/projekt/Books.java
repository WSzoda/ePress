package com.example.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Books {
    private final String fileName = "books";
    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public Books() throws FileNotFoundException {
        this.books = loadBookFromFile();
    }

    private List<Book> loadBookFromFile() throws FileNotFoundException {
        List<Book> books = new ArrayList<>();
        ArrayList<String> booksArray = FileOperator.getFileDataAsArray(fileName);
        for(int i = 0 ; i < booksArray.size(); i++){
            String[] bookElements = booksArray.get(i).split(";");
            books.add(new Book(Integer.parseInt(bookElements[0]), Integer.parseInt(bookElements[1]), bookElements[2], bookElements[3], bookElements[4], bookElements[5]));
        }
        return books;
    }

    private int getIndexOfBook(int id) {
        for(int i = 0 ; i < books.size(); i++){
            int selectedBookId = books.get(i).id;
            if( selectedBookId == id ){
                return selectedBookId;
            }
        }
        return -1;
    }

    private int getNextID() throws IOException {
        return FileOperator.getNextId(fileName);
    }

    private void deleteBook(int id) throws IOException {
        int index = getIndexOfBook(id);
        if(index == -1){
            return;
        }
        FileOperator.deleteInstanceFromFile(id, fileName);
    }

    private void addBook(Book book) throws IOException {
        FileOperator.addNewInstanceToFile(book.prepareToSaveToFile(), fileName);
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
