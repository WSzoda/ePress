package com.example.projekt.controllers;

import com.example.projekt.Books;
import com.example.projekt.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class BooksController {
    @FXML
    private TableView booksTable;

    @FXML
    private TableColumn coverCol;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn authorCol;
    @FXML
    private TableColumn genreCol;
    @FXML
    private TableColumn frequencyOfReleasingCol;

    private void loadTable() throws FileNotFoundException {
        Books books = new Books();
        booksTable.setItems(FXCollections.observableArrayList(books.getBooks()));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authorsNameSurname"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        frequencyOfReleasingCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        coverCol.setCellValueFactory(new PropertyValueFactory<>("coverImageName"));
    }

    @FXML
    private void handleAddNewBook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addBookInput.fxml"));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        Button btnLogin = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        AddBookController addBookController = fxmlLoader.getController();
        ArrayList<String> dialogOutput = null;
        setEventListenerOnButtonOK(btnLogin, addBookController);
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.get() == ButtonType.OK){
            addBookController.createNewBook();
        }
    }

    private void setEventListenerOnButtonOK(Button button, AddBookController inputController){
        button.addEventFilter(ActionEvent.ACTION, event -> {
                if(inputController.dataNotSet()){
                    event.consume();
                    inputController.showErrorAlert();
            }
        });
    }
}
