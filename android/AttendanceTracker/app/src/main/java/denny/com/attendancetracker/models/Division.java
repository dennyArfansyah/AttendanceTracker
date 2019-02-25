package denny.com.attendancetracker.models;

import io.realm.RealmObject;

public class Division extends RealmObject {

    private String id, name;

    public Division(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Division(){}

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
