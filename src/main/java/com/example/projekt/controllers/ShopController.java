package com.example.projekt.controllers;

import com.example.projekt.Main;
import com.example.projekt.Warehouse;
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

public class ShopController {

    @FXML
    private Button sellBooks;
    @FXML
    private Button printBooks;
    @FXML
    private TableView shopTable;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn bookIdCol;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn stockCol;
    @FXML
    private TextField amountTextField;

    private Warehouse warehouse;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void initialize() throws FileNotFoundException {
        warehouse = new Warehouse();
        shopTable.setItems(FXCollections.observableArrayList(warehouse.getStocks()));
        idCol.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    @FXML
    private void handleSellBooks() {
        try {
            int amount = getValueFromInput();
            Warehouse.MagazineRecord record = (Warehouse.MagazineRecord) shopTable.getSelectionModel().getSelectedItem();
            int onStock = record.getStock();
            if (onStock < amount) {
                throw new NumberFormatException();
            }
            Warehouse.MagazineRecord newRecord = new Warehouse.MagazineRecord(record.getRecordId(), record.getBookId(), onStock - amount);
            warehouse.editRecord(newRecord);
            warehouse.updateRecords();
            updateTable();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid value in Input or not enough books in stock");
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePrintBooks() {
        try {
            int amount = getValueFromInput();
            Warehouse.MagazineRecord record = (Warehouse.MagazineRecord) shopTable.getSelectionModel().getSelectedItem();
            int onStock = record.getStock();
            Warehouse.MagazineRecord newRecord = new Warehouse.MagazineRecord(record.getRecordId(), record.getBookId(), onStock + amount);
            warehouse.editRecord(newRecord);
            warehouse.updateRecords();
            updateTable();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid value in Input");
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMouseClick() {
        if (shopTable.getSelectionModel().getSelectedItem() != null) {
            sellBooks.setDisable(false);
            printBooks.setDisable(false);
        } else {
            sellBooks.setDisable(true);
            printBooks.setDisable(true);
        }
    }

    @FXML
    private void backScene(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("handlowy-page.fxml")).load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void updateTable() {
        shopTable.setItems(FXCollections.observableArrayList(warehouse.getStocks()));
    }

    private int getValueFromInput() throws NumberFormatException {
        try {
            int number = Integer.parseInt(amountTextField.getText());
            return number;
        } catch (NumberFormatException e) {
            throw e;
        }
    }

}
