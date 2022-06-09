package com.example.projekt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final String FILE_NAME = "stocks";
    private List<MagazineRecord> stocks;

    public Warehouse() throws FileNotFoundException {
        stocks = loadStocksFromFile();
    }

    private List<MagazineRecord> loadStocksFromFile() throws FileNotFoundException {
        List<MagazineRecord> records = new ArrayList<>();
        ArrayList<String> recordsArray = FileOperator.getFileDataAsArray(FILE_NAME);
        for (int i = 0; i < recordsArray.size(); i++) {
            String[] magazineRecords = recordsArray.get(i).split(";");
            records.add(new MagazineRecord(Integer.parseInt(magazineRecords[0]), Integer.parseInt(magazineRecords[1]), Integer.parseInt(magazineRecords[2])));
        }
        return records;
    }

    public List<MagazineRecord> getStocks() {
        return stocks;
    }

    public void updateRecords() throws FileNotFoundException {
        stocks = loadStocksFromFile();
    }

    private int getNextId() throws IOException {
        return FileOperator.getNextId(FILE_NAME);
    }

    public void deleteRecordForBook(int bookId) throws IOException {
        int id = 0;
        for (int i = 0; i < stocks.size(); i++) {
            int selectedRecordID = stocks.get(i).getBookId();
            if (selectedRecordID == bookId) {
                id = stocks.get(i).getRecordId();
                break;
            }
        }
        FileOperator.deleteInstanceFromFile(id, FILE_NAME);
    }

    public void editRecord(MagazineRecord record) throws IOException {
        FileOperator.editInstanceInFile(record.getRecordId(), record.prepareToSaveToFile(), FILE_NAME);
    }

    public void addRecord(int bookId, int stock) throws IOException {
        int id = getNextId();
        MagazineRecord newRecord = new MagazineRecord(id, bookId, stock);
        FileOperator.addNewInstanceToFile(newRecord.prepareToSaveToFile(), FILE_NAME);
        stocks.add(newRecord);
    }


    public static class MagazineRecord {
        private final int recordId;
        private final int bookId;
        private final String bookName;
        private final int stock;

        public MagazineRecord(int id, int bookId, int stock) throws FileNotFoundException {
            Books books = new Books();
            this.bookId = bookId;
            recordId = id;
            List<Books.Book> booksList = books.getBooks();
            bookName = booksList.get(books.getIndexOfBook(bookId)).getTitle();
            this.stock = stock;
        }

        public int getRecordId() {
            return recordId;
        }

        public int getBookId() {
            return bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public int getStock() {
            return stock;
        }

        public String prepareToSaveToFile() {
            return String.format("%d;%d;%d", recordId, bookId, stock);
        }
    }
}
