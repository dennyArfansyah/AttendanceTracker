package denny.com.attendancetracker.models;

import java.util.List;

public class FeedResult extends BaseResult {

    private int count;
    private List<Feed> data;

    public FeedResult(int count, List<Feed> data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Feed> getData() {
        return data;
    }

    public void setData(List<Feed> data) {
        this.data = data;
    }
}
