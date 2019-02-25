package denny.com.attendancetracker.presenter;

import android.content.Context;

import java.util.List;

import denny.com.attendancetracker.contracts.ApprovalContract;
import denny.com.attendancetracker.models.Approval;

public class ApprovalPresenter implements ApprovalContract.Presenter {

    private Context context;
    private ApprovalContract.View view;
    private ApprovalContract.Repository repository;

    public ApprovalPresenter(Context context, ApprovalContract.View view, ApprovalContract.Repository repository) {
        this.context = context;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getAllApprovals() {
        view.showLoading();
        repository.allApprovals(context, new ApprovalContract.Repository.Callback() {
            @Override
            public void onSuccess(List<Approval> approvalList) {
                view.hideLoading();
                view.success(approvalList);
            }

            @Override
            public void noInternetConnection() {
                view.hideLoading();
                view.noInternetConnection();
            }

            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.error(errorMessage);
            }
        });
    }

    @Override
    public void getAllApprovalsBasedOnStatus(int status) {
        repository.allApprovalsBasedOnStatus(status, new ApprovalContract.Repository.Callback() {
            @Override
            public void onSuccess(List<Approval> approvalList) {
                view.success(approvalList);
            }

            @Override
            public void noInternetConnection() {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
