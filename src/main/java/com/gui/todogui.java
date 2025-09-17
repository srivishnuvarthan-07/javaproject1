package com.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.dao.tododao;

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

        // Example action listener
        addbutton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add button clicked!"));
    }

    private void setuplayout() {
        setLayout(new BorderLayout());

        // Table in scroll pane
//        JScrollPane scrollPane = new JScrollPane(todotable);
//        add(scrollPane, BorderLayout.CENTER);

        // Input panel (bottom)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.anchor = GridBagConstraints.WEST;

        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(textField, gbc);
        add(inputPanel, BorderLayout.NORTH);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(descriptionArea, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(inputPanel, BorderLayout.NORTH);


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new todogui().setVisible(true);
        });
    }
}
