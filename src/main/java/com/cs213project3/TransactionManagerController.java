package com.cs213project3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;

//assuming database has to be created in this file
public class TransactionManagerController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField firstNameText, lastNameText, amountText;
    @FXML
    private DatePicker datepicker;
    private Database database = new Database();


    @FXML
    void openAccountButton(ActionEvent event){
        String fname = firstNameText.getText();
        String lname = lastNameText.getText();
        Date dob = new Date(datePicker.getValue().toString());
        int amount = 0;
        try{
            amount = Integer.parseInt(amountText.getText());
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Data");
            alert.setHeaderText("Non-numeric data has been entered.");
            alert.setContentText("Please enter amount as a double.");
            alert.showAndWait();
        }

        //will add more later

    }

    @FXML
    void closeAccountButton(ActionEvent event){
        String fname = firstNameText.getText();
        String lname = lastNameText.getText();


        //will add more later

    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}