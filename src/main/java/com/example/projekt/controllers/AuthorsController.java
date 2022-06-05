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
import javafx.scene.layout.Pane;
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
    private TableColumn NameCol;
    @FXML
    private TableColumn SurnameCol;
    @FXML
    private TableColumn BirthDateCol;
    @FXML
    private TableColumn IdCol;

    @FXML
    private TextField NameInput;
    private Authors authors;
    @FXML
    public void initialize(){
        try{
            authors = new Authors();
           authorsTable.setItems(FXCollections.observableArrayList(authors.getAuthors()));
           NameCol.setCellValueFactory(new PropertyValueFactory<Authors.Author, String>("name"));
           SurnameCol.setCellValueFactory(new PropertyValueFactory<Authors.Author, String>("surname"));
           BirthDateCol.setCellValueFactory(new PropertyValueFactory<Authors.Author, String>("birthdate"));
           IdCol.setCellValueFactory(new PropertyValueFactory<Authors.Author, Integer>("id"));
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("AuthorsInput.fxml"));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        AuthorsInputController inputController = fxmlLoader.getController();
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        ArrayList<String> dialogOutput = null;
        if (clickedButton.get() == ButtonType.APPLY){
            dialogOutput = inputController.result();
            authors.addAuthor(dialogOutput.get(0), dialogOutput.get(1), dialogOutput.get(2));
        }
    }
    private void handleEditAuthor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("AuthorsInput.fxml"));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        AuthorsInputController inputController = fxmlLoader.getController();
        inputController.setData((Authors.Author) authorsTable.getSelectionModel().getSelectedItem(););
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        ArrayList<String> dialogOutput = null;
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
