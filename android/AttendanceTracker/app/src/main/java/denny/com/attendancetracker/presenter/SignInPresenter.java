package denny.com.attendancetracker.presenter;

import android.content.Context;

import denny.com.attendancetracker.contracts.SignInContract;
import denny.com.attendancetracker.models.Employee;

public class SignInPresenter implements SignInContract.Presenter {

    private Context context;
    private SignInContract.View view;
    private SignInContract.Repository repository;

    public SignInPresenter(Context context, SignInContract.View view, SignInContract.Repository repository) {
        this.context = context;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void signIn(String email, String password) {
        if(email.isEmpty() || password.isEmpty()){
            view.error("Please type your email and password");
            return;
        }

        view.showLoading();
        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setPassword(password);
        repository.doLogin(context, employee, new SignInContract.Repository.Callback() {
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
