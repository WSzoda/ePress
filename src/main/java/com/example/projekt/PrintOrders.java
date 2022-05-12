package com.example.projekt;

import java.util.List;

public class PrintOrders {
    List<PrintOrder> printOrders;

    public void fullFillOrder(int id){}
    public List<PrintOrder> loadOrdersFromFile(){}
    private int getNextId(){}
    private void deleteOrder(){}
    private void addAuthor(){}



    public static class PrintOrder {
        private int id;
        private int bookId;
        private int amount;

        public PrintOrder(int id, int bookId, int amount) {
            this.id = id;
            this.bookId = bookId;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public int getBookId() {
            return bookId;
        }

        public int getAmount() {
            return amount;
        }

        public String prepareToSaveToFile(){
            return String.format("%d;%s;%s;%s", id, bookId, amount);
        }

        @Override
        public String toString() {
            return String.format("Order Id:%s | Book Id %s | Amount: %s", id, bookId, amount)
        }
    }
}
