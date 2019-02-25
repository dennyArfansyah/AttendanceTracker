package denny.com.attendancetracker.presenter;

import denny.com.attendancetracker.contracts.MoreContract;

public class MorePresenter implements MoreContract.Presenter {

    private MoreContract.View view;
    private MoreContract.Repository repository;

    public MorePresenter(MoreContract.View view, MoreContract.Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void signOut() {
        view.showLoading();
        repository.doSignOut(new MoreContract.Repository.Callback() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                view.success();
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
