package denny.com.attendancetracker.presenter;

import android.content.Context;

import denny.com.attendancetracker.contracts.SignUpContract;
import denny.com.attendancetracker.models.Employee;

public class SignUpPresenter implements SignUpContract.Presenter {

    private Context context;
    private SignUpContract.View view;
    private SignUpContract.Repository repository;

    public SignUpPresenter(Context context, SignUpContract.View view, SignUpContract.Repository repository) {
        this.context = context;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void signUp(Employee employee) {
        if(employee.getName().isEmpty() || employee.getEmail().isEmpty() ||
            employee.getPassword().isEmpty() || employee.getDob().isEmpty()){
            view.error("Please type your name, email, password and date of birth");
            return;
        }

        view.showLoading();
        repository.doSignUp(context, employee, new SignUpContract.Repository.Callback() {
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
