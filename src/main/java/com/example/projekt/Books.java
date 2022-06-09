package com.example.projekt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
            books.add(new Book(Integer.parseInt(bookElements[0]), Integer.parseInt(bookElements[1]), bookElements[2], bookElements[3], bookElements[4], bookElements[5], bookElements[6]));
        }
        return books;
    }

    public int getIndexOfBook(int id) {
        for(int i = 0 ; i < books.size(); i++){
            int selectedBookId = books.get(i).id;
            if( selectedBookId == id ){
                return selectedBookId;
            }
        }
        return -1;
    }

    public void updateBooks() throws FileNotFoundException {
        books = loadBookFromFile();
    }


    public int getNextID() throws IOException {
        return FileOperator.getNextId(fileName);
    }

    public void deleteBook(int id) throws IOException {
        int index = getIndexOfBook(id);
        if(index == -1){
            return;
        }
        FileOperator.deleteInstanceFromFile(id, fileName);
    }

    public void addBook(Book book) throws IOException {
        FileOperator.addNewInstanceToFile(book.prepareToSaveToFile(), fileName);
    }

    public static class Book {
        private int id;
        private int authorsID;
        private String authorsNameSurname;
        private String title;
        private String genre;
        private String type; // book or magazine
        private String frequency; // one time/weekly/monthly
        private String coverImageName;
        private ImageView photo;

        public Book(int id, int authorsID, String title, String genre, String type, String frequency, String coverImageName) throws FileNotFoundException {
            this.id = id;
            this.authorsID = authorsID;
            this.title = title;
            this.genre = genre;
            this.type = type;
            this.frequency = frequency;
            this.coverImageName = coverImageName;
            System.out.println();
            photo = new ImageView(new Image(System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + coverImageName));
            Authors authors = new Authors();
            int authorIndex = authors.getIndexOfAuthor(authorsID);
            List<Authors.Author> authorsList = authors.getAuthors();
            authorsNameSurname = authorsList.get(authorIndex).getName() + " " + authorsList.get(authorIndex).getSurname();
        }

        public ImageView getPhoto() {
            return photo;
        }

        public String getAuthorsNameSurname() {
            return authorsNameSurname;
        }

        public String getTitle() {
            return title;
        }

        public String getGenre() {
            return genre;
        }

        public String getType() {
            return type;
        }

        public String getFrequency() {
            return frequency;
        }

        public String getCoverImageName() {
            return coverImageName;
        }

        public int getId() {
            return id;
        }

        public int getAuthorsID() {
            return authorsID;
        }

        public String prepareToSaveToFile() {
            return String.format("%d;%d;%s;%s;%s;%s;%s", id, authorsID, title, genre, type, frequency, coverImageName);
        }

        public boolean needHighQUalityPrinter() {
            if (type.equals("album")) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return id + " " + title + " " + frequency;
        }
    }
}
