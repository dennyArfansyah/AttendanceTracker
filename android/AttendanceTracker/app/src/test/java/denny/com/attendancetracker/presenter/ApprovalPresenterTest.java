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

import denny.com.attendancetracker.contracts.ApprovalContract;
import denny.com.attendancetracker.models.Approval;

public class ApprovalPresenterTest {

    @Mock
    Context context;

    @Mock
    ApprovalContract.View view;

    @Mock
    ApprovalContract.Repository repository;

    ApprovalPresenter presenter;

    @Captor
    ArgumentCaptor<ApprovalContract.Repository.Callback> argumentCaptor;

    @Captor
    ArgumentCaptor<Context> contextArgumentCaptor;

    @Captor
    ArgumentCaptor<Integer> statusArgumentCaptor;

    private List<Approval> feedList = Arrays.asList(new Approval(), new Approval(), new Approval());

    private String failed = "Sorry, you have failed to get the data";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ApprovalPresenter(context, view, repository);
    }

    @Test
    public void getAllWithNoInternetConnection(){
        presenter.getAllApprovals();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).allApprovals(contextArgumentCaptor.capture(),
                argumentCaptor.capture());
        argumentCaptor.getValue().noInternetConnection();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).noInternetConnection();
    }

    @Test
    public void successGetAllApprovals(){
        presenter.getAllApprovals();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).allApprovals(contextArgumentCaptor.capture(),
                argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(feedList);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).success(feedList);
    }

    @Test
    public void successGetAllApprovalBasedOnStatus(){
        presenter.getAllApprovalsBasedOnStatus(1);
        Mockito.verify(repository).allApprovalsBasedOnStatus(statusArgumentCaptor.capture(),
                argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(feedList);
        Mockito.verify(view).success(feedList);
    }

    @Test
    public void failedGetAllApprovals(){
        presenter.getAllApprovals();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).allApprovals(contextArgumentCaptor.capture(),
                argumentCaptor.capture());
        argumentCaptor.getValue().onError(failed);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).error(failed);
    }
}