package com.example.projekt;

public class PrintersDepartment implements IDepartment{

    public void orderPrinting(PrintOrders.PrintOrder order, Printers.Printer printer){
        PrintOrders printOrders = new PrintOrders();
        printOrders.fullFillOrder(order.getId(), printer);

    }
}
