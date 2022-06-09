package com.example.projekt.controllers;

import com.example.projekt.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramowyPageController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void goToAuthors(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("authors-main-scene.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("login-page.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void goToContracts(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("contracts-page.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToPossitions(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("books-page.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
