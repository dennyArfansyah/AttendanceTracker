package denny.com.attendancetracker.models;

import io.realm.RealmObject;

public class LeaveType extends RealmObject {

    private String id, name;

    public LeaveType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public LeaveType(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
