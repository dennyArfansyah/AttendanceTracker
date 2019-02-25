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

import denny.com.attendancetracker.contracts.FeedContract;
import denny.com.attendancetracker.models.Feed;

public class FeedPresenterTest {

    @Mock
    Context context;

    @Mock
    FeedContract.View view;

    @Mock
    FeedContract.Repository repository;

    FeedPresenter presenter;

    @Captor
    ArgumentCaptor<FeedContract.Repository.Callback> argumentCaptor;

    @Captor
    ArgumentCaptor<Context> contextArgumentCaptor;

    private List<Feed> feedList = Arrays.asList(new Feed(), new Feed(), new Feed());

    private String failed = "Sorry, you have failed to get the data";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new FeedPresenter(context, view, repository);
    }

    @Test
    public void getAllWithNoInternetConnection(){
        presenter.getAllFeeds();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).allFeeds(contextArgumentCaptor.capture(),
                argumentCaptor.capture());
        argumentCaptor.getValue().noInternetConnection();
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).noInternetConnection();
    }

    @Test
    public void successGetAllFeed(){
        presenter.getAllFeeds();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).allFeeds(contextArgumentCaptor.capture(),
                argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(feedList);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).success(feedList);
    }

    @Test
    public void failedGetAllFeed(){
        presenter.getAllFeeds();
        Mockito.verify(view).showLoading();
        Mockito.verify(repository).allFeeds(contextArgumentCaptor.capture(),
                argumentCaptor.capture());
        argumentCaptor.getValue().onError(failed);
        Mockito.verify(view).hideLoading();
        Mockito.verify(view).error(failed);
    }
}