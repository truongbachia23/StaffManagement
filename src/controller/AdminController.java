package controller;

import lib.MyDateTime;
import lib.MyNumber;
import model.Admin;
import model.Staff;

import java.util.ArrayList;
import java.util.Date;

public class AdminController {
    public Admin admin;

    public AdminController(String username, String password) {
        this.admin = new Admin(username, password);
    }

    public boolean login() {
        return this.admin.login();
    }

    private String isValidInput(String name, String birthday, String salary, String gender, boolean isMarried) {
        String error = "";
        if (name == null || name.length() < 2) error += "Invalid name\n";
        Date birthdayDate = MyDateTime.convertStringtoDate(birthday, "dd/MM/yyyy");
        if (birthdayDate == null || birthdayDate.getTime() >= new Date().getTime()) error += "Invalid birthday\n";
        if (!MyNumber.isInt(salary)) error += "Invalid salary\n";
        if (convertGender(gender) == null) error += "Invalid gender\n";
        return error.trim();
    }

    private String convertGender(String gender) {
        switch (gender) {
            case "Nam":
                return "male";
            case "Nữ":
                return "female";
            case "Khác":
                return "other";
            default:
                return null;
        }
    }

    private String reconvertGender(String gender) {
        switch (gender) {
            case "male":
                return "Nam";
            case "female":
                return "Nữ";
            case "other":
                return "Khác";
            default:
                return null;
        }
    }

    public String editStaff(int id, String name, String birthday, String salary, String gender, boolean isMarried) {
        //valid input
        String error = isValidInput(name, birthday, salary, gender, isMarried);
        if (error.equals("")) {
            Staff staff = new Staff();
            staff.setId(id);
            staff.setName(name);
            staff.setBirthday(birthday, "dd/MM/yyyy");
            staff.setGender(convertGender(gender));
            staff.setMarried(isMarried);
            staff.setSalary(Integer.valueOf(salary));
            if (!staff.save()) {
                error += "Can't edit staff " + name;
            }
        }
        return error.trim();
    }

    public String addStaff(String name, String birthday, String salary, String gender, boolean isMarried) {
        //valid input
        String error = isValidInput(name, birthday, salary, gender, isMarried);
        if (error.equals("")) {
            if (!this.admin.addStaff(name, MyDateTime.convertStringtoDate(birthday, "dd/MM/yyyy"), Integer.parseInt(salary), convertGender(gender), (isMarried ? "yes" : "no"))) {
                error += "Can't add staff " + name + "\n";
            }
        }
        return error.trim();
    }

    public ArrayList<ArrayList<String>> getStaffList() {
        return convertSqlDatatoTableData(this.admin.getStaffList());
    }

    private ArrayList<ArrayList<String>> convertSqlDatatoTableData(ArrayList<ArrayList<String>> staffList) {
        if (staffList == null || staffList.size() == 0 || staffList.get(0) == null) return null;
        for (int i = 0; i < staffList.size(); i++) {
            ArrayList<String> staff = staffList.get(i);
            staff.set(4, reconvertGender(staff.get(4)));
            staff.set(5, (staff.get(5).equals("yes") ? "Đã cưới" : "Độc thân"));
            staffList.set(i, staff);
        }
        return staffList;
    }

    public String isValidSearchInput(String name, String salaryFrom, String salaryTo, String birthday) {
        String error = "";
        if (!salaryFrom.equals("") && (!MyNumber.isInt(salaryFrom) || Integer.valueOf(salaryFrom) < 0))
            error += "Invalid Salary from\n";
        if (!salaryTo.equals("") && (!MyNumber.isInt(salaryTo) || Integer.valueOf(salaryTo) < 0))
            error += "Invalid Salary to\n";
        if (!birthday.equals("") && (!MyNumber.isInt(birthday) || birthday.length() != 4))
            error += "Invalid birth year\n";
        return error.trim();
    }

    public ArrayList<ArrayList<String>> searchStaff(String name, String salaryFrom, String salaryTo, String birthdayYear) {
        if (name.equals("")) name = null;
        if (salaryFrom.equals("")) salaryFrom = null;
        if (salaryTo.equals("")) salaryTo = null;
        ;
        if (birthdayYear.equals("")) birthdayYear = null;
        return convertSqlDatatoTableData(this.admin.searchStaff(name, salaryFrom, salaryTo, birthdayYear));
    }

    public void deleteStaff(int staffId) {
        this.admin.deleteStaff(staffId);
    }

    public ArrayList<ArrayList<String>> doStatistics1() {
        return convertSqlDatatoTableData(this.admin.doStatisticsType1());
    }

    public int doStatistics2() {
        try {
            return Integer.valueOf(this.admin.doStatisticsType2().get(0).get(0));
        } catch (Exception ex) {
            return -1;
        }
    }

    public double doStatistics3() {
        try {
            return Double.valueOf(this.admin.doStatisticsType3().get(0).get(0));
        } catch (Exception ex) {
            return -1;
        }
    }


}
