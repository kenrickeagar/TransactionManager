package com.manager;

import java.text.DecimalFormat;

/**
 * Array-Based implementation to hold list of accounts.
 * @author Kenrick Eagar, Zachary Derish
 */
public class AccountDatabase {
    private Account [] accounts; //list of various types of accounts
    private int numAcct; //number of accounts in the array
    public static final int NOT_FOUND = -1;

    /**
     * AccountDatabase Constructor.
     */
    public AccountDatabase(){
        this.accounts = new Account[4];
        int initialSize = 0;
        this.numAcct = initialSize;
    }

    /**
     * returns the number of accounts in database.
     * @return integer, the number of accounts in database.
     */
    public int getNumAcct() {
        return this.numAcct;
    }

    /**
     * Search for an account in the array.
     * @param account manager.Account object.
     * @return integer representing index of desired element in array.
     */
    private int find(Account account) {
        for (int i = 0; i < this.numAcct; i++) {
            if(this.accounts[i].equals(account)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Will check to see if account database contains an account based off profile.
     * @param account the account we want to check if in database.
     * @return boolean indicating true if profile is in database, false otherwise.
     */
    public boolean containsProfile(Account account) {
        for (int i = 0; i < this.numAcct; i++) {
            String account1Type = this.accounts[i].accountType();
            String account2Type = account.accountType();
            boolean secondClause = (account1Type.equals("College Checking") && account2Type.equals("Checking")) || (account1Type.equals("Checking") && account2Type.equals("College Checking"));
            if (this.accounts[i].getHolder().equals(account.getHolder()) && secondClause) {
                return true;
            }
        }
        return false;
    }

    /**
     * Given a select amount of elements of an object, method will attempt to find the account.
     * @param fname the first name of account holder.
     * @param lname the last name of account holder.
     * @param dob the date of birth of account holder.
     * @param accountType the account type we are attempting to locate.
     * @return the index of the element if it is in the database.
     */
    private int findByElements(String fname, String lname, Date dob, String accountType){
        for(int i = 0; i < numAcct; i++){
            String firstName = accounts[i].getHolder().getFname();
            String lastName = accounts[i].getHolder().getLname();
            Date dateOfBirth = accounts[i].getHolder().getDob();
            String accType = accounts[i].accountType();
            if(firstName.equals(fname) && lastName.equals(lname)){ //checking to see if account exists with name
                if(dateOfBirth.compareTo(dob) == 0 && accType.equals(accountType)){ //if it exists then we look for dob and account type
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }


    /**
     * Grow the array capacity by 4.
     */
    private void grow(){
        int n = this.numAcct;
        Account[] temp = new Account[n + 4];
        for (int i = 0; i < n; i++) {
            temp[i] = this.accounts[i];
        }

        this.accounts = new Account[n+4];
        this.accounts = temp;
    }

    /**
    * Checks to see if given account is en element of the accounts array.
    * @param account object we are searching for.
    * @return boolean indicating true if it exists in array false otherwise.
    */
    public boolean contains(Account account){//overload if necessary
        if (this.find(account) == NOT_FOUND) {
            return false;
        }
        return true;
    }

    /**
     * Checker to see if there is space availability in accounts array.
     * @return boolean indicating true if there is space false otherwise.
     */
    private boolean hasSpace(){
        if(this.accounts[accounts.length-1] != null){
            return false;
        }
        return true;
    }

    /**
     * Adds new account to accounts array.
     * @param account object we are adding.
     * @return boolean indicating true if it was successfully added, false otherwise.
     */
    public boolean open(Account account){
        if (this.contains(account)) {
            return false;
        }
        if (!this.hasSpace()) {
            this.grow();
        }
        for (int i = 0; i < this.accounts.length; i++) {
            if(this.accounts[i] == null){
                this.accounts[i] = account;
                this.numAcct++;
                return true;
            }
        }
        return false;
    }

    /**
     * Close given account.
     * @param account object we will be removing.
     * @return boolean indicating true if account has been successfully removed, false otherwise.
     */
    public boolean close(Account account){
        int index = this.find(account);
        if(index != NOT_FOUND){
            for (int i = index; i < this.accounts.length - 1; i++) {
                this.accounts[i] = this.accounts[i + 1];
            }
            this.numAcct--;
            return true;
        }

        return false;
    }

    /**
     * Given an account, get the account balance.
     * @param account the account we want to get balance of.
     * @return double representing balance of account.
     */
    public double getAccountBalance(Account account){
        int index = find(account);
        if(index != NOT_FOUND){
            return accounts[index].getBalance();
        }

        return -1.0;
}

    /**
     * Method to withdraw money from specified account.
     * @param account object we will be withdrawing money from.
     * @return boolean indicating true if withdrawal has been successful false otherwise.
     */
    public boolean withdraw(Account account){ //the account input will be a temp account created by system.in
        String firstName = account.getHolder().getFname();
        String lastName = account.getHolder().getLname();
        Date dateOfBirth = account.getHolder().getDob();
        String accountType = account.accountType();

        double newBalance = account.getBalance();
        int index = findByElements(firstName, lastName, dateOfBirth, accountType); //we extract the data we need to search if account exists
        if(index != NOT_FOUND){ //as long as it exists
            double currentBalance = accounts[index].getBalance(); //get the current balance of the real account
            int balanceLimit = 0;
            if(newBalance >= balanceLimit){ //compare amounts and make sure there's enough money to withdraw
                newBalance = roundDouble(newBalance);
                accounts[index].setBalance(newBalance); //set the balance of the real account
                if (accounts[index].accountType().equals("Money Market")) {
                    MoneyMarket moneyMarket = (MoneyMarket) accounts[index];
                    moneyMarket.incrementWithdrawal();
                }
                return true;
            }
        }
        return false;//if it doesn't exist in the array or there's insufficient funds return false
    } //false if insufficient fund

    /**
     * Method to deposit money from specified account.
     * @param account the account object that is receiving the deposit.
     */
    public void deposit(Account account){
        double newBalance = account.getBalance();
        int index = find(account); //we extract the data we need to search if account exists
        if(index != NOT_FOUND) { //as long as it exists
            newBalance = roundDouble(newBalance);
            accounts[index].setBalance(newBalance); //set the balance of the real account
        }
    }

    /**
     * Method to round account balance.
     * @param amount the balance we will be rounding.
     * @return the rounded double of input number.
     */
    private double roundDouble(double amount) {
        double scale = Math.pow(10, 2);
        amount = Math.round(amount * scale) / scale;
        return amount;
    }

    /**
     * Print the sorted Accounts.
     */
    public void printSorted(){ // insertion sort
        System.out.println("\n* Accounts sorted by account type and profile.");
        if (this.numAcct > 1) { // Insertion sort from: https://www.geeksforgeeks.org/insertion-sort/
            for (int i = 1; i < this.numAcct; ++i) {

                Account account = this.accounts[i];
                int j = i - 1;

                while (j >= 0 && this.accounts[j].compareTo(account) > 0) {
                    this.accounts[j + 1] = this.accounts[j];
                    j = j - 1;
                }
                this.accounts[j + 1] = account;
            }
        }
        for (int i = 0; i < this.numAcct; i++) {
            System.out.println(this.accounts[i].toString());
        }
        System.out.println("* end of list.");
    }

    /**
     * Prints the accounts in order with respect to fees and interests.
     */
    public void printFeesAndInterests() {
        System.out.println("\n* list of accounts with fee and monthly interest");
        if (this.numAcct > 1) { // Insertion sort from: https://www.geeksforgeeks.org/insertion-sort/
            for (int i = 1; i < this.numAcct; ++i) {

                Account account = this.accounts[i];
                int j = i - 1;

                while (j >= 0 && this.accounts[j].compareTo(account) > 0) {
                    this.accounts[j + 1] = this.accounts[j];
                    j = j - 1;
                }
                this.accounts[j + 1] = account;
            }
        }

        for (int i = 0; i < this.numAcct; i++) {
            Account account = this.accounts[i];
            String formatFee = new DecimalFormat("#0.00").format(account.monthlyFee());
            String monthlyFee = "::fee $" + formatFee;

            double monthlyInterest = (account.getBalance() * account.monthlyInterest() / 12);
            monthlyInterest = roundDouble(monthlyInterest);
            String formatInterest = new DecimalFormat("#0.00").format(monthlyInterest);
            String interest = "::monthly interest $" + formatInterest;

            System.out.println(account + monthlyFee + interest);
        }
        System.out.println("* end of list.");
    }

    /**
     * Prints the accounts in order with respect to any updates that have occurred.
     */
    public void printUpdatedBalances(){
        System.out.println("* list of accounts with fees and interests applied.");
        for (int i = 0; i < this.numAcct; i++) {
            Account account = this.accounts[i];
            double monthlyInterest = (account.getBalance() * account.monthlyInterest() / 12);
            double balance = account.getBalance() + monthlyInterest - account.monthlyFee();
            //balance = roundDouble(balance);
            account.setBalance(balance);
            System.out.println(account);
        }

        System.out.println("* end of list.");
    }
}
