package com.manager;

/**
 * Extend the checking class and define collegeChecking subclass.
 * @author Kenrick Eagar, Zachary Derish
 */

public class CollegeChecking extends Checking {

    private static final double MONTHLY_FEE = 0.00;
    private static final double INTEREST_RATE = 0.01;
    private Campus campus; //campus code

    /**
     * Constructor for College Checking object.
     * @param holder Profile object of account holder.
     * @param balance integer for account.
     * @param campus Campus code.
     */
    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }

    /**
     * Get INTEREST_RATE for a CollegeChecking account.
     * @return INTEREST_RATE constant.
     */
    @Override
    public double monthlyInterest(){
        return INTEREST_RATE;
    }

    /**
     * Get MONTHLY_FEE for a CollegeChecking account.
     * @return MONTHLY_FEE constant.
     */
    @Override
    public double monthlyFee(){
        return MONTHLY_FEE;
    }

    /**
     * String representation of Account Type.
     * @return String representing the account type.
     */
    @Override
    public String accountType(){
        return "College Checking";
    }

    /**
     * Given an account will check to see if object type and information is equal.
     * @param obj Account object we want to compare to.
     * @return boolean indicating true if the Accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof CollegeChecking){
            CollegeChecking account = (CollegeChecking)obj;
            if(compareTo(account) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the campus of collegeChecking account.
     * @return returns the campus of collegeChecking account.
     */
    public Campus getCampus() {
        return this.campus;
    }

    /**
     * String representation of profile information, account type, and campus.
     * @return String representation of profile information, account type, and number of withdrawals.
     */
    @Override
    public String toString() {
        String superToString = super.toString();
        superToString = superToString.replace("Checking::", "");
        return "College Checking::" + superToString + "::" + this.getCampus();
    }
}
