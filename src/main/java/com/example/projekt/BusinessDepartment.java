package com.example.projekt;

public class BusinessDepartment implements IDepartment{
    public void addPrintedBooks() {
    }
    public void createPrintOrder(PrintOrders.PrintOrder order){
        PrintOrders printOrders = new PrintOrders();
        printOrders.addOrder(order);
    }

    @Override
    public void showScene() {

    }
}
