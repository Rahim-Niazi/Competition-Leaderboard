package view;
import model.Contestant;
import model.ContestantList;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class frontend extends JFrame {

    private User currentUser;
    private ContestantList model;
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea searchResultTextArea;
    private JTable ContestantTable;
    private JTextArea detailsTextArea;
    private JButton updateButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton reportButton;
    private JButton addContestantButton;
    private JButton shortButton;
    private JButton generateReportButton;
    private JButton closeButton;

    public frontend(ContestantList model, User user) {
        this.model = model;
        this.currentUser = user;

        // Initialization of GUI components
        ContestantTable = new JTable();
        detailsTextArea = new JTextArea();
        updateButton = new JButton("Update");
        editButton = new JButton("Edit");
        removeButton = new JButton("Remove");
        reportButton = new JButton("Generate Report");
        shortButton = new JButton("Short Detail");
        searchField = new JTextField(10);
        searchButton = new JButton("Search");
        searchResultTextArea = new JTextArea();
        addContestantButton = new JButton("Add Contestant");
        generateReportButton = new JButton("Generate Final Report");
        JButton closeButton = new JButton("Close");


        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(generateReportButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(shortButton);
        buttonPanel.add(addContestantButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        buttonPanel.add(closeButton);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(ContestantTable), BorderLayout.CENTER);
        tablePanel.add(detailsTextArea, BorderLayout.SOUTH);
        tablePanel.add(searchResultTextArea, BorderLayout.NORTH);

        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        setupButtonVisibility();

        updateButton.addActionListener(e -> updateContestantTable());
        editButton.addActionListener(e -> editContestantDetails());
        removeButton.addActionListener(e -> removeContestant());
        reportButton.addActionListener(e -> generateReport());
        shortButton.addActionListener(e -> displayShortDetailsForContestant());
        addContestantButton.addActionListener(e -> addContestantFromForm());
        searchButton.addActionListener(e -> searchContestant());
        generateReportButton.addActionListener(e -> generateFinalReport());
        closeButton.addActionListener(e -> closeProgam());


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        updateContestantTable();
    }
    public void searchContestant() {
        try
        {
            int ContestantNumberToSearch = Integer.parseInt(searchField.getText());
            Contestant foundContestant = model.getContestantByNumber(ContestantNumberToSearch);
            if (foundContestant != null)
            {
                searchResultTextArea.setText("Contestant Found:\n" + foundContestant.getFullDetails());
            }
            else
            {
                searchResultTextArea.setText("Contestant not found.");
            }
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid Contestant number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
