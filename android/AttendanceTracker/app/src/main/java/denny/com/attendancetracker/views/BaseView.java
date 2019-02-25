package denny.com.attendancetracker.views;

public interface BaseView {

    void showLoading();
    void noInternetConnection();
    void hideLoading();
    void error(String errorMessage);
}
