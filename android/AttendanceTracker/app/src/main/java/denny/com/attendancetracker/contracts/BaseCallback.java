package denny.com.attendancetracker.contracts;

public interface BaseCallback {
    void noInternetConnection();
    void onError(String errorMessage);
}
