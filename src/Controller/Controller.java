package Controller;
import view.frontend;
import model.ContestantList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller
{
    private frontend view;
    private ContestantList model;
    public Controller(frontend view, ContestantList model)
    {
        this.view = view;
        this.model = model;

        view.getUpdateButton().addActionListener(new UpdateButtonListener());
        view.getEditButton().addActionListener(new EditButtonListener());
        view.getRemoveButton().addActionListener(new RemoveButtonListener());
        view.getReportButton().addActionListener(new ReportButtonListener());
        view.getShortButton().addActionListener(new ShortButtonListener());
        view.getAddContestantButton().addActionListener(new AddContestantButtonListener());
        view.getSearchButton().addActionListener(new SearchButtonListener());
    }

    private class UpdateButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.updateContestantTable();
        }
    }

    private class EditButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.editContestantDetails();
        }
    }

    private class RemoveButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.removeContestant();
        }
    }

    private class ReportButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.generateReport();
        }
    }

    private class ShortButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.shortReport();
        }
    }

    private class AddContestantButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.addContestantFromForm();
        }
    }

    private class SearchButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.searchContestant();
        }
    }
}