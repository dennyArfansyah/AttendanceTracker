package denny.com.attendancetracker.contracts;

import android.content.Context;

import java.util.List;

import denny.com.attendancetracker.models.Feed;
import denny.com.attendancetracker.views.BaseView;

public interface FeedContract {

    interface View extends BaseView {

        void success(List<Feed> feedList);
    }

    interface Presenter{
        void getAllFeeds();
    }

    interface Repository{

        interface Callback extends BaseCallback{
            void onSuccess(List<Feed> feedList);
        }

        void allFeeds(Context context, Callback callback);
    }
}
