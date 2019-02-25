package denny.com.attendancetracker.presenter;

import android.content.Context;

import denny.com.attendancetracker.contracts.CreateApprovalContract;
import denny.com.attendancetracker.models.CreateApproval;

public class CreateApprovalPresenter implements CreateApprovalContract.Presenter {

    private Context context;
    private CreateApprovalContract.View view;
    private CreateApprovalContract.Repository repository;

    public CreateApprovalPresenter(Context context, CreateApprovalContract.View view, CreateApprovalContract.Repository repository) {
        this.context = context;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void applyApproval(CreateApproval approval) {
        if(approval.getType().isEmpty() || approval.getReason().isEmpty()
            || approval.getSince().isEmpty() || approval.getUntil().isEmpty()){
            view.error("Please choose your leave type, type your reson and chooese your since and untill leave duration");
            return;
        }

        view.showLoading();
        repository.doApplyApproval(context, approval, new CreateApprovalContract.Repository.Callback() {
            @Override
            public void onSuccess(String message) {
                view.hideLoading();
                view.success(message);
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
}
