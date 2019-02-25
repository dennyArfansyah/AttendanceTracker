package denny.com.attendancetracker.contracts;

import java.util.List;

import denny.com.attendancetracker.models.Service;
import denny.com.attendancetracker.views.BaseView;

public interface ServiceContract {

    interface View extends BaseView {
        void success(List<Service> serviceList);
    }

    interface Presenter{
        void getAllServices();
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess(List<Service> serviceList);
        }

        void getAllServices(Callback callback);
    }
}
