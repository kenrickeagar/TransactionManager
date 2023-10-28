package com.cs213project3;

/**
 * Extend the savings class and define MoneyMarket subclass.
 * @author Kenrick Eagar, Zachary Derish
 */

public class MoneyMarket extends Savings {

    private static final double INTEREST_RATE = 0.045;
    private static final double MONTHLY_FEE = 25.00;

    private int withdrawal; //number of withdrawals

    /**
     * Constructor for Money Market object.
     * @param holder manager.Profile object of account holder.
     * @param balance double for account.
     * @param withdrawal integer for number of withdrawals.
     */
    public MoneyMarket(Profile holder, double balance, int withdrawal){
        super(holder, balance, true);
        this.withdrawal = withdrawal;

    }

    /**
     * Getter method to obtain number of withdrawals.
     * @return the number of withdrawals account has.
     */
    public int getWithdrawal() {
        return this.withdrawal;
    }

    /**
     * Increases the withdrawal of account by 1.
     */
    public void incrementWithdrawal () {
        this.withdrawal++;
    }

    /**
     * Get the INTEREST_RATE for a manager.MoneyMarket account.
     * @return INTEREST_RATE constant if not a loyal customer, 0.0475 otherwise.
     */
    @Override
    public double monthlyInterest(){
        double loyalRate = 0.0475;
        if (balance >= 2000) {
            this.isLoyal = true;
            return loyalRate;
        }
        this.isLoyal = false;
        return INTEREST_RATE;
    }

    /**
     * Get the MONTHLY_FEE for a manager.MoneyMarket account.
     * @return 0.0 if balance > 2000, 25.0 otherwise, and add 10.0 if withdrawals > 3.
     */
    @Override
    public double monthlyFee(){
        int withdrawalFeeLimit = 3;
        double withdrawalFee = 10.0;

        int feeWaiverLimit = 2000;
        double feeWaiver = 0.0;

        if (balance >= feeWaiverLimit) {
            if (withdrawal > withdrawalFeeLimit) {
                return feeWaiver + withdrawalFee;
            }
            return feeWaiver;
        }
        else {
            if (withdrawal > withdrawalFeeLimit) {
                return withdrawalFee + MONTHLY_FEE;
            }
            return MONTHLY_FEE;
        }

    }

    /**
     * @return String representing accountType of object.
     */
    @Override
    public String accountType(){
        return "Money Market";
    }

    /**
     * Given an account will check to see if object type and information is equal.
     * @param obj Account object we want to compare to.
     * @return boolean indicating true if the Accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof MoneyMarket){
            MoneyMarket account = (MoneyMarket)obj;
            if(compareTo(account) == 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * String representation of profile information, account type, and number of withdrawals.
     * @return String representation of profile information, account type, and number of withdrawals.
     */
    @Override
    public String toString() {
        String savingsInfo = super.toString();
        savingsInfo = "Money Market::" + savingsInfo;
        savingsInfo += "::withdrawal: " + this.withdrawal;
        return savingsInfo;
    }
}
