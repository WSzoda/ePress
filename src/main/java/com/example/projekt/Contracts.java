package com.example.projekt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Contracts {
    private final String fileName = "contracts";
    List<Contract> contracts;

    public Contracts() {
        this.contracts = loadContractsFromFile();
    }

    private List<Contract> loadContractsFromFile(){return new ArrayList<Contract>();
    }

    private int getIndexOfContract(){return 0;}
    private int getNextID() throws IOException {
        return FileOperator.getNextId(fileName);
    }
    public void addContract(Contract contract) throws IOException {
        FileOperator.addNewInstanceToFile(contract.prepareToSaveToFile(), fileName);
    }


    public static class Contract {
        private int contractId;
        private int authorId;
        private String type;

        public Contract(int contractId, int authorId, String type) {
            this.contractId = contractId;
            this.authorId = authorId;
            this.type = type;
        }

        public int getContractId() {
            return contractId;
        }

        public int getAuthorId() {
            return authorId;
        }

        public String getType() {
            return type;
        }
        public String prepareToSaveToFile(){
            return String.format("%d;%d;%s", contractId, authorId, type);
        }

        @Override
        public String toString() {
            return String.format("Umowa Id: %d | Autor Id: %d | Typ: %s", contractId, authorId, type);
        }
    }
}
