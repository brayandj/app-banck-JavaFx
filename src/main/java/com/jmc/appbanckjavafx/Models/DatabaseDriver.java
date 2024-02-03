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

    public ResultSet getAdminData(String tableName, String username, String password) {
        try {
            return query("SELECT * FROM "+ tableName +" WHERE Username = ? AND Password = ?", username, password);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    public void createClient(String tableName, String columms, String fname, String lname, String address, String password, LocalDate date) {
        try {
            update("INSERT INTO " + tableName + " ("+ columms + ")" + " VALUES (?,?,?,?,?)", fname, lname, address, password, java.sql.Date.valueOf(date));

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
    public void createClient(String fName, String lName, String pAddress, String password, LocalDate date) {
        PreparedStatement preparedStatement;
        try {
            String sql = "INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, Date)" +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, lName);
            preparedStatement.setString(3, pAddress);
            preparedStatement.setString(4, password);
            preparedStatement.setDate(5, java.sql.Date.valueOf(date));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(String tableName,String columns, String owner, String number, double limit, double balance) {
        try {
            update("INSERT INTO " + tableName + " (" + columns + ") VALUES (?,?,?,?)", owner, number, limit, balance);
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
        // manejo de excepcion
    }

    public ResultSet getAllClientsData(String tableName) {
        try {
            return query("SELECT * FROM "+ tableName);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }
    /*
    *Utility methods
     */
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

//    public ResultSet getCheckingAccountData(String tableName, String pAddress) {
//        try {
//            return query("SELECT * FROM " + tableName + " WHERE Owner = ?" + "'" + pAddress + "'");
//        } catch (SQLException e) {
//            handleException(e);
//            return null;
//        }
//    }
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




