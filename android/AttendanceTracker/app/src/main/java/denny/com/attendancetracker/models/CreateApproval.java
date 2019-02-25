package denny.com.attendancetracker.models;

public class CreateApproval {

    private String employeeId, type, reason, since, until;
    private int status;

    public CreateApproval(String employeeId, String type, String reason, String since, String until, int status) {
        this.employeeId = employeeId;
        this.type = type;
        this.reason = reason;
        this.since = since;
        this.until = until;
        this.status = status;
    }

    public CreateApproval(){}

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
