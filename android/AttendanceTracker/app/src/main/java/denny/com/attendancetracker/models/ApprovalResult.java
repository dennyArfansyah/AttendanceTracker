package denny.com.attendancetracker.models;

import java.util.List;

public class ApprovalResult extends BaseResult {

    private int count;
    private List<Approval> data;

    public ApprovalResult(int count, List<Approval> data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Approval> getData() {
        return data;
    }

    public void setData(List<Approval> data) {
        this.data = data;
    }
}
