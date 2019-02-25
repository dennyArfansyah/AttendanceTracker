package denny.com.attendancetracker.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import denny.com.attendancetracker.contracts.SignUpContract;
import denny.com.attendancetracker.models.Employee;

public class SignUpPresenterTest {

    @Mock
    Context context;

    @Mock
    SignUpContract.View view;

    @Mock
    SignUpContract.Repository repository;

    SignUpPresenter presenter;

    @Captor
    ArgumentCaptor<SignUpContract.Repository.Callback> argumentCaptor;
    @Captor
    ArgumentCaptor<Context> contextArgumentCaptor;

    private String typeYourRequiredField = "Please type your name, email, password and date of birth";
    private String email = "iam@you.com";
    private String password = "com";
    private String name = "I am great";
    private String dob = "1998-02-09";
    private String division = "IT";

    private Employee validEmployee = new Employee();
    private Employee invalidEmployee = new Employee("","","","","","","");

    @Captor
    ArgumentCaptor<Employee> employeeArgumentCaptor;

    private String success = "SignUp successfully";
    private String error = "SignUp failed";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new SignUpPresenter(context, view, repository);
        validEmployee.setEmail(email);
        validEmployee.setPassword(password);
        validEmployee.setDob(dob);
        validEmployee.setName(name);
        validEmployee.setDivision(division);
    }

    @Test
    public void nothingEmailPasswordType(){
        presenter.signUp(invalidEmployee);
        Mockito.verify(view).error(typeYourRequiredField);
    }

    @Test
    public void doSignUpWithNoInternetConnection(){
        presenter.signUp(validEmployee);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doSignUp(contextArgumentCaptor.capture(),
                employeeArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().noInternetConnection();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).noInternetConnection();
    }

    @Test
    public void doSignUpWithValidData(){
        presenter.signUp(validEmployee);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doSignUp(contextArgumentCaptor.capture(),
                employeeArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(success);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).success(success);
    }

    @Test
    public void doSignUpWithInvalidData(){
        presenter.signUp(validEmployee);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doSignUp(contextArgumentCaptor.capture(),
                employeeArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onError(error);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).error(error);
    }
}