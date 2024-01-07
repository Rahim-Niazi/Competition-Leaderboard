import Controller.Controller;
import view.frontend;
import model.ContestantList;
import model.User;

import javax.swing.*;
import java.awt.*;

public class Manager
{
    private JFrame selectionFrame;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Manager().showSelectionPanel());
    }

    private void showSelectionPanel()
    {
        selectionFrame = new JFrame("Role Selection");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(2, 1));

        JButton staffButton = new JButton("Staff");
        JButton ContestantButton = new JButton("Contestant");

        staffButton.addActionListener(e -> openCompetitionGUI("staff"));
        ContestantButton.addActionListener(e -> openCompetitionGUI("Contestant"));

        selectionPanel.add(staffButton);
        selectionPanel.add(ContestantButton);

        selectionFrame.add(selectionPanel);
        selectionFrame.setSize(400, 400);
        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setVisible(true);
    }

}
