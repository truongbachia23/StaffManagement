package view;

import controller.AdminController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class SearchStaffForm extends JFrame {
    private JTable searchResultTable;
    private JButton searchButton;
    private JButton resetInputButton;
    private JTextField birthYearTextField;
    private JTextField nameTextField;
    private JTextField salaryFromTextField;
    private JTextField salaryToTextField;
    private JPanel searchPanel;
    private JButton editButton;
    private JButton deleteButton;

    private AdminController adminController;

    public SearchStaffForm(AdminController adminController) {

        this.adminController = adminController;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Search Staff Form");
        setContentPane(this.searchPanel);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);
        setTable(this.adminController.getStaffList());
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        resetInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetInputData();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStaff();
            }
        });

        nameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()== KeyEvent.VK_ENTER){
                    search();
                }
            }
        });
        salaryFromTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()== KeyEvent.VK_ENTER){
                    search();
                }
            }
        });
        salaryToTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()== KeyEvent.VK_ENTER){
                    search();
                }
            }
        });
        birthYearTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()== KeyEvent.VK_ENTER){
                    search();
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editStaff();
            }
        });
    }

    private void editStaff(){
        int[] selectedRows = searchResultTable.getSelectedRows();

        if(selectedRows.length == 0){
            JOptionPane.showMessageDialog(searchPanel, "Bạn chưa chọn nhân viên nào");
        } else if(selectedRows.length > 1){
            JOptionPane.showMessageDialog(searchPanel, "Bạn chỉ được phép chọn 1 nhân viên");
        } else {
            int staffId = Integer.valueOf(searchResultTable.getValueAt(selectedRows[0], 0).toString());
            new EditStaffFrom(this.adminController,staffId);
            disable();
        }
    }

    private void deleteStaff() {
        int[] selectedRows = searchResultTable.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(searchPanel, "Bạn chưa chọn nhân viên nào cả");
        } else {
            String warningMessage = "Bạn có chắc muốn xóa "+ ((selectedRows.length > 1) ? "các " : "") +"nhân viên ";
            for (int i = 0; i < selectedRows.length; i++) {
                int selectedRow = selectedRows[i];
                warningMessage += searchResultTable.getValueAt(selectedRow, 1).toString();
                if(i < selectedRows.length - 1) warningMessage += ", ";
            }
            warningMessage += " chứ?";
            if(JOptionPane.showConfirmDialog(searchPanel, warningMessage, "Warning!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                //do delete
                for (int i = 0; i < selectedRows.length; i++) {
                    int staffId = Integer.valueOf(searchResultTable.getValueAt(selectedRows[i], 0).toString());
                    this.adminController.deleteStaff(staffId);
                    System.out.println("Removed staff has id " + staffId);
                }
                //remove deleted row from table
                for (int i = selectedRows.length -1; i >= 0; i--) {
                    ((DefaultTableModel)searchResultTable.getModel()).removeRow(selectedRows[i]);
                }

                JOptionPane.showMessageDialog(searchPanel, "Xóa thành công");
            }

        }
    }

    private void resetInputData() {
        nameTextField.setText("");
        salaryFromTextField.setText("");
        salaryToTextField.setText("");
        birthYearTextField.setText("");
    }

    private void search() {
        String name = nameTextField.getText().trim();
        String salaryFrom = salaryFromTextField.getText().trim();
        String salaryTo = salaryToTextField.getText().trim();
        String birthYear = birthYearTextField.getText().trim();

        String error = this.adminController.isValidSearchInput(name, salaryFrom, salaryTo, birthYear);
        if (error.equals("")) {
            setTable(this.adminController.searchStaff(name, salaryFrom, salaryTo, birthYear));
            resetInputData();
        } else {
            JOptionPane.showMessageDialog(searchPanel, error);
        }
    }

    private void setTable(ArrayList<ArrayList<String>> data) {
        Object[][] os = new Object[][]{};
        if (data != null && data.get(0) != null) {
            os = new Object[data.size()][data.get(0).size()];
            for (int j = 0; j < data.size(); j++) {
                Object[] rowData = new Object[data.get(j).size()];
                for (int i = 0; i < rowData.length; i++) {
                    rowData[i] = data.get(j).get(i);
                }
                os[j] = rowData;
            }
        }
        searchResultTable.setModel(new javax.swing.table.DefaultTableModel(
                os,
                new String[]{
                        "ID", "Tên", "Ngày sinh", "Lương", "Giới tính", "Tình trạng hôn nhân"
                }
        ));
    }

}
