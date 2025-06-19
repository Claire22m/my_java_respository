package LabActivity6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class LabActivity6SwingToDoList {
    Font font;
    Font SFont;

    public String[] FnameDescStat = {"Task Name", "Description", "Status"};
    public String[] stts = {"Not Started", "Ongoing", "Completed"};
    public DefaultTableModel tableModel;
    public boolean isAddFormOpen = false;

    public LabActivity6SwingToDoList() {
        JFrame UFrame = new JFrame();
        JPanel FPanel = new JPanel();
        FPanel.setLayout(new BorderLayout());
        JPanel SPanel = new JPanel();
        JButton add = new JButton("Add Task");
        SPanel.add(add);

        tableModel = new DefaultTableModel(FnameDescStat, 0);
        JTable table = new JTable(tableModel);
        JScrollPane pane = new JScrollPane(table);
        FPanel.add(pane);

        UFrame.setLayout(new BorderLayout());
        UFrame.add(SPanel, BorderLayout.NORTH);
        UFrame.add(FPanel, BorderLayout.CENTER);
        UFrame.setTitle("TO Do List");
        UFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UFrame.setVisible(true);
        UFrame.setSize(800, 600);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ad) {
                if (!isAddFormOpen) {
                    isAddFormOpen = true;
                    toDoListForm();  
                  }
                }
            }); 
    }

    private void toDoListForm() {
        JFrame sFrame = new JFrame("Add New Task");
        JPanel TPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        JPanel FTPanel = new JPanel(new FlowLayout());
        JLabel TName = new JLabel("Task Name: ");
        JTextField tName = new JTextField(20);
        JLabel fullDescription = new JLabel("Task Description:");
        JTextField description = new JTextField(20);
        JLabel status = new JLabel("Status:");
        JComboBox statuss = new JComboBox(stts);
        JButton Save = new JButton("Save Task");

        TPanel.add(TName);
        TPanel.add(tName);
        TPanel.add(fullDescription);
        TPanel.add(description);
        TPanel.add(status);
        TPanel.add(statuss);
        FTPanel.add(Save);

        sFrame.setTitle("Add New Task");
        sFrame.setLayout(new BorderLayout());
        sFrame.add(TPanel, BorderLayout.PAGE_START);
        sFrame.add(FTPanel, BorderLayout.PAGE_END);
        sFrame.setSize(400, 350);
        sFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sFrame.setVisible(true);
        isAddFormOpen = true;

        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = tName.getText().trim();
                String taskDescription = description.getText().trim();
                String taskStatus = (String) statuss.getSelectedItem();

                if (taskName.isEmpty() || taskStatus.trim().isEmpty()||taskStatus == null) {
                    warning(); 
                } else {
                    tableModel.addRow(new String[]{taskName, taskDescription, taskStatus});
                    isAddFormOpen = false; 
                    sFrame.dispose();     
                }
            }
        });
        
        sFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isAddFormOpen = false;
            }
        });
    }

    private void warning() {
        JFrame warnFrame = new JFrame();
        warnFrame.setLayout(new BorderLayout()); 
        Image image = new ImageIcon("C:\\Users\\Juztin\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\LabActivity6\\warningg.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 10)); 
        JLabel WLabel = new JLabel("Please fill in Task Name and Status.");
        JPanel cPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton ok = new JButton("OK");
        JPanel bPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        cPanel.add(imageLabel);
        cPanel.add(WLabel);
        bPanel.add(ok);
        warnFrame.add(cPanel, BorderLayout.CENTER);
        warnFrame.add(bPanel, BorderLayout.SOUTH);
        warnFrame.setSize(400, 150);
        warnFrame.setTitle("Input Error");
        warnFrame.setLocationRelativeTo(null);
        warnFrame.setVisible(true);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                warnFrame.dispose();
            }
        });


    }

    public static void main(String[] args) {
        new LabActivity6SwingToDoList();
    }
}
