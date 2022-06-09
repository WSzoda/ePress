package com.example.projekt.controllers;

import com.example.projekt.Authors;
import com.example.projekt.Main;
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

public class AuthorsController {

    @FXML
    private Button deleteAuthor;
    @FXML
    private Button editAuthor;
    @FXML
    private TableView authorsTable;
    @FXML
    private TableColumn<Authors.Author, String> NameCol;
    @FXML
    private TableColumn<Authors.Author, String> SurnameCol;
    @FXML
    private TableColumn<Authors.Author, String> BirthDateCol;
    @FXML
    private TableColumn<Authors.Author, Integer> IdCol;

    private Authors authors;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        try {
            authors = new Authors();
            authorsTable.setItems(FXCollections.observableArrayList(authors.getAuthors()));
            NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            SurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
            BirthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
            IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backScene(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("programowy-page.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAddNewAuthor() throws IOException {
        ArrayList<String> dialogOutput = handleAuthorDialog(null);
        if (dialogOutput != null) {
            authors.addAuthor(dialogOutput.get(0), dialogOutput.get(1), dialogOutput.get(2));
            updateAuthors();
        }
    }

    private void updateAuthors() {
        authorsTable.setItems(FXCollections.observableArrayList(authors.getAuthors()));
    }

    @FXML
    private void handleEditAuthor() throws IOException {
        Authors.Author author = (Authors.Author) authorsTable.getSelectionModel().getSelectedItem();
        ArrayList<String> dialogOutput = handleAuthorDialog(author);
        if (dialogOutput != null) {
            authors.editAuthor(new Authors.Author(author.getId(), dialogOutput.get(0), dialogOutput.get(1), dialogOutput.get(2)));
        }
    }

    private ArrayList<String> handleAuthorDialog(Authors.Author author) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("AuthorsInput.fxml"));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        AuthorsInputController inputController = fxmlLoader.getController();
        ArrayList<String> dialogOutput = null;
        if (author != null) {
            inputController.setData(author);
        }
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.APPLY) {
            dialogOutput = inputController.result();
        }
        return dialogOutput;
    }

    @FXML
    private void handleMouseClick() {
        if (authorsTable.getSelectionModel().getSelectedItem() != null) {
            deleteAuthor.setDisable(false);
            editAuthor.setDisable(false);
        } else {
            deleteAuthor.setDisable(true);
            editAuthor.setDisable(true);
        }
    }

    @FXML
    private void handleDeleteAuthorButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to delete Author?");
        alert.setContentText("Are you sure, you want to delete Author?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Authors.Author user = (Authors.Author) authorsTable.getSelectionModel().getSelectedItem();
            authors.deleteAuthor(user.getId());
            authors.updateAuthors();
            authorsTable.setItems(FXCollections.observableArrayList(authors.getAuthors()));
        }
    }

}
