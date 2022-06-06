package com.example.projekt.controllers;

import com.example.projekt.Authors;
import com.example.projekt.Contracts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SignContractController {

    @FXML
    private ChoiceBox<Object> contractTypePicker;
    @FXML
    private ChoiceBox<Object> authorPicker;
    @FXML
    private TextField nameOfBookInput;
    @FXML
    public void initialize() throws FileNotFoundException {
        setContractTypes();
        setAuthors();
    }

    private void setAuthors() throws FileNotFoundException {
        Authors authors = new Authors();
        ArrayList<String> authorsList = new ArrayList<>();
        for(Authors.Author author : authors.getAuthors()){
            String result = author.getId() + " " + author.getName() + " " + author.getSurname();
            authorsList.add(result);
        }
        authorPicker.setItems(FXCollections.observableArrayList(authorsList));
    }
    @FXML
    private void contractTypeChanged(){
        if(contractTypePicker.getSelectionModel().getSelectedItem().equals("Umowa o dzieło")){
            nameOfBookInput.setEditable(true);
        } else {
            nameOfBookInput.setText("");
            nameOfBookInput.setEditable(false);
        }
    }
    private void setContractTypes(){
        ArrayList<String> contractOptions = new ArrayList<>(){
            {
                add("Umowa o pracę");
                add("Umowa o dzieło");
            }
        };
        contractTypePicker.setItems(FXCollections.observableArrayList(contractOptions));
    }
    public boolean dataNotSet(){
        if(authorPicker.getValue() == null){
            return true;
        }
        if(contractTypePicker.getValue() == null){
            return true;
        }
        return false;
    }
    public void showDataNotSetAlert(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Chosen invalid options");
        a.show();
    }
    public void createNewContract() throws IOException {
        String author = (String) authorPicker.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(author.split(" ")[0]);
        Contracts contracts = new Contracts();
        String bookName;
        String type = (String) contractTypePicker.getSelectionModel().getSelectedItem();
        if(nameOfBookInput.getText() == null || nameOfBookInput.getText().trim().isEmpty()){
            bookName = "Niedotyczy";
        } else {
            bookName = nameOfBookInput.getText();
        }
        contracts.addContract(id, type, bookName);
    }

}