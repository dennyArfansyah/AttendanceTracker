package denny.com.attendancetracker.presenter;

import android.content.Context;

import denny.com.attendancetracker.contracts.InputBaseUrlContract;

public class InputBaseUrlPresenter implements InputBaseUrlContract.Presenter{

    private Context context;
    private InputBaseUrlContract.InputBaseUrlView view;
    private InputBaseUrlContract.Repository repository;

    public InputBaseUrlPresenter(Context context, InputBaseUrlContract.InputBaseUrlView view,
                                 InputBaseUrlContract.Repository repository) {
        this.context = context;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void checkServerConnection(String ipAddress) {
        if(ipAddress.isEmpty()){
            view.error("Please, type your ip address");
            return;
        }
        view.showLoading();
        repository.doCheckServerConnection(context, ipAddress, new InputBaseUrlContract.Repository.Callback() {

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
