package denny.com.attendancetracker.models;

import io.realm.RealmObject;

public class Approval extends RealmObject {

    private String _id, employeeId, type, reason, since, until;
    private int status;

    public Approval(String _id, String employeeId, String type, String reason, int status, String since, String until) {
        this._id = _id;
        this.employeeId = employeeId;
        this.type = type;
        this.reason = reason;
        this.status = status;
        this.since = since;
        this.until = until;
    }

    public Approval(){}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }
}
