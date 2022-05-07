package com.example.projekt.controllers;

import com.example.projekt.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    @FXML
    void tryLogIn() throws FileNotFoundException {
        System.out.println(login.getText());
        System.out.println(password.getText());
        System.out.println("JD");
        User user = new User();
        user.logIn(login.getText(), password.getText());
    }

}
