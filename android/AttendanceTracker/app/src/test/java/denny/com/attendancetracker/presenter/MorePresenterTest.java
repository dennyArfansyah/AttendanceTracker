package denny.com.attendancetracker.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import denny.com.attendancetracker.contracts.MoreContract;

public class MorePresenterTest {

    @Mock
    MoreContract.View view;

    @Mock
    MoreContract.Repository repository;

    MorePresenter presenter;

    @Captor
    ArgumentCaptor<MoreContract.Repository.Callback> argumentCaptor;

    private String failed = "Sorry, you cannot sign out";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MorePresenter(view, repository);
    }

    @Test
    public void signOutNoInternetConnection(){
        presenter.signOut();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doSignOut(argumentCaptor.capture());
        argumentCaptor.getValue().noInternetConnection();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).noInternetConnection();
    }

    @Test
    public void successSignOut(){
        presenter.signOut();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doSignOut(argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).success();
    }

    @Test
    public void failedSignOut(){
        presenter.signOut();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).doSignOut(argumentCaptor.capture());
        argumentCaptor.getValue().onError(failed);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).error(failed);
    }

}