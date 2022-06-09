package com.example.projekt.controllers;

import com.example.projekt.Main;
import com.example.projekt.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    @FXML
    void tryLogIn(Event event) throws IOException {
        User user = new User();
        String nextSceneName = user.logIn(login.getText(), password.getText());
        changeScene(nextSceneName, event);
    }

    @FXML
    void checkEnter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            tryLogIn(event);
        }
    }

    private void changeScene(String sceneName, Event event) throws IOException {
        Parent root = new FXMLLoader(Main.class.getResource(sceneName)).load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
