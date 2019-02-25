package denny.com.attendancetracker.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import denny.com.attendancetracker.contracts.CreateApprovalContract;
import denny.com.attendancetracker.models.CreateApproval;

public class CreateApprovalPresenterTest {

    @Mock
    Context context;

    @Mock
    CreateApprovalContract.View view;

    @Mock
    CreateApprovalContract.Repository repository;

    CreateApprovalPresenter presenter;

    @Captor
    ArgumentCaptor<CreateApprovalContract.Repository.Callback> argumentCaptor;
    @Captor
    ArgumentCaptor<Context> contextArgumentCaptor;


    private String typeYourRequiredField = "Please choose your leave type, type your reson and chooese your since and untill leave duration";

    private CreateApproval invalidCreateApproval = new CreateApproval("","","","","",0);
    private CreateApproval createApproval;

    @Captor
    ArgumentCaptor<CreateApproval> createApprovalArgumentCaptor;

    private String success = "SignUp successfully";
    private String error = "SignUp failed";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CreateApprovalPresenter(context, view, repository);
        createApproval = new CreateApproval();
        createApproval.setEmployeeId("5c6e4ddc76e2d6147e4a38c4");
        createApproval.setReason("Just wanna leaves");
        createApproval.setSince("2019-03-01");
        createApproval.setUntil("2019-03-06");
        createApproval.setStatus(0);
        createApproval.setType("Annual");
    }

    @Test
    public void nothingEmailPasswordType(){
        presenter.applyApproval(invalidCreateApproval);
        Mockito.verify(view).error(typeYourRequiredField);
    }

    @Test
    public void creatingApprovalWithNoInternetConnection(){
        presenter.applyApproval(createApproval);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doApplyApproval(contextArgumentCaptor.capture(),
                createApprovalArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().noInternetConnection();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).noInternetConnection();
    }

    @Test
    public void creatingApprovalWithValidData(){
        presenter.applyApproval(createApproval);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doApplyApproval(contextArgumentCaptor.capture(),
                createApprovalArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(success);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).success(success);
    }

    @Test
    public void creatingApprovalInvalidData(){
        presenter.applyApproval(createApproval);
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doApplyApproval(contextArgumentCaptor.capture(),
                createApprovalArgumentCaptor.capture(), argumentCaptor.capture());
        argumentCaptor.getValue().onError(error);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).error(error);
    }
}