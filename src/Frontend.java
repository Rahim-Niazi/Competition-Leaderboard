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
    public void searchContestant()
    {
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
    public void setupButtonVisibility()
    {
        if ("staff".equals(currentUser.getRole()))
        {
            // Staff can access all buttons
            updateButton.setEnabled(true);
            editButton.setEnabled(true);
            removeButton.setEnabled(true);
            reportButton.setEnabled(true);
            shortButton.setEnabled(true);
            addContestantButton.setEnabled(true);
            searchButton.setEnabled(true);
        }
        else if ("Contestant".equals(currentUser.getRole()))
        {
            // Contestants can only search for their scores
            updateButton.setEnabled(false);
            editButton.setEnabled(false);
            removeButton.setEnabled(false);
            reportButton.setEnabled(false);
            shortButton.setEnabled(false);
            addContestantButton.setEnabled(false);
            searchButton.setEnabled(true);
        }
    }
    public void updateContestantTable()
    {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Contestant Number");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Level");
        tableModel.addColumn("Age");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Country");
        tableModel.addColumn("Scores");

        for (Contestant contestant : model.getAllContestants())
        {
            Object[] rowData =
                    {
                            contestant.getContestantNumber(),
                            contestant.getName(),
                            contestant.getCategory(),
                            contestant.getLevel(),
                            contestant.getAge(),
                            contestant.getGender(),
                            contestant.getCountry(),
                            Arrays.toString(contestant.getScores())
                    };
            tableModel.addRow(rowData);
        }

        ContestantTable.setModel(tableModel);
        String filePath = "src\\Contestants.csv";
        if ("staff".equals(currentUser.getRole()))
        {
            model.writeContestantsToCSV(filePath);
        }
    }
    public void editContestantDetails()
    {

        if ("Contestant".equals(currentUser.getRole()))
        {
            JOptionPane.showMessageDialog(this, "Contestants are not allowed to edit details.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = ContestantTable.getSelectedRow();
        if (selectedRow != -1)
        {
            int ContestantNumber = (int) ContestantTable.getValueAt(selectedRow, 0);
            Contestant contestant = model.getContestantByNumber(ContestantNumber);
            if (contestant != null)
            {
                JTextField nameField = new JTextField(contestant.getName());
                JComboBox<Contestant.Level> levelComboBox = new JComboBox<>(Contestant.Level.values());
                levelComboBox.setSelectedItem(contestant.getLevel());
                JComboBox<Contestant.Category> categoryComboBox = new JComboBox<>(Contestant.Category.values());
                categoryComboBox.setSelectedItem(contestant.getCategory());
                JTextField ageField = new JTextField(String.valueOf(contestant.getAge()));
                JTextField genderField = new JTextField(contestant.getGender());
                JTextField countryField = new JTextField(contestant.getCountry());

                Object[] message =
                        {
                                "Name:", nameField,
                                "Level:", levelComboBox,
                                "Category:", categoryComboBox,
                                "Age:", ageField,
                                "Gender:", genderField,
                                "Country:", countryField
                        };

                int option = JOptionPane.showConfirmDialog(this, message, "Edit Contestant Details", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION)
                {
                    try
                    {
                        String newName = nameField.getText();
                        Contestant.Level newLevel = (Contestant.Level) levelComboBox.getSelectedItem();
                        Contestant.Category newCategory = (Contestant.Category) categoryComboBox.getSelectedItem();
                        int newAge = Integer.parseInt(ageField.getText());
                        String newGender = genderField.getText();
                        String newCountry = countryField.getText();

                        int[] newScores = new int[contestant.getScores().length];
                        for (int i = 0; i < newScores.length; i++)
                        {
                            String scoreStr = JOptionPane.showInputDialog("Enter new score for round " + (i + 1) + ":", contestant.getScores()[i]);
                            newScores[i] = Integer.parseInt(scoreStr);
                        }

                        contestant.setName(newName);
                        contestant.setLevel(newLevel);
                        contestant.setCategory(newCategory);
                        contestant.setAge(newAge);
                        contestant.setGender(newGender);
                        contestant.setCountry(newCountry);
                        contestant.setScores(newScores);

                        updateContestantTable();
                    }
                    catch (NumberFormatException ex)
                    {
                        JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    public void removeContestant()
    {

        if ("Contestant".equals(currentUser.getRole()))
        {
            JOptionPane.showMessageDialog(this, "Contestants are not allowed to remove Contestants.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = ContestantTable.getSelectedRow();
        if (selectedRow != -1)
        {
            int ContestantNumber = (int) ContestantTable.getValueAt(selectedRow, 0);
            model.removeContestant(ContestantNumber);
            updateContestantTable();
        }
    }

    public void generateReport()
    {
        if ("Contestant".equals(currentUser.getRole()))
        {
            detailsTextArea.setText("Access Denied: Contestants are not allowed to generate reports.");
            return;
        }

        int selectedRow = ContestantTable.getSelectedRow();
        if (selectedRow != -1)
        {
            int ContestantNumber = (int) ContestantTable.getValueAt(selectedRow, 0);
            Contestant contestant = model.getContestantByNumber(ContestantNumber);
            if (contestant != null)
            {
                detailsTextArea.setText(contestant.getFullDetails());
            }
        }
    }

    public void shortReport()
    {
        StringBuilder sreport = new StringBuilder();
        Contestant winner = model.getWinner();
        if (winner != null)
        {
            sreport.append("Contestant with the highest overall score:\n");
            sreport.append(String.format("Contestant Number: %d, Name: %s, Overall Score: %.2f%n",
                    winner.getContestantNumber(), winner.getName(), winner.getOverallScore()));
            sreport.append("\n");
        }
        detailsTextArea.setText(sreport.toString());
    }

    public void addContestantFromForm()
    {

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField countryField = new JTextField();
        JTextField scoresField = new JTextField();

        // Create dropdown lists for Level and Category
        JComboBox<Contestant.Level> levelComboBox = new JComboBox<>(Contestant.Level.values());
        JComboBox<Contestant.Category> categoryComboBox = new JComboBox<>(Contestant.Category.values());

        // Create a panel for the form with labels and input fields
        JPanel formPanel = new JPanel(new GridLayout(8, 2));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Country:"));
        formPanel.add(countryField);
        formPanel.add(new JLabel("Level:"));
        formPanel.add(levelComboBox);  // Use dropdown list
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryComboBox);  // Use dropdown list
        formPanel.add(new JLabel("Scores (comma-separated, e.g., 1,2,3,4,5):"));
        formPanel.add(scoresField);

        int result = JOptionPane.showConfirmDialog(
                this, formPanel, "Add Contestant", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION)
        {
            // Get values from input fields and dropdown lists
            String name = nameField.getText();
            String ageText = ageField.getText();
            String gender = genderField.getText();
            String country = countryField.getText();
            String scoresText = scoresField.getText();

            // Validate age is a non-empty integer
            if (!isNonEmptyInteger(ageText))
            {
                JOptionPane.showMessageDialog(this, "Age must be a valid non-empty integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int age = Integer.parseInt(ageText);

            // Validate name is a non-empty string containing only letters
            if (!isNonEmptyStringWithLetters(name))
            {
                JOptionPane.showMessageDialog(this, "Name must be a non-empty string containing only letters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate country is a non-empty string containing only letters
            if (!isNonEmptyStringWithLetters(country))
            {
                JOptionPane.showMessageDialog(this, "Country must be a non-empty string containing only letters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int[] scores = Arrays.stream(scoresText.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Contestant.Level selectedLevel = (Contestant.Level) levelComboBox.getSelectedItem();
            Contestant.Category selectedCategory = (Contestant.Category) categoryComboBox.getSelectedItem();

            int ContestantNumber = model.getAllContestants().size() + 1;
            model.addContestant(ContestantNumber, name, selectedCategory, selectedLevel, scores, age, gender, country);
            updateContestantTable();
        }
    }

    private boolean isNonEmptyInteger(String str) {
        return !str.isEmpty() && isInteger(str);
    }

    private boolean isAlpha(String str) {
        return str.matches("^[a-zA-Z ]+$");
    }

    private boolean isNonEmptyStringWithLetters(String str) {
        return !str.isEmpty() && isAlpha(str);
    }

    private boolean isInteger(String str)
    {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }
}
