import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ToDoApp {
    private JFrame mainFrame; // To-Do List Viewer
    private JTable table;
    private DefaultTableModel tableModel;
    private JFrame formFrame; // To-Do List Form

    public ToDoApp() {
        initializeMainFrame();
    }

    private void initializeMainFrame() {
        mainFrame = new JFrame("To-Do List Viewer");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Task Name", "Task Description", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        // Add Task button
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> openFormWindow());
        JPanel topPanel = new JPanel();
        topPanel.add(addButton);
        mainFrame.add(topPanel, BorderLayout.NORTH);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void openFormWindow() {
        if (formFrame != null && formFrame.isShowing()) {
            // If form is already open, bring it to front
            formFrame.toFront();
            return;
        }

        formFrame = new JFrame("Add New Task");
        formFrame.setSize(400, 300);
        formFrame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Task Name
        JLabel nameLabel = new JLabel("Task Name:");
        JTextField nameField = new JTextField(20);

        // Task Description
        JLabel descLabel = new JLabel("Task Description:");
        JTextArea descArea = new JTextArea(3, 20);
        JScrollPane descScrollPane = new JScrollPane(descArea);

        // Status
        JLabel statusLabel = new JLabel("Status:");
        String[] statuses = {"Not Started", "Ongoing", "Completed"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);

        // Adding components to formPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(descLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(statusLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(statusCombo, gbc);

        // Save Button
        JButton saveButton = new JButton("Save Task");
        saveButton.addActionListener(e -> {
            String taskName = nameField.getText().trim();
            String taskDesc = descArea.getText().trim();
            String taskStatus = (String) statusCombo.getSelectedItem();

            if (taskName.isEmpty() || taskDesc.isEmpty()) {
                JOptionPane.showMessageDialog(formFrame, "Please fill in Task Name and Description.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add to table
            Object[] rowData = {taskName, taskDesc, taskStatus};
            tableModel.addRow(rowData);

            // Close form window
            formFrame.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        formFrame.add(formPanel, BorderLayout.CENTER);
        formFrame.add(buttonPanel, BorderLayout.SOUTH);
        formFrame.setLocationRelativeTo(mainFrame);
        formFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(ToDoApp::new);
    }
                          }
