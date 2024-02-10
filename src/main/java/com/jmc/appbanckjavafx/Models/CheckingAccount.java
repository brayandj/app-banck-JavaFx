package com.jmc.appbanckjavafx.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account{
    //El número de transacciones que un cliente puede realizar al día
    private final IntegerProperty transactionLimit;
    protected CheckingAccount(String owner, String accountNumber, double balance, int tLimit) {
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", tLimit);
    }
    public IntegerProperty transactionLimitProp() {
        return transactionLimit;
    }

    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
