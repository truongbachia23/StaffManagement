package view;

import controller.AdminController;
import lib.MyDateTime;
import model.Staff;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditStaffFrom extends JFrame{
    private JComboBox genderComboBox;
    private JRadioButton noRadioButton;
    private JRadioButton yesRadioButton;
    private JTextField salaryTextField;
    private JTextField birthdayTextField;
    private JTextField nameTextField;
    private JLabel nameLabel;
    private JLabel birthdayLabel;
    private JButton resetButton;
    private JButton addButton;
    private JPanel editStaffPanel;
    private JTextField idTextField;

    private AdminController adminController;
    private Staff staff;

    public EditStaffFrom(AdminController adminController, int staffId) {
        this.adminController = adminController;
        this.staff = new Staff(staffId);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(staff.getName());
        setContentPane(this.editStaffPanel);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);
        noRadioButton.setSelected(true);
        yesRadioButton.setSelected(false);

        idTextField.setText(String.valueOf(staff.getId()));
        idTextField.setEditable(false);
        nameTextField.setText(staff.getName());
        birthdayTextField.setText(MyDateTime.convertDatetoString(staff.getBirthday(), "dd/MM/yyyy"));
        salaryTextField.setText(String.valueOf(staff.getSalary()));
        genderComboBox.setSelectedIndex(staff.getGenderInt());

        yesRadioButton.setSelected(staff.isMarried());
        noRadioButton.setSelected(!staff.isMarried());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editStaff();
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
    private void editStaff(){
        int id = Integer.valueOf(idTextField.getText());
        String name = nameTextField.getText();
        String birthday = birthdayTextField.getText();
        String salary = salaryTextField.getText();
        String gender = genderComboBox.getSelectedItem().toString();
        boolean isMarried = yesRadioButton.isSelected();

        String error = this.adminController.editStaff(id, name, birthday, salary, gender, isMarried);
        if(error.equals("")){
            JOptionPane.showMessageDialog(editStaffPanel, "Sửa thành công nhân viên " + name);
        } else JOptionPane.showMessageDialog(editStaffPanel, error.trim());
    }
}
