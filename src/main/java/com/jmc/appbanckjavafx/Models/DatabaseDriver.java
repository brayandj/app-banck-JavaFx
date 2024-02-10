package com.jmc.appbanckjavafx.Models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (this.conn != null && !conn.isClosed()) {
                this.conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet query(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        setParameters(stmt, params);
        return stmt.executeQuery();
    }

    public void update(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        setParameters(stmt, params);
        stmt.executeUpdate();
    }

    public void insert(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        setParameters(stmt, params);
        stmt.executeUpdate();
    }

    private void setParameters(PreparedStatement stmt, Object[] params) throws SQLException {
        for(int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    public ResultSet getClientData(String tableName, String address, String password) {
        try {
            return query("SELECT * FROM " + tableName + " WHERE PayeeAddress = ? AND Password = ?", address, password);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    //El método devuelve el saldo de la cuenta de ahorro
    public double getSavingsAccountBalance(String tableName, String columnName, String pAddress) {
        double balance = 0;
        try {
            ResultSet resultSet = query("SELECT * FROM " + tableName + " WHERE " + columnName + " = ? ", pAddress);
            balance = resultSet.getDouble("Balance");
        } catch (SQLException e) {
            handleException(e);
        }
        return balance;
    }

    //Método para sumar o restar de la balanza una operación dada
    public void updateBalance(String tableName, String columnName, String pAddress, double amount, String operation) {
        ResultSet resultSet;
        try {
            resultSet = query("SELECT * FROM " + tableName + " WHERE " + columnName + " = ? ", pAddress);
            double newBalance;
            if (operation.equals("ADD")) {
                newBalance = resultSet.getDouble("Balance") + amount;
                update("UPDATE " + tableName + " SET Balance = ?" + " WHERE " + columnName + "= ?",newBalance, pAddress);
            } else {
                if (resultSet.getDouble("Balance") > amount) {
                    newBalance = resultSet.getDouble("Balance") + amount;
                    update("UPDATE " + tableName + " SET Balance = ?" + " WHERE " + columnName + "= ?", newBalance, pAddress);
                }
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }
    //Crea y registra una nueva transacción
    public void newTransaction(String tableName,String columms, String sender, String receiver, double amount, String message) {
        try {
            LocalDate date = LocalDate.now();
            insert("INSERT INTO " + tableName + " ("+ columms + ")" + " VALUES (?,?,?,?,?)",sender, receiver, amount, date, message);
        } catch (SQLException e) {
            handleException(e);
        }
    }

    //Datos administrativos
    public ResultSet getAdminData(String tableName, String username, String password) {
        try {
            return query("SELECT * FROM " + tableName + " WHERE Username = ? AND Password = ?", username, password);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    public void createClient(String tableName, String columms, String fname, String lname, String address, String password, LocalDate date) {
        try {
            insert("INSERT INTO " + tableName + " ("+ columms + ")" + " VALUES (?,?,?,?,?)", fname, lname, address, password, java.sql.Date.valueOf(date));

        } catch (SQLException e) {
            handleException(e);
        }
    }
    public ResultSet getAdminData(String username, String password) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM Admins WHERE Username = ? AND Password = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void createAccount(String tableName,String columns, String owner, String number, double limit, double balance) {
        try {
            insert("INSERT INTO " + tableName + " (" + columns + ") VALUES (?,?,?,?)", owner, number, limit, balance);
        } catch (SQLException e) {
            handleException(e);
        }
    }

    public void createCheckingAccount(String table, String columns, String owner, String number, double limit, double balance) {
        createAccount(table, columns, owner, number, limit, balance);
    }

    public void createSavingsAccount(String table, String columns, String owner, String number, double limit, double balance) {
        createAccount(table, columns, owner, number, limit, balance);
    }

    private void handleException(SQLException e) {
        e.printStackTrace();
    }

    public ResultSet getAllClientsData(String tableName) {
        try {
            return query("SELECT * FROM " + tableName);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    public ResultSet depositSavings(String tableName,String columnSet, String columnWhere, String pAddress, double amount) {
        try {
            update("UPDATE " + tableName + " SET " + columnSet + " = ? WHERE " + columnWhere +" = ?", amount, pAddress);
        } catch (SQLException e) {
            handleException(e);
        }
        return null;
    }

    public ResultSet getTransactions(String tableName, String columnNameWhere, String columnNameOr, String pAddress, int limit) {
        try {
            return query("SELECT * FROM " + tableName + " WHERE " + columnNameWhere + " = ? OR " + columnNameOr + " = ? LIMIT " + limit,
                    pAddress, pAddress);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    /*
    *Métodos de utilidad
     */
    public ResultSet searchClient(String tableName,String column, String pAddress) {
        try {
            return query("SELECT * FROM " + tableName + " WHERE " + column + " = ?", pAddress);
        } catch (SQLException e) {
            handleException(e);
        }
        return null;
    }
    public int getListClientsId() {
        try {
            ResultSet rs = query("SELECT seq FROM sqlite_sequence WHERE name = 'Clients'");
            if(rs.next()) {
                return rs.getInt("seq");
            }
        } catch (SQLException e) {
            handleException(e);
        }
        return 0;
    }

    public ResultSet getCheckingAccountData(String tableName, String pAddress) {
        try {
            return query("SELECT * FROM " + tableName + " WHERE Owner = ?", pAddress);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    public ResultSet getSavingsAccountData(String tableName, String pAddress) {
        try {
            return query("SELECT * FROM " + tableName + " WHERE Owner = ?", pAddress);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }
}




