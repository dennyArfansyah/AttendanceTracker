package denny.com.attendancetracker.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import denny.com.attendancetracker.contracts.InputBaseUrlContract;

public class InputBaseUrlPresenterTest {

    @Mock
    Context context;

    @Mock
    InputBaseUrlContract.InputBaseUrlView view;

    @Mock
    InputBaseUrlContract.Repository repository;

    InputBaseUrlPresenter presenter;

    private String validIpAddress = "192.168.100.26";
    private String invalidIpAddress = "192.168.0.26";

    @Captor
    ArgumentCaptor<String> ipAddressArgumentCaptor;

    @Captor
    ArgumentCaptor<InputBaseUrlContract.Repository.Callback> argumentCaptor;

    @Captor
    ArgumentCaptor<Context> contextArgumentCaptor;

    private String typeYourIpAddress = "Please, type your ip address";
    private String congratulation = "Congratulation";
    private String errorMessage = "Sorry";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new InputBaseUrlPresenter(context, view, repository);
    }

    @Test
    public void nothingIpAddressType(){
        presenter.checkServerConnection("");
        Mockito.verify(view).error(typeYourIpAddress);
    }

    @Test
    public void checkServerConnectionWithNoInternetConnection(){
        presenter.checkServerConnection(validIpAddress);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doCheckServerConnection(contextArgumentCaptor.capture(),
                ipAddressArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().noInternetConnection();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).noInternetConnection();
    }

    @Test
    public void checkServerConnectionWithValidIpAddress(){
        presenter.checkServerConnection(validIpAddress);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doCheckServerConnection(contextArgumentCaptor.capture(),
                ipAddressArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(congratulation);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).success(congratulation);
    }

    @Test
    public void checkServerConnectionWithInvalidIpAddress(){
        presenter.checkServerConnection(invalidIpAddress);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doCheckServerConnection(contextArgumentCaptor.capture(),
                ipAddressArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onError(errorMessage);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).error(errorMessage);
    }

}