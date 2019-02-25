package denny.com.attendancetracker.contracts;

import android.content.Context;

import denny.com.attendancetracker.models.CreateApproval;
import denny.com.attendancetracker.views.BaseView;

public interface CreateApprovalContract {

    interface View extends BaseView {

        void success(String message);
    }

    interface Presenter{
        void applyApproval(CreateApproval approval);
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess(String message);
        }

        void doApplyApproval(Context context, CreateApproval approval, Callback callback);
    }
}
