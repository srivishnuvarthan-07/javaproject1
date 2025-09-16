package com.todo;

import com.dao.DataBaseConnect;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DataBaseConnect dataBaseConnect1 = new DataBaseConnect();
        try {
            Connection conn = dataBaseConnect1.getConnection();
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }

    }
}
