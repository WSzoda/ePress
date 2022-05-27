package com.example.projekt;

import java.io.IOException;
import java.util.List;

public class Printers {
    private List<Printer> printers;
    private void printOrder(PrintOrders.PrintOrder order){}

    public static class Printer {
        private int qualityOfPrint;
        private int printerId;

        public void printBooks(PrintOrders.PrintOrder order) throws IOException {
            Warehouse.changeBookAmounts(order.getBookId(), order.getAmount());
            PrintOrders printOrders = new PrintOrders();
            printOrders.deleteOrder(order);

        }
    }
}
