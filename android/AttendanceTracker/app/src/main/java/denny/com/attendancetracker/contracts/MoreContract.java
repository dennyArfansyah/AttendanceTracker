package denny.com.attendancetracker.contracts;

import denny.com.attendancetracker.views.BaseView;

public interface MoreContract {

    interface View extends BaseView {

        void success();
    }

    interface Presenter{
        void signOut();
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess();
        }

        void doSignOut(Callback callback);
    }
}
