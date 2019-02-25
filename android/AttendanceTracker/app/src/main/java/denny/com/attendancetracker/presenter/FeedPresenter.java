package denny.com.attendancetracker.presenter;

import android.content.Context;

import java.util.List;

import denny.com.attendancetracker.contracts.FeedContract;
import denny.com.attendancetracker.models.Feed;

public class FeedPresenter implements FeedContract.Presenter {

    private Context context;
    private FeedContract.View view;
    private FeedContract.Repository repository;

    public FeedPresenter(Context context, FeedContract.View view, FeedContract.Repository repository) {
        this.context = context;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getAllFeeds() {
        view.showLoading();
        repository.allFeeds(context, new FeedContract.Repository.Callback() {
            @Override
            public void onSuccess(List<Feed> feedList) {
                view.hideLoading();
                view.success(feedList);
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
