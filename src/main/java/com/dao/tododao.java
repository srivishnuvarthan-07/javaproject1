package com.dao;

import com.model.todoapp;
import com.Util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class tododao {


    public List<todoapp> getAllTodos() throws SQLException {
        List<todoapp> todoapps = new ArrayList<>();
        String sql = "SELECT * FROM todoapp ORDER BY created_at";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                todoapps.add(mapRow(rs));
            }
        }
        return todoapps;
    }


    public boolean insertTodo(todoapp t) throws SQLException {
        String sql = "INSERT INTO todoapp (title, description, completed) VALUES (?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, t.getTitle());
            stmt.setString(2, t.getDescription());
            stmt.setBoolean(3, t.isCompleted());
            int rowscount=stmt.executeUpdate();
            return (rowscount == 1);
        }
    }

    public boolean updateTodo(todoapp t) throws SQLException {
        String sql = "UPDATE todoapp SET title = ?, description = ?, completed = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setBoolean(3, t.isCompleted());
            ps.setInt(4, t.getId());
            int updaterow=ps.executeUpdate();
            return (updaterow == 1);
        }
    }

    public boolean deleteTodo(int id) throws SQLException {
        String sql = "DELETE FROM todoapp WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int a=ps.executeUpdate();
            return a>0;
        }
    }

    public List<todoapp> getCompletedTodos() throws SQLException {
        return getTodosByStatus(true);
    }

    public List<todoapp> getPendingTodos() throws SQLException {
        return getTodosByStatus(false);
    }


    private List<todoapp> getTodosByStatus(boolean completed) throws SQLException {
        List<todoapp> todoapps = new ArrayList<>();
        String sql = "SELECT * FROM todoapp WHERE completed = ? ORDER BY created_at";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, completed);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    todoapps.add(mapRow(rs));
                }
            }
        }
        return todoapps;
    }

    private todoapp mapRow(ResultSet rs) throws SQLException {
        todoapp t = new todoapp();
        t.setId(rs.getInt("id"));
        t.setTitle(rs.getString("title"));
        t.setDescription(rs.getString("description"));
        t.setCompleted(rs.getBoolean("completed"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            t.setCreated_at(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            t.setUpdated_at(updatedAt.toLocalDateTime());
        }

        return t;
    }
}
