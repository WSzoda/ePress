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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class AuthorsController {

    @FXML
    private Button deleteAuthor;
    @FXML
    private Button editAuthor;
    @FXML
    private TableView authorsTable;
    private Authors authors;
    @FXML
    public void initialize(){
        try{
            authors = new Authors();
           authorsTable.setItems(FXCollections.observableArrayList(authors.getAuthors()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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
    private void handleAddNewAuthor() throws IOException {
        TextInputDialog textInput = new TextInputDialog();

        textInput.setTitle("Add new Author");

        textInput.getDialogPane().setContentText("Name");

        Optional<String> result = textInput.showAndWait();

        if(result.isPresent()){
            authorsTable.getItems().add(result);
        }
        authors.addAuthor("Wojtek", "Szoda", "dwadwa");
        authors.updateAuthors();
        authorsTable.setItems(FXCollections.observableArrayList(authors.getAuthors()));
        System.out.println("Dziala");
    }

    @FXML
    private void showPopup() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AuthorsInput.fxml"));
        Pane studentDialogPane = fxmlLoader.load();

        AuthorsController authorsController = fxmlLoader.getController();
    }

    @FXML
    private void handleMouseClick(){
        System.out.println(authorsTable.getSelectionModel().getSelectedItem());
        deleteAuthor.setDisable(false);
        editAuthor.setDisable(false);
    }

    @FXML
    private void handleDeleteAuthorButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to delete Author?");
        alert.setContentText("Are you sure, you want to delete Author?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Authors.Author user = (Authors.Author) authorsTable.getSelectionModel().getSelectedItem();
            authors.deleteAuthor(user.getId());
            authors.updateAuthors();
            authorsTable.setItems(FXCollections.observableArrayList(authors.getAuthors()));
        }
    }

}
