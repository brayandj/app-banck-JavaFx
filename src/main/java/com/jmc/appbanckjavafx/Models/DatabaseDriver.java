package com.jmc.appbanckjavafx.Models;

import java.sql.*;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    *Client Section
     */
    public ResultSet getClientData(String pAddress, String password) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM Clients WHERE PayeeAddress = ? AND Password = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, pAddress);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    /*
    *Admin Section
     */
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
            e.printStackTrace();;
        }
        return resultSet;
    }
    /*
    *Utility Methods
     */
}
