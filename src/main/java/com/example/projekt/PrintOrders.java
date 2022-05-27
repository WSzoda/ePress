package com.example.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrintOrders {
    private final String FILE_NAME = "contracts";
    List<PrintOrder> printOrders;

    public void fullFillOrder(int id, Printers.Printer printer) throws IOException {
        printer.printBooks(findOrder(id));
    }
    private PrintOrder findOrder(int id) {
        for(int i =0; i < printOrders.size(); i++) {
            if(printOrders.get(i).getId() == id) {
                return printOrders.get(i);
            }
        }
        return null;
    }
    private List<PrintOrder> loadOrdersFromFile() throws FileNotFoundException {
        List<PrintOrder> orders = new ArrayList<>();
        ArrayList<String> ordersArray = FileOperator.getFileDataAsArray(FILE_NAME);
        for(int i = 0 ; i < ordersArray.size(); i++){
            String[] orderElements = ordersArray.get(i).split(";");
            orders.add(new PrintOrder(Integer.parseInt(orderElements[0]), Integer.parseInt(orderElements[1]), Integer.parseInt(orderElements[2])));
        }
        return orders;
    }
    private int getIndexOfOrder(int id){
        for(int i = 0; i < printOrders.size(); i++){
            if(printOrders.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    public void updateOrders() throws FileNotFoundException {
        printOrders = loadOrdersFromFile();
    }

    private int getNextId() throws IOException {
        return FileOperator.getNextId(FILE_NAME);
    }
    public void deleteOrder(PrintOrder order) throws IOException {
        int index = getIndexOfOrder(order.getId());
        if(index == -1){
            return;
        }
        FileOperator.deleteInstanceFromFile(order.getId(), FILE_NAME);
    }
    public void addOrder(int bookId, int amount) throws IOException {
        int id = getNextId();
        PrintOrder newOrder = new PrintOrder(id, bookId, amount);
        FileOperator.addNewInstanceToFile(newOrder.prepareToSaveToFile(), FILE_NAME);
        printOrders.add(newOrder);
    }


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
            return String.format("Order Id:%s | Book Id %s | Amount: %s", id, bookId, amount);
        }
    }
}
