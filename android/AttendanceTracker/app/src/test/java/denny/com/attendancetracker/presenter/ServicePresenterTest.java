package denny.com.attendancetracker.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import denny.com.attendancetracker.contracts.ServiceContract;
import denny.com.attendancetracker.models.Service;

public class ServicePresenterTest {

    @Mock
    Context context;

    @Mock
    ServiceContract.View view;

    @Mock
    ServiceContract.Repository repository;

    ServicePresenter presenter;

    @Captor
    ArgumentCaptor<ServiceContract.Repository.Callback> argumentCaptor;

    private List<Service> serviceList = Arrays.asList(new Service(), new Service(), new Service());

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ServicePresenter(view, repository);
    }

    @Test
    public void getAllServices(){
        presenter.getAllServices();
        Mockito.verify(repository).getAllServices(argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(serviceList);
        Mockito.verify(view).success(serviceList);
    }

}