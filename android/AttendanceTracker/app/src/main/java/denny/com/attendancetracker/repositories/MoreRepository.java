package denny.com.attendancetracker.repositories;

import denny.com.attendancetracker.contracts.MoreContract;
import denny.com.attendancetracker.utils.DatabaseHelper;

public class MoreRepository implements MoreContract.Repository {

    @Override
    public void doSignOut(Callback callback) {
        DatabaseHelper.deleteEmployee();
        callback.onSuccess();
    }
}
