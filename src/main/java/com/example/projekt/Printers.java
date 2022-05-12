package com.example.projekt;

import java.util.List;

public class Printers {
    private List<Printer> printers;
    private void printOrder(PrintOrders.PrintOrder order){}

    public static class Printer {
        private int qualityOfPrint;
        private int printerId;

        public void printBooks(PrintOrders.PrintOrder order) {
            Warehouse.changeBookAmounts(order.getBookId(), order.getAmount());
            PrintOrders printOrders = new PrintOrders();
            printOrders.deleteOrder(order);

        }
    }
}
