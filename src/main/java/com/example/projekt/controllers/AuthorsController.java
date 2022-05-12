package com.example.projekt.controllers;

import com.example.projekt.Author;
import com.example.projekt.Authors;
import com.example.projekt.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public class AuthorsController {

    @FXML
    private ListView authorsList;
    @FXML
    private Button deleteAuthor;
    @FXML
    private Button editAuthor;
    private Authors authors;
    @FXML
    public void initialize(){
        try{
            authors = new Authors();
            authorsList.setItems(FXCollections.observableArrayList(authors.getAuthors()));
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
            authorsList.getItems().add(result);
        }
        authors.addAuthor("Wojtek", "Szoda", "dwadwa");
        System.out.println("Dziala");
    }

    @FXML
    private void handleMouseClick(){
        System.out.println(authorsList.getSelectionModel().getSelectedItem());
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
            Author user = (Author) authorsList.getSelectionModel().getSelectedItem();
            authors.deleteAuthor(user.getId());
        }
    }

}
