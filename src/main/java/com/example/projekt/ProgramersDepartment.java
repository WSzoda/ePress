package com.example.projekt;

import java.io.IOException;

public class ProgramersDepartment implements IDepartment {
    private void createNewContract(Contracts.Contract contract) throws IOException {
        Contracts contracts = new Contracts();
        contracts.addContract(contract);
    }

    public void orderPrinting(PrintOrders.PrintOrder order, Printers.Printer printer) {
        PrintOrders printOrders = new PrintOrders();
        printOrders.fullFillOrder(order.getId(), printer);

    }

    @Override
    public void showScene() {

    }
}
