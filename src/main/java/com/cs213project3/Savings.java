package com.cs213project3;

/**
 * Extend the account class and define savings subclass
 * @author Kenrick Eagar, Zachary Derish
 */

public class Savings extends Account {

    private static final double INTEREST_RATE = 0.04;
    private static final double MONTHLY_FEE = 25.00;

    protected boolean isLoyal; //loyal customer status

    /**
     * Constructor for manager.Savings object.
     * @param holder manager.Profile object of account holder.
     * @param balance integer for account.
     * @param isLoyal boolean for loyal customer status.
     */
    public Savings(Profile holder, double balance, boolean isLoyal){
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Get the INTEREST_RATE for a manager.Savings account.
     * @return INTEREST_RATE constant if not a loyal customer, 0.0425 otherwise.
     */
    @Override
    public double monthlyInterest(){
        double loyalRate = 0.0425;
        if (this.isLoyal) {
            return loyalRate;
        }
        return INTEREST_RATE;
    }

    /**
     * Returns string representation of the account type.
     * @return string indicating the type of account.
     */
    @Override
    public String accountType(){
        return "Savings";
    }

    /**
     * Get the MONTHLY_FEE for a manager.Savings account.
     * @return MONTHLY_FEE constant if balance < 500, 0.0 otherwise.
     */
    @Override
    public double monthlyFee(){
        int feeWaivedLimit = 500;
        double waivedFee = 0.0;

        if (balance >= feeWaivedLimit) {
            return waivedFee;
        }
        return MONTHLY_FEE;
    }

    /**
     * Compares two savings accounts to see if they are equal or not.
     * @param obj Savings object we want to compare to.
     * @return returns true if the Accounts are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Savings){
            Savings account = (Savings)obj;
            if(compareTo(account) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method displays general account information and the account type and loyal status.
     * @return String displaying account type and loyal status.
     */
    @Override
    public String toString() {
        String account = super.toString();
        account = "Savings::" + account;
        if (this.isLoyal) {
            account += "::is loyal";
        }
        return account;
    }
}
