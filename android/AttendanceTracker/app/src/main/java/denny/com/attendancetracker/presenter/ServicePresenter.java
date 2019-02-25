package denny.com.attendancetracker.presenter;

import java.util.List;

import denny.com.attendancetracker.contracts.ServiceContract;
import denny.com.attendancetracker.models.Service;

public class ServicePresenter implements ServiceContract.Presenter {

    private ServiceContract.View view;
    private ServiceContract.Repository repository;

    public ServicePresenter(ServiceContract.View view, ServiceContract.Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getAllServices() {
        repository.getAllServices(new ServiceContract.Repository.Callback() {
            @Override
            public void onSuccess(List<Service> serviceList) {
                view.success(serviceList);
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
