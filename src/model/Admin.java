package model;


import lib.DB;
import lib.MyDateTime;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

public class Admin {
    private int id;
    private String name;
    private String username;
    private String password;

    private DB db;

    public Admin(String username, String password) {
        //encrypt md5 password
        try {
            byte[] bytesOfPassword = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfPassword);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < thedigest.length; ++i) {
                sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1,3));
            }
            this.password = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }

        this.username = username;
        this.db = new DB();
    }

    public boolean login(){
        if(db.select("id,name", "admin", "username = \"" + DB.validSql(this.username) + "\" AND password = \"" + DB.validSql(this.password) + "\"")){
            ArrayList<ArrayList<String>> result = db.getResult();
            this.id = Integer.valueOf(result.get(0).get(0));
            this.name = result.get(0).get(1);
            return true;
        } else return false;
    }
    public boolean addStaff(String name, Date birthday, int salary, String gender, String isMarried){
       return (db.insert("staff", new String[]{"name","birthday","salary","gender","is_married"}, new String[]{name, MyDateTime.convertDatetoString(birthday,"yyyy-MM-dd"), String.valueOf(salary), gender, isMarried}));
    }

    public ArrayList<ArrayList<String>> getStaffList(){
        if(db.select("id, name, DATE_FORMAT(birthday, \"%d/%m/%Y\"), salary, gender, is_married", "staff", "1 ORDER BY id ASC")){
            return db.getResult();
        } else return null;
    }
    public ArrayList<ArrayList<String>> doStatisticsType1(){
        if(db.select("id, name, DATE_FORMAT(birthday, \"%d/%m/%Y\"), salary, gender, is_married", "staff", "1 ORDER BY salary DESC")){
            return db.getResult();
        } else return null;
    }
    public ArrayList<ArrayList<String>> doStatisticsType2(){
        if(db.select("sum(salary)", "staff", "1")){
            return db.getResult();
        } else return null;
    }
    public ArrayList<ArrayList<String>> doStatisticsType3(){
        if(db.select("sum(salary)/count(id)", "staff", "1 ORDER BY salary DESC")){
            return db.getResult();
        } else return null;
    }
    public ArrayList<ArrayList<String>> searchStaff(String name, String salaryFrom, String salaryTo, String birthdayYear){
        String condition = "1";
        if(name != null) condition += " AND name like '%" + DB.validSql(name) + "%'";
        if(salaryFrom != null) condition += " AND salary >= " + salaryFrom;
        if(salaryTo != null) condition += " AND salary <= " + salaryTo;
        if(birthdayYear != null) condition += " AND YEAR(birthday) = '" + birthdayYear + "'";
        condition = condition.trim();
        if(db.select("id, name, birthday, salary, gender, is_married", "staff", condition + " ORDER BY date_created ASC")){
            return db.getResult();
        } else return null;
    }
    public void deleteStaff(int staffId){
        this.db.execute("DELETE FROM staff WHERE id = " + staffId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
