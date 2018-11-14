package view;

import controller.AdminController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffManagementForm extends JFrame {
    private JButton addButton;
    private JButton searchButton;
    private JButton statisticsButton;
    private JPanel functionsPanel;
    private JLabel welcomeMessage;

    private AdminController adminController;

    public StaffManagementForm(AdminController adminController) {
        this.adminController = adminController;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Staff Management Form");
        setContentPane(this.functionsPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);

        welcomeMessage.setText("Hello " + adminController.admin.getName());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddStaffForm();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchStaffForm();
            }
        });
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchStatisticsForm();
            }
        });
    }

    public void openAddStaffForm() {
        new AddStaffForm(this.adminController);
    }

    public void openSearchStaffForm() {
        new SearchStaffForm(this.adminController);
    }

    public void openSearchStatisticsForm() {
        new StatisticsForm(this.adminController);
    }
}
