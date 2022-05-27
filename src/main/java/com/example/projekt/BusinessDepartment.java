package com.example.projekt;


import java.io.IOException;

public class BusinessDepartment implements IDepartment{
    public void addPrintedBooks() {
    }
    public void createPrintOrder(int bookId, int amount) throws IOException {
        PrintOrders printOrders = new PrintOrders();
        printOrders.addOrder(bookId, amount);
    }

    @Override
    public void showScene() {

    }
}
