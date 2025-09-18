package com.dao;

import com.model.todoapp;
import com.Util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class tododao {

    // Fetch all todos
    public List<todoapp> getAllTodos() throws SQLException {
        List<todoapp> todoapps = new ArrayList<>();
        String sql = "SELECT * FROM todoapp ORDER BY created_at";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                todoapp t = new todoapp();
                t.setId(rs.getInt("id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setCompleted(rs.getBoolean("completed"));
                t.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                t.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());

                todoapps.add(t);
            }
        }
        return todoapps;
    }

    // Insert a new todo
    public void insertTodo(todoapp t) throws SQLException {
        String sql = "INSERT INTO todoapp (title, description, completed) VALUES (?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setBoolean(3, t.isCompleted());

            ps.executeUpdate();
        }
    }

    // Update an existing todo
    public void updateTodo(todoapp t) throws SQLException {
        String sql = "UPDATE todoapp SET title = ?, description = ?, completed = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setBoolean(3, t.isCompleted());
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        }
    }

    // Delete a todo by ID
    public void deleteTodo(int id) throws SQLException {
        String sql = "DELETE FROM todoapp WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
