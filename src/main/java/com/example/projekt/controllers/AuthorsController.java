package com.example.projekt.controllers;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public class AuthorsController {

    @FXML
    private ListView authorsList;

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
    private void handleAddNewAuthor(ActionEvent event){
        TextInputDialog textInput = new TextInputDialog();

        textInput.setTitle("Add new Author");

        textInput.getDialogPane().setContentText("Name");

        Optional<String> result = textInput.showAndWait();

        if(result.isPresent()){
            authorsList.getItems().add(result);
        }

        System.out.println("Dziala");
    }

    @FXML
    public void initialize(){
        try{
            Authors authors = new Authors();
            authorsList.setItems(FXCollections.observableArrayList(authors.getAuthors()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
