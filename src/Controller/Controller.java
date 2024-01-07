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
}