package com.example.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrintOrders {
    private final String fileName = "contracts";
    List<PrintOrder> printOrders;

    public void fullFillOrder(int id, Printers.Printer printer){
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
        List<PrintOrder> orders = new ArrayList<PrintOrder>();
        File ordersFile = new File("src/main/resources/database/orders.txt");
        Scanner scanner = new Scanner(ordersFile);
        scanner.nextLine();
        while(scanner.hasNextLine()){
            String ordersString = scanner.nextLine();
            String[] orderElements = ordersString.split(";");
            orders.add(new PrintOrder(Integer.parseInt(orderElements[0]), Integer.parseInt(orderElements[1]), Integer.parseInt(orderElements[2])));
        }
        scanner.close();
        return orders;
    }

    private int getNextId() throws IOException {
        File ordersFile = new File("src/main/resources/database/orders.txt");
        File ordersFileTemp = new File("src/main/resources/database/ordersTemp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFileTemp));
        int id = Integer.parseInt(reader.readLine());
        writer.write(String.valueOf(id+1));
        String currentLine;
        while((currentLine = reader.readLine()) != null){
            writer.newLine();
            writer.write(currentLine);
        }
        writer.close();
        reader.close();

        ordersFile.delete();
        ordersFileTemp.renameTo(ordersFile); //ordersFileTemp.renameTo(ordersFile);
        return id;
    }
    public void deleteOrder(PrintOrder order){}
    public void addOrder(PrintOrder order){}


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
