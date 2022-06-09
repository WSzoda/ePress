package com.example.projekt.controllers;

import com.example.projekt.Authors;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AuthorsInputController {
    @FXML
    private TextField NameInput;
    @FXML
    private TextField SurnameInput;
    @FXML
    private DatePicker DateInput;

    public ArrayList<String> result() {
        ArrayList<String> result = new ArrayList<>();
        result.add(NameInput.getText());
        result.add(SurnameInput.getText());
        result.add(DateInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return result;
    }

    public void setData(Authors.Author author) {
        NameInput.setText(author.getName());
        SurnameInput.setText(author.getSurname());
        String date = author.getBirthdate();
        DateTimeFormatter customDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, customDateTimeFormatter);
        DateInput.setValue(localDate);
    }
}
