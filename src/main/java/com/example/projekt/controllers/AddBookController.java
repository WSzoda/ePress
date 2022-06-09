package com.example.projekt.controllers;

import com.example.projekt.Authors;
import com.example.projekt.Books;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class AddBookController {

    @FXML
    private ChoiceBox genrePicker;

    @FXML
    private ChoiceBox typePicker;

    @FXML
    private ChoiceBox frequencyPicker;

    @FXML
    private ChoiceBox authorPicker;

    @FXML
    private TextField titleField;

    @FXML
    private TextField coverImageTextField;

    private File image;

    @FXML
    private void initialize() throws FileNotFoundException {
        setFrequency();
        setAuthors();
        setTypes();
        setGenres();
    }

    private void setGenres(){
        ArrayList<String> genres = new ArrayList<>(){
            {
                add("Dark Fantasy");
                add("Fantasy");
                add("Sci-Fi");
                add("Technical");
                add("Romance");
                add("Thriller");
                add("Horror");
                add("For Kids");
            }
        };
        genrePicker.setItems(FXCollections.observableArrayList(genres));
    }
    private void setTypes(){
        ArrayList<String> types = new ArrayList<>(){
            {
                add("Book");
                add("Album");
                add("Magazine");
                add("Atlas");
            }
        };
        typePicker.setItems(FXCollections.observableArrayList(types));
    }
    private void setAuthors() throws FileNotFoundException {
        Authors authors = new Authors();
        ArrayList<String> authorsList = new ArrayList<>();
        for(Authors.Author author : authors.getAuthors()){
            String result = author.getId() + " " + author.getName() + " " + author.getSurname();
            authorsList.add(result);
        }
        authorPicker.setItems(FXCollections.observableArrayList(authorsList));
    }
    private void setFrequency(){
        ArrayList<String> frequency = new ArrayList<>(){
            {
                add("Daily");
                add("Weekly");
                add("Monthly");
                add("Once per 3 months");
            }
        };
        frequencyPicker.setItems(FXCollections.observableArrayList(frequency));
    }

    @FXML
    private void typeChanged(){
        if(typePicker.getSelectionModel().getSelectedItem().equals("Magazine")){
            frequencyPicker.setDisable(false);
        } else {
            frequencyPicker.setDisable(true);
            frequencyPicker.setValue(null);
        }
    }

    @FXML
    private void handleChooseImage(ActionEvent event){
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if(f!= null){
            coverImageTextField.setText(f.getName());
            image = f;
        }
    }

    private void copyImage(File image) throws IOException {
        if(image == null){
            return;
        }
        System.out.println(System.getProperty("user.dir"));
        File dest = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + image.getName());
        Files.copy(image.toPath(), dest.toPath());
    }

    public boolean dataNotSet(){
        if(authorPicker.getValue() == null){
            return true;
        }
        if(typePicker.getValue() == null){
            return true;
        }
        if(genrePicker.getValue() == null){
            return true;
        }
        if(titleField.getText().trim().isEmpty()){
            return true;
        }
        return false;
    }

    public void showErrorAlert(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Chosen invalid options");
        a.show();
    }

    public void createNewBook() throws IOException {
        String coverImageName;
        if(coverImageTextField.getText().trim().isEmpty()){
            coverImageName = "placeholder.png";
        } else {
            coverImageName = coverImageTextField.getText();
        }
        copyImage(image);
        String frequency;
        String genre = (String) genrePicker.getSelectionModel().getSelectedItem();
        String type = (String) typePicker.getSelectionModel().getSelectedItem();
        String title = titleField.getText();
        String author = (String) authorPicker.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(author.split(" ")[0]);
        if(frequencyPicker.getValue() == null){
            frequency = "not applicable";
        } else {
            frequency = (String) frequencyPicker.getSelectionModel().getSelectedItem();
        }

        Books books = new Books();
        Books.Book book = new Books.Book(books.getNextID(), id, title, genre, type, frequency, coverImageName);
        books.addBook(book);
    }
}
