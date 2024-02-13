package com.jmc.appbanckjavafx.Controllers.Client;

public enum DBTableColumns {
    CHECKING_ACCOUNTS("Owner, AccountNumber, TransactionLimit, Balance"),
    SAVINGS_ACCOUNTS("Owner, AccountNumber, WithdrawalLimit, Balance"),
    CLIENTS("FirstName, LastName, PayeeAddress, Password, Date"),
    TRANSACTIONS("Sender, Receiver, Amount, Date, Message");

    final String columns;

    DBTableColumns(String columns) {
        this.columns = columns;
    }

    public String getColumns() {
        return columns;
    }
}

