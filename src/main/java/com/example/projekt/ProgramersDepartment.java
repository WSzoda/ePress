package com.example.projekt;

public class ProgramersDepartment implements IDepartment {
    private void createNewContract(Contracts.Contract contract) {
        Contracts contracts = new Contracts();
        contracts.addContract(contract);
    }

    public void orderPrinting(PrintOrders.PrintOrder order, Printers.Printer printer) {
        PrintOrders printOrders = new PrintOrders();
        printOrders.fullFillOrder(order.getId(), printer);

    }
}
