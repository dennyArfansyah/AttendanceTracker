package denny.com.attendancetracker.models;

public class Feed {

    private String _id, image, content, date;

    public Feed(String id, String image, String content, String date) {
        this._id = id;
        this.image = image;
        this.content = content;
        this.date = date;
    }

    public Feed(){}

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
