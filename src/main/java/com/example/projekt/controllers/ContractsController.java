package com.example.projekt.controllers;

import com.example.projekt.Contracts;
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
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ContractsController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView contractsTable;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn contractTypeCol;
    @FXML
    private TableColumn authorCol;
    @FXML
    private TableColumn positionTitle;
    @FXML
    private Button cancelContract;

    private Contracts contracts;

    @FXML
    private void initialize() throws FileNotFoundException {
        loadTable();
    }
    @FXML
    private void backScene(ActionEvent event) throws IOException {
        root = new FXMLLoader(Main.class.getResource("programowy-page.fxml")).load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void showPopup() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("sign-contract.fxml"));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        SignContractController inputController = fxmlLoader.getController();
        ArrayList<String> dialogOutput = null;
        Button btnLogin = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        setEventListenerOnButtonOK(btnLogin, inputController);
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK){
            inputController.createNewContract();
            loadTable();
        }
    }
    private void setEventListenerOnButtonOK(Button button, SignContractController inputController){
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(inputController.dataNotSet()){
                event.consume();
                inputController.showDataNotSetAlert();
            }
        });
    }
    @FXML
    private void handleMouseClick(){
        if(contractsTable.getSelectionModel().getSelectedItem() != null){
            cancelContract.setDisable(false);
        } else {
            cancelContract.setDisable(true);
        }
    }

    private void loadTable() throws FileNotFoundException {
        contracts = new Contracts();
        contractsTable.setItems(FXCollections.observableArrayList(contracts.getContracts()));
        idCol.setCellValueFactory(new PropertyValueFactory<>("contractId"));
        contractTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authorsNameAndSurname"));
        positionTitle.setCellValueFactory(new PropertyValueFactory<>("positionName"));
    }
    @FXML
    private void cancelSelectedContract() throws IOException {
        Contracts.Contract selectedItem = (Contracts.Contract) contractsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to cancel this contract?");
        Optional<ButtonType> selectedButton = alert.showAndWait();
        if(selectedButton.get() == ButtonType.OK){
            contracts.cancelContract(selectedItem.getContractId());
            cancelContract.setDisable(true);
            loadTable();
        }
    }

}
