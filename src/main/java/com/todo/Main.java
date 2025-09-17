package com.todo;

import com.dao.tododao;
import com.gui.todogui;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Step 1: Connect to DB
        tododao database = new tododao();
        try (Connection conn = database.getConnection()) {
            System.out.println("✅ Connected to the database");

            // Step 2: Launch GUI safely on Swing thread
            SwingUtilities.invokeLater(() -> {
                todogui gui = new todogui();
                gui.setVisible(true);
            });

        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed: " + e.getMessage());
        }
    }
}
