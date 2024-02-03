package com.jmc.appbanckjavafx.Controllers.Client;
public enum DBTableNames {
    ADMINS("Admins"),
    CHECKING_ACCOUNTS("CheckingAccounts"),
    CLIENTS("Clients"),
    SAVINGS_ACCOUNTS("SavingsAccounts"),
    TRANSACTIONS("Transactions"),
    SQLITE_SEQUENCE("sqlite_sequence");

    final String tableName;

    DBTableNames(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
