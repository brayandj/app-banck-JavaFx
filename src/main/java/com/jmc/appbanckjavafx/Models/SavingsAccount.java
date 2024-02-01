package com.jmc.appbanckjavafx.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsAccount extends Account{
    //The withdrowal limit from the savings
    private final DoubleProperty withdrawalLimit;
    protected SavingsAccount(String owner, String accountNumber, double balance, int tLimit, double withdrawalLimit) {
        super(owner, accountNumber, balance);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "withdrawal Limit", withdrawalLimit);
    }
    public DoubleProperty withdrawalLimitProp() {
        return withdrawalLimit;
    }
}
