package denny.com.attendancetracker.contracts;

import android.content.Context;

import denny.com.attendancetracker.views.BaseView;

public interface InputBaseUrlContract {

    interface InputBaseUrlView extends BaseView {

        void success(String message);
    }

    interface Presenter{
        void checkServerConnection(String ipAddress);
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess(String message);
        }

        void doCheckServerConnection(Context context, String ipAddress, Callback callback);
    }
}
