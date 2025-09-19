package com.todo;

import com.Util.Database;
import com.gui.todogui;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        try (Connection conn = database.getConnection()) {
            System.out.println("Connected to the database");

            SwingUtilities.invokeLater(() -> {
                todogui gui = new todogui();
                gui.setVisible(true);
            });

        } catch (SQLException e) {
            System.out.println("Database.java Connection Failed: " + e.getMessage());
        }
    }
}
