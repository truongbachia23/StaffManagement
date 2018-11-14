package view;

import controller.AdminController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStaffForm extends JFrame {
    private JButton resetButton;
    private JButton addButton;
    private JLabel nameLabel;
    private JLabel birthdayLabel;
    private JComboBox genderComboBox;
    private JRadioButton noRadioButton;
    private JRadioButton yesRadioButton;
    private JPanel addStaffPanel;
    private JTextField salaryTextField;
    private JTextField birthdayTextField;
    private JTextField nameTextField;


    private AdminController adminController;

    public AddStaffForm(AdminController adminController) {
        this.adminController = adminController;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Add Staff Form");
        setContentPane(this.addStaffPanel);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);
        noRadioButton.setSelected(true);
        yesRadioButton.setSelected(false);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStaff();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetInput();
            }
        });

        noRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                yesRadioButton.setSelected(!noRadioButton.isSelected());
            }
        });
        yesRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                noRadioButton.setSelected(!yesRadioButton.isSelected());
            }
        });
    }

    private void addStaff() {
        String name = nameTextField.getText();
        String birthday = birthdayTextField.getText();
        String salary = salaryTextField.getText();
        String gender = genderComboBox.getSelectedItem().toString();
        boolean isMarried = yesRadioButton.isSelected();

        String error = this.adminController.addStaff(name, birthday, salary, gender, isMarried);
        if(error.equals("")){
            JOptionPane.showMessageDialog(addStaffPanel, "Thêm thành công nhân viên " + name);
            resetInput();
        } else JOptionPane.showMessageDialog(addStaffPanel, error.trim());
    }
    private void resetInput(){
        nameTextField.setText("");
        birthdayTextField.setText("dd/MM/yyyy");
        salaryTextField.setText("");
        genderComboBox.setSelectedIndex(0);
        noRadioButton.setSelected(true);
        yesRadioButton.setSelected(false);
    }
}
