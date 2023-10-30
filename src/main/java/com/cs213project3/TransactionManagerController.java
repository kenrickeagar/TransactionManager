package com.cs213project3;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.lang.module.FindException;
import java.text.DecimalFormat;
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
    void ToggleAllCampusButtons(ActionEvent event, boolean toggle){
    if(!toggle) { //toggle = true enable them
        nbButton.setDisable(true);
        newarkButton.setDisable(true);
        camdenButton.setDisable(true);
        return;
    }
    nbButton.setDisable(false);
    newarkButton.setDisable(false);
    camdenButton.setDisable(false);

}
    @FXML
    void disableAccountButtons(ActionEvent event){
        if(checkingButton.isSelected()){
           ToggleAllCampusButtons(event,false);
            loyalBox.setDisable(true);
        }
        if(savingsButton.isSelected()){
            ToggleAllCampusButtons(event,false);
            loyalBox.setDisable(false);
        }
        if(mmButton.isSelected()){
            ToggleAllCampusButtons(event,false);
            loyalBox.setDisable(true);
        }
        if(ccButton.isSelected()){
            ToggleAllCampusButtons(event,true);
            loyalBox.setDisable(true);
        }
    }

    @FXML
    private boolean accountSelected(ActionEvent event){
        if(!mmButton.isSelected() && !ccButton.isSelected()){
            if(!checkingButton.isSelected() && !savingsButton.isSelected()){
                return false;
            }
        }
        return true;
    }
    @FXML
    private boolean campusSelected(ActionEvent event){
        if(!newarkButton.isSelected() && !camdenButton.isSelected() && !nbButton.isSelected()){
            return false;
        }
        return true;
    }

    private boolean containsSpecialChars(String input){
        int capitalLowerBound = 65; // based off ascii capital letters have val of 65-90
        int capitalUpperBound = 90;
        int lowerCaseLowerBound = 97; // ascii lower case letters have val of 97-122
        int lowerCaseUpperBound = 122;
        char[] array = input.toCharArray();
        for(int i = 0; i<array.length; i++){
            int temp = (int)array[i]; //getting ascii
            if((temp<65 || temp>90) && (temp<97 || temp >122)){ //if its in range theres no special characters
                return true;
            }
        }

        return false;

    }
    @FXML
    private String exceptionFinder(ActionEvent event){ //true = for open/close false = for withdrawal/deposit (needed because one requires campus specified and other does not)
        String exception = "";
        if(firstNameText.getText().isEmpty()){
            exception = "Missing First Name\n";
        }
        if(lastNameText.getText().isEmpty()){
            exception+= "Missing Last Name\n";
        }
        if(containsSpecialChars(firstNameText.getText()) && !firstNameText.getText().isEmpty()){
            exception+= "First Name Cannot Contain Special Characters Or Spaces\n";
        }
        if(containsSpecialChars(lastNameText.getText()) && !lastNameText.getText().isEmpty()){
            exception+= "Last Name Cannot Contain Special Characters Or Spaces\n";
        }
        if(datepicker.getValue()==null){
            exception += "Missing Date of Birth\n";
        }
        if(amountText.getText().isEmpty()){
            exception+= "Missing Amount\n";
        }
        if(!accountSelected(event)){
            exception += "No Account Type Selected\n";
        }
        if(!campusSelected(event) && ccButton.isSelected()){
            exception+= "No Campus Selected\n";
        }
        try{
            double amount = Double.parseDouble(amountText.getText());
        }
        catch(NumberFormatException e){
            exception+= "Invalid Amount Type\n";
        }
        double amount = Double.parseDouble(amountText.getText());
        if (amount < 2000 && mmButton.isSelected()){
            exception+= "Minimum of $2000 to open a Money Market account.\n";
        }
        if (amount <= 0) {
            exception+= "Initial deposit cannot be 0 or negative.\n";
        }
        return exception;
    }



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

        String exceptionA = exceptionFinder(event);
        if(!exceptionA.equals("")){
            messageArea.setText(exceptionA);
            return;
        }

        String fname = firstNameText.getText(),lname = lastNameText.getText();
        String[] dateinfo = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).split("-");
        int month = Integer.parseInt(dateinfo[0]), day = Integer.parseInt(dateinfo[1]),year = Integer.parseInt(dateinfo[2]);
        Date dob = new Date(month,day,year);
        String dateS = dob.toString();
        Profile holder = new Profile(fname,lname,dob);
        double amount = Double.parseDouble(amountText.getText());

        Account newAcc = makeAccount(holder,amount);
        String accountType = newAcc.accountType();

        if (newAcc.getHolder().getDob().futureOrToday()) {
            messageArea.setText("DOB invalid: " + dateS  + " cannot be today or a future day.");
            return;
        }
        if (newAcc.getHolder().getDob().underSixteen()) {
            messageArea.setText("DOB invalid: " + dateS  + " under 16.");
            return;
        }
        if (newAcc.getHolder().getDob().overTwentyFour() && accountType.equals("College Checking")) {
            messageArea.setText ("DOB invalid: " + dateS  + " over 24.");
            return;
        }
        boolean didWeOpen = database.open(newAcc);
        String returnString = fname + " " + lname + " " + dateS + " " + "(" + accountType + ")";
        if(didWeOpen) {
            messageArea.setText(returnString + " " + "Opened."); // for testing purposes
            return;
        }
        messageArea.setText(returnString + " already exists in database");
    }

    @FXML
    void closeAccountButton(ActionEvent event){

        String exceptionA = exceptionFinder(event);

        if(!exceptionA.equals("")){
            messageArea.setText(exceptionA);
            return;
        }

        String fname = firstNameText.getText(), lname = lastNameText.getText();
        String[] dateinfo = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).split("-");

        int month = Integer.parseInt(dateinfo[0]), day = Integer.parseInt(dateinfo[1]), year = Integer.parseInt(dateinfo[2]);
        Date dob = new Date(month,day,year);
        String dateS = dob.toString();

        Profile holder = new Profile(fname,lname,dob);
        double amount = Double.parseDouble(amountText.getText());
        Account newAcc = makeAccount(holder,amount);
        String accountType = newAcc.accountType();
        boolean didWeClose = database.close(newAcc);

        String returnString = fname + " " + lname + " " + dateS + " " + "(" + accountType + ")";
        if(!didWeClose){
            messageArea.setText(returnString + " " + "is not in the database.");
            return;
        }
        messageArea.setText(returnString+ " "+ "has been closed."); // for testing purposes
        //will add exception handling later

    }

    @FXML
    void depositButton(ActionEvent event){

        //
        //will add exception handling later
    }

    @FXML
    void withdrawalButton(ActionEvent event){

    }

    private double roundDouble(double amount) {
        double scale = Math.pow(10, 2);
        amount = Math.round(amount * scale) / scale;
        return amount;
    }

    @FXML
    void printFeesButton(ActionEvent event){
        //display sorted print fees
        if (database.getNumAcct() == 0) {
            messageArea.setText("Account Database is empty!");
        }
        else {
            database.sortAccounts();
            String display = "*list of accounts with fee and monthly interest.";
            for (int i = 0; i < database.getNumAcct(); i++) {
                Account account = database.getAccount(i);
                String formatFee = new DecimalFormat("#0.00").format(account.monthlyFee());
                String monthlyFee = "::fee $" + formatFee;

                double monthlyInterest = (account.getBalance() * account.monthlyInterest() / 12);
                monthlyInterest = roundDouble(monthlyInterest);
                String formatInterest = new DecimalFormat("#0.00").format(monthlyInterest);
                String interest = "::monthly interest $" + formatInterest;
                String temp = account + monthlyFee + interest;
                display += "\n" + temp;
            }
            display += "\n" + "* end of list.";
            messageArea.setText(display);
        }
    }

    @FXML
    void printSortedButton(ActionEvent event){
        //display sorted by accounts to textarea
        if (database.getNumAcct() == 0) {
            messageArea.setText("Account Database is empty!");
        }
        else {
            database.sortAccounts();
            String display = "*Accounts sorted by account type and profile.";
            for(int i =0; i< database.getNumAcct(); i++){
                String temp = database.getAccount(i).toString();
                display += "\n" + temp;
            }
            display += "\n" + "* end of list.";
            messageArea.setText(display);
        }
    }

    @FXML
    void printUpdatedBalanceButton(ActionEvent event){
        //display updated balance to textArea
        if (database.getNumAcct() == 0) {
            messageArea.setText("Account Database is empty!");
        }
        else {
            database.sortByUpdateBalances();
            String display = "* list of accounts with fees and interests applied.";
            for(int i = 0; i < database.getNumAcct(); i++){
                String temp = database.getAccount(i).toString();
                display += "\n" + temp;
            }
            display += "\n" + "* end of list.";
            messageArea.setText(display);
        }
    }

    @FXML
    void loadFromFileButton(ActionEvent event){
    //method to process commands from text file (basically shove the entire original transaction manager stuff in here)
    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
