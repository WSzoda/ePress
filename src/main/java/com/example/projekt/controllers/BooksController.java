package com.example.projekt.controllers;

import com.example.projekt.Authors;
import com.example.projekt.Books;
import com.example.projekt.Main;
import com.example.projekt.Warehouse;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    @FXML
    private Button deleteBook;

    private Books books;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private void backScene(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("programowy-page.fxml")).load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() throws FileNotFoundException {
        loadTable();
    }

    private void loadTable() throws FileNotFoundException {
        books = new Books();
        booksTable.setItems(FXCollections.observableArrayList(books.getBooks()));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authorsNameSurname"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        frequencyOfReleasingCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        coverCol.setCellValueFactory(new PropertyValueFactory<>("photo"));
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
            loadTable();
        }
    }

    @FXML
    private void handleMouseClick(){
        if(booksTable.getSelectionModel().getSelectedItem() != null){
            deleteBook.setDisable(false);
        } else {
            deleteBook.setDisable(true);
        }
    }

    @FXML
    private void handleDeleteBook() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to delete Book?");
        alert.setContentText("Are you sure, you want to delete Book?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Books.Book book = (Books.Book) booksTable.getSelectionModel().getSelectedItem();
            Warehouse warehouse = new Warehouse();
            warehouse.deleteRecordForBook(book.getId());
            books.deleteBook(book.getId());
            books.updateBooks();
            booksTable.setItems(FXCollections.observableArrayList(books.getBooks()));
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
