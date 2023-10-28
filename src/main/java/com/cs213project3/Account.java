package com.cs213project3;

import java.text.DecimalFormat;

/**
 * Define abstract Account class.
 * @author Kenrick Eagar, Zachary Derish
 */

public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    /**
     * Abstract method for Account monthly interest.
     * @return monthly interest as a double.
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method for Account monthly fee.
     * @return monthly fee as a double.
     */
    public abstract double monthlyFee();

    /**
     * Abstract method for Account type.
     * @return Account type as a String.
     */
    public abstract String accountType();

    /**
     * Account object constructor.
     * @param holder Profile object of account holder.
     * @param balance integer for account.
     */
    public Account(Profile holder, double balance){
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Getter method to retrieve account holder profile.
     * @return The profile/holder of the account.
     */
    public Profile getHolder(){
        return this.holder;
    }

    /**
     * Getter method to retrieve current account balance.
     * @return the balance of an account.
     */
    public double getBalance(){
        return this.balance;
    }

    /**
     * Setter method to set account balance.
     * @param newBalance the double representing the monetary balance value.
     */
    public void setBalance(double newBalance){
        this.balance = newBalance;
    }

    /**
     * compareTo method for manager.Account objects by account type and holder information.
     * @param account the object to be compared.
     * @return 1 if this.account greater than account, -1 if this.account less than account, and 0 otherwise.
     */
    @Override
    public int compareTo(Account account){
        if (this.accountType().compareTo(account.accountType()) > 0) {
            return 1;
        }
        if (this.accountType().compareTo(account.accountType()) < 0) {
            return -1;
        }
        if (this.holder.compareTo(account.holder) > 0) {
            return 1;
        }
        if (this.holder.compareTo(account.holder) < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /**
     * Generic equals method to compare Accounts and test equivalency.
     * @param obj Account object to be compared.
     * @return Boolean true if Accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Account){
            Account account = (Account)obj;
            if(this.compareTo(account) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the accounts profile information and balance to a String format.
     * @return String stating Account objects profile and balance information.
     */
    @Override
    public String toString() {
        String profileInfo = this.holder.toString() + "::";
        double balance = this.getBalance();
        String formatBalance = new DecimalFormat("#,##0.00").format(balance);
        String stringBalance = "Balance $" + formatBalance;
        return profileInfo + stringBalance;
    }

}
