package denny.com.attendancetracker.models;

import io.realm.RealmObject;

public class Employee extends RealmObject {

    private String _id, email, password, name, dob, division, token;

    public Employee(String _id, String email, String password, String name, String dob, String division, String token) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.division = division;
        this.token = token;
    }

    public Employee(){}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
