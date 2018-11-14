package view;

import controller.AdminController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StatisticsForm extends JFrame{
    private JComboBox statisticsType;
    private JTable statisticsTable;
    private JPanel statisticsPanel;
    private JButton statisticsButton;

    private AdminController adminController;

    public StatisticsForm(AdminController adminController){
        this.adminController = adminController;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Statistics Form");
        setContentPane(this.statisticsPanel);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doStatistics();
            }
        });
    }

    private void doStatistics(){
        switch (statisticsType.getSelectedIndex()){
            case 0: {
                ArrayList<ArrayList<String>> result = this.adminController.doStatistics1();
                if(result == null || result.size() == 0){
                    JOptionPane.showMessageDialog(statisticsPanel, "Can;t get this statistic");
                } else {
                    setTableType1(result);
                }
                break;
            }
            case 1: {
                int result = this.adminController.doStatistics2();
                if(result == -1 ){
                    JOptionPane.showMessageDialog(statisticsPanel, "Can;t get this statistic");
                } else {
                    Object[][] os = new Object[1][1];
                    Object[] rowData = new Object[1];
                    rowData[0] = result;
                    os[0] = rowData;
                    statisticsTable.setModel(new javax.swing.table.DefaultTableModel(
                            os,
                            new String[]{
                                    "Tổng lương phải trả"
                            }
                    ));
                }
                break;
            }
            case 2: {
                double result = this.adminController.doStatistics3();
                if(result == -1 ){
                    JOptionPane.showMessageDialog(statisticsPanel, "Can't get this statistic");
                } else {
                    Object[][] os = new Object[1][1];
                    Object[] rowData = new Object[1];
                    rowData[0] = result;
                    os[0] = rowData;
                    statisticsTable.setModel(new javax.swing.table.DefaultTableModel(
                            os,
                            new String[]{
                                    "Lương trung bình mỗi nhân viên"
                            }
                    ));
                break;
            }
        }

    }
    }
    private void setTableType1(ArrayList<ArrayList<String>> data) {
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
        statisticsTable.setModel(new javax.swing.table.DefaultTableModel(
                os,
                new String[]{
                        "ID", "Tên", "Ngày sinh", "Lương", "Giới tính", "Tình trạng hôn nhân"
                }
        ));
    }
}

