package denny.com.attendancetracker.contracts;

import android.content.Context;

import denny.com.attendancetracker.models.Employee;
import denny.com.attendancetracker.views.BaseView;

public interface SignInContract {

    interface View extends BaseView {

        void success(String message);
    }

    interface Presenter{
        void signIn(String email, String password);
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess(String message);
        }

        void doLogin(Context context, Employee employee, Callback callback);
    }
}
