package model;

import lib.DB;
import lib.MyDateTime;

import java.util.ArrayList;
import java.util.Date;

public class Staff {
    private int id;
    private String name;
    private Date birthday;
    private int salary;
    private String gender;//male, female, other
    private boolean isMarried;//yes, no

    private DB db;

   /* public static void main(String[] args){
        Staff staff = new Staff(1);
        staff.setBirthday("20/5/2000", "dd/MM/yyyy");
        staff.save();
        staff = new Staff(1);
        System.out.println(staff.getName());
        System.out.println(staff.getBirthday());
    }*/

    public Staff(){
        this.db = new DB();
    }
    public Staff(int id){
        this.id = id;
        this.db = new DB();

        if(this.db.select("id, name, birthday, salary, gender, is_married", "staff", "id = " + id)){
            ArrayList<ArrayList<String>> result = this.db.getResult();
            this.name = result.get(0).get(1);
            this.birthday = MyDateTime.convertStringtoDate(result.get(0).get(2), "yyyy-MM-dd");
            this.salary = Integer.valueOf(result.get(0).get(3));
            this.gender = result.get(0).get(4);
            this.isMarried = "yes".equalsIgnoreCase(result.get(0).get(5)) ;
        } else {
            System.out.println("Can't find this staff has id = " + id);
        }
    }

    public boolean save(){
        if(this.id > 0){
            String birthdaySql = MyDateTime.convertDatetoString(this.birthday,"yyyy-MM-dd");
            String salaryStr = (salary > 0) ? String.valueOf(salary) : null;
            String isMarriedStr = isMarried ? "yes" : "no";
            return this.db.inodate("staff", new String[]{"id", "name", "birthday","salary","gender","is_married"},
                    new String[]{String.valueOf(id), name,birthdaySql, salaryStr,gender, isMarriedStr}, "id = " + id);
        } else {
            System.out.println("Invalid id");
            return false;
        }
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(String birthday, String format) {
        Date birthdayDate = MyDateTime.convertStringtoDate(birthday, format);
        if(birthdayDate != null){
            this.birthday = birthdayDate;
        } else {
            System.out.println("Invalid datetime format");
        }
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getGender() {
        return gender;
    }
    public int getGenderInt() {
        switch (gender){
            case "male" : return 0;
            case "female" : return 1;
            case "other" : return 2;
        }
        return -1;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }
}
