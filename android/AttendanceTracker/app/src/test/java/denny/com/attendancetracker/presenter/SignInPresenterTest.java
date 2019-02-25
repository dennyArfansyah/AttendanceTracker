package denny.com.attendancetracker.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import denny.com.attendancetracker.contracts.SignInContract;
import denny.com.attendancetracker.models.Employee;

public class SignInPresenterTest {

    @Mock
    Context context;

    @Mock
    SignInContract.View view;

    @Mock
    SignInContract.Repository repository;

    SignInPresenter presenter;

    private String typeYourEmailPassword = "Please type your email and password";
    private String email = "denny@deni.com";
    private String password = "asd";

    @Captor
    ArgumentCaptor<SignInContract.Repository.Callback> argumentCaptor;

    @Captor
    ArgumentCaptor<Context> contextArgumentCaptor;

    private Employee employee = new Employee();

    @Captor
    ArgumentCaptor<Employee> employeeArgumentCaptor;

    private String success = "Sigin Successfull";
    private String failed = "Authorization Failed";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new SignInPresenter(context, view, repository);
        employee.setEmail(email);
        employee.setPassword(password);
    }

    @Test
    public void nothingEmailPasswordType(){
        presenter.signIn("", "");
        Mockito.verify(view).error(typeYourEmailPassword);
    }

    @Test
    public void doSignInWithNoInternetConnection(){
        presenter.signIn(email, password);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doLogin(contextArgumentCaptor.capture(),
                employeeArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().noInternetConnection();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).noInternetConnection();
    }

    @Test
    public void doSignInWithValidEmailAddress(){
        presenter.signIn(email, password);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doLogin(contextArgumentCaptor.capture(),
                employeeArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(success);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).success(success);
    }

    @Test
    public void doSignInWithInvalidEmailAddress(){
        presenter.signIn(email, password);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doLogin(contextArgumentCaptor.capture(),
                employeeArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onError(failed);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).error(failed);
    }
}