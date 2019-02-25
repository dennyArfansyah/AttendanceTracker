package denny.com.attendancetracker.contracts;

import android.content.Context;

import java.util.List;

import denny.com.attendancetracker.models.Approval;
import denny.com.attendancetracker.views.BaseView;

public interface ApprovalContract {

    interface View extends BaseView {

        void success(List<Approval> approvalList);
    }

    interface Presenter{
        void getAllApprovals();
        void getAllApprovalsBasedOnStatus(int status);
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess(List<Approval> approvalList);
        }

        void allApprovals(Context context, Callback callback);
        void allApprovalsBasedOnStatus(int status, Callback callback);
    }
}
