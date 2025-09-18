package com.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import com.dao.tododao;
import com.model.todoapp;

public class todogui extends JFrame {

    private tododao tdao;
    private JTable todotable;
    private DefaultTableModel tablemodel;
    private JTextField textField;
    private JTextArea descriptionArea;
    private JCheckBox checkBox;
    private JButton addbutton;
    private JButton updatebutton;
    private JButton deletebutton;
    private JButton refreasebutton;
    private JComboBox<String> comboBox;

    public todogui() {
        tdao = new tododao();   // create DAO
        initializecomponent();
        setuplayout();
        setupListeners();
        loadtodo();
    }

    private void initializecomponent() {
        setTitle("Todo Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Table
        String[] carray = {"ID", "Title", "Description", "Completed", "Created At", "Updated At"};
        tablemodel = new DefaultTableModel(carray, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        todotable = new JTable(tablemodel);
        todotable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Input fields
        textField = new JTextField(20);
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        checkBox = new JCheckBox("Completed");

        // Buttons
        addbutton = new JButton("Add");
        updatebutton = new JButton("Update");
        deletebutton = new JButton("Delete");
        refreasebutton = new JButton("Refresh");

        // Filter combo
        String[] fileoption = {"All", "Completed", "Pending"};
        comboBox = new JComboBox<>(fileoption);
    }

    private void setuplayout() {
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(checkBox, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addbutton);
        buttonPanel.add(updatebutton);
        buttonPanel.add(deletebutton);
        buttonPanel.add(refreasebutton);

        // Filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("FILTER"));
        filterPanel.add(comboBox);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(inputPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        northPanel.add(filterPanel, BorderLayout.NORTH);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(todotable), BorderLayout.CENTER);
    }

    private void setupListeners() {
        addbutton.addActionListener(e -> addtodo());
        updatebutton.addActionListener(e -> updatetodo());
        deletebutton.addActionListener(e -> deletetodo());
        refreasebutton.addActionListener(e -> refreasetodo());
    }

    private void addtodo() {
        try {
            String title = textField.getText().trim();
            String desc = descriptionArea.getText().trim();
            boolean completed = checkBox.isSelected();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title cannot be empty!");
                return;
            }

            todoapp t = new todoapp();
            t.setTitle(title);
            t.setDescription(desc);
            t.setCompleted(completed);

            tdao.insertTodo(t);
            loadtodo();
            clearInputs();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding todo: " + ex.getMessage());
        }
    }

    private void updatetodo() {
        int row = todotable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update!");
            return;
        }

        try {
            int id = (int) tablemodel.getValueAt(row, 0);
            String title = textField.getText().trim();
            String desc = descriptionArea.getText().trim();
            boolean completed = checkBox.isSelected();

            todoapp t = new todoapp();
            t.setId(id);
            t.setTitle(title);
            t.setDescription(desc);
            t.setCompleted(completed);

            tdao.updateTodo(t);
            loadtodo();
            clearInputs();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating todo: " + ex.getMessage());
        }
    }

    private void deletetodo() {
        int row = todotable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete!");
            return;
        }

        try {
            int id = (int) tablemodel.getValueAt(row, 0);
            tdao.deleteTodo(id);
            loadtodo();
            clearInputs();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting todo: " + ex.getMessage());
        }
    }

    private void refreasetodo() {
        loadtodo();
    }

    private void loadtodo() {
        try {
            List<todoapp> todos = tdao.getAllTodos();
            updateTable(todos);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading todos: " + e.getMessage());
        }
    }

    private void updateTable(List<todoapp> todos) {
        tablemodel.setRowCount(0);
        for (todoapp t : todos) {
            Object[] obj = {
                    t.getId(),
                    t.getTitle(),
                    t.getDescription(),
                    t.isCompleted(),
                    t.getCreated_at(),
                    t.getUpdated_at()
            };
            tablemodel.addRow(obj);
        }
    }

    private void clearInputs() {
        textField.setText("");
        descriptionArea.setText("");
        checkBox.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new todogui().setVisible(true));
    }
}
