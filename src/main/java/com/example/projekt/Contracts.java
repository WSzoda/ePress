package com.example.projekt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Contracts {
    private final String FILE_NAME = "contracts";
    private List<Contract> contracts;

    public Contracts() throws FileNotFoundException {
        this.contracts = loadContractsFromFile();
    }

    private List<Contract> loadContractsFromFile() throws FileNotFoundException {
        List<Contract> contracts = new ArrayList<>();
        ArrayList<String> contractsArray = FileOperator.getFileDataAsArray(FILE_NAME);
        for(int i = 0 ; i < contractsArray.size(); i++){
            String[] contractElements = contractsArray.get(i).split(";");
            contracts.add(new Contract(Integer.parseInt(contractElements[0]), Integer.parseInt(contractElements[1]), contractElements[2], contractElements[3]));
        }
        return contracts;
    }
    public List<Contract> getContracts(){
        return contracts;
    }

    private int getIndexOfContract(int id){
        for(int i = 0 ; i < contracts.size(); i++){
            if(contracts.get(i).getContractId() == id){
                return i;
            }
        }
        return -1;
    }
    private int getNextID() throws IOException {
        return FileOperator.getNextId(FILE_NAME);
    }
    public void addContract(Contract contract) throws IOException {
        FileOperator.addNewInstanceToFile(contract.prepareToSaveToFile(), FILE_NAME);
    }
    public void addContract(int authorID, String type, String positionName) throws IOException {
        int id = getNextID();
        Contract newContract = new Contract(id, authorID, type, positionName);
        FileOperator.addNewInstanceToFile(newContract.prepareToSaveToFile(), FILE_NAME);
        contracts.add(newContract);
    }
    public void cancelContract(int id) throws IOException {
        int index = getIndexOfContract(id);
        if(index == -1){
            return;
        }
        FileOperator.deleteInstanceFromFile(id, FILE_NAME);
    }


    public static class Contract {
        private int contractId;
        private int authorId;
        private String authorsNameAndSurname;
        private String type;
        private String positionName;

        public Contract(int contractId, int authorId, String type, String positionName) throws FileNotFoundException {
            this.contractId = contractId;
            this.authorId = authorId;
            this.type = type;
            this.positionName = positionName;
            Authors authors = new Authors();
            List<Authors.Author> authorsList = authors.getAuthors();
            int index = authors.getIndexOfAuthor(authorId);
            this.authorsNameAndSurname = authorsList.get(index).getName() + " " + authorsList.get(index).getSurname();
        }

        public String getPositionName() {
            return positionName;
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

        public String getAuthorsNameAndSurname() {
            return authorsNameAndSurname;
        }

        public String prepareToSaveToFile(){
            return String.format("%d;%d;%s;%s", contractId, authorId, type, positionName);
        }

        @Override
        public String toString() {
            return String.format("Umowa Id: %d | Autor Id: %d | Typ: %s", contractId, authorId, type);
        }
    }
}
