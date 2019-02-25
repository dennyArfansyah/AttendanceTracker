package denny.com.attendancetracker.repositories;

import java.util.ArrayList;
import java.util.List;

import denny.com.attendancetracker.R;
import denny.com.attendancetracker.contracts.ServiceContract;
import denny.com.attendancetracker.models.Service;

public class ServiceRepository implements ServiceContract.Repository {

    @Override
    public void getAllServices(Callback callback) {
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service("1", "Leave Tracker", R.drawable.ic_leave));
        serviceList.add(new Service("2", "Time Tracker", R.drawable.ic_time));
        serviceList.add(new Service("3", "Attendance", R.drawable.ic_attendance));
        serviceList.add(new Service("4", "Files", R.drawable.ic_files));
        serviceList.add(new Service("5", "Organization", R.drawable.ic_organization));
        serviceList.add(new Service("6", "Announcement", R.drawable.ic_announcement));
        callback.onSuccess(serviceList);
    }
}
