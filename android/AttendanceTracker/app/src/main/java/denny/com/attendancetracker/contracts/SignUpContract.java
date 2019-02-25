package denny.com.attendancetracker.contracts;

import android.content.Context;

import denny.com.attendancetracker.models.Employee;
import denny.com.attendancetracker.views.BaseView;

public interface SignUpContract {

    interface View extends BaseView {

        void success(String message);
    }

    interface Presenter{
        void signUp(Employee employee);
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess(String message);
        }

        void doSignUp(Context context, Employee employee, Callback callback);
    }
}
