package com.cs213project3;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.time.format.DateTimeFormatter;

//assuming database has to be created in this file
public class TransactionManagerController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField firstNameText, lastNameText, amountText;
    @FXML
    private DatePicker datepicker;
    @FXML
    private RadioButton checkingButton,savingsButton,mmButton,ccButton,nbButton,camdenButton,newarkButton;
    @FXML
    private CheckBox loyalBox;
    @FXML private TextArea messageArea;
    private AccountDatabase database = new AccountDatabase();


@FXML
private Account makeAccount(Profile holder, double balance){

    if(checkingButton.isSelected()){
        return new Checking(holder,balance);
    }
    if(savingsButton.isSelected()){
        boolean loyal = loyalBox.isSelected();
        return new Savings(holder, balance, loyal);
    }
    if(mmButton.isSelected()){
        return new MoneyMarket(holder,balance,0);
    }

    if(nbButton.isSelected()){
        return new CollegeChecking(holder,balance, Campus.NEW_BRUNSWICK);
    }
    if(newarkButton.isSelected()){
        return new CollegeChecking(holder,balance, Campus.NEWARK);
    }
        return new CollegeChecking(holder,balance, Campus.CAMDEN);
}

    @FXML
    void openAccountButton(ActionEvent event){
        String fname = firstNameText.getText();
        String lname = lastNameText.getText();
        String[] dateinfo = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).split("-");
        int month = Integer.parseInt(dateinfo[1]);
        int day = Integer.parseInt(dateinfo[2]);
        int year = Integer.parseInt(dateinfo[0]);
        Date dob = new Date(month,day,year);

        Profile holder = new Profile(fname,lname,dob);
        double amount = Double.parseDouble(amountText.getText());

        database.open(makeAccount(holder,amount));
        messageArea.setText("Successfully Opened Account"); // for testing purposes
        //will add exception handling later

    }

    @FXML
    void closeAccountButton(ActionEvent event){
        String fname = firstNameText.getText();
        String lname = lastNameText.getText();
        String[] dateinfo = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).split("-");
        int month = Integer.parseInt(dateinfo[1]);
        int day = Integer.parseInt(dateinfo[2]);
        int year = Integer.parseInt(dateinfo[0]);
        Date dob = new Date(month,day,year);

        Profile holder = new Profile(fname,lname,dob);
        double amount = Double.parseDouble(amountText.getText());

        boolean didWeClose = database.close(makeAccount(holder,amount));
        if(!didWeClose){
            messageArea.setText("Account Does Not Exist");
            return;
        }
        messageArea.setText("Successfully Closed Account"); // for testing purposes
        //will add exception handling later

    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
