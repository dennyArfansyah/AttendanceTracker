package denny.com.attendancetracker;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import denny.com.attendancetracker.adapter.FeedAdapter;
import denny.com.attendancetracker.contracts.FeedContract;
import denny.com.attendancetracker.models.Feed;
import denny.com.attendancetracker.presenter.FeedPresenter;
import denny.com.attendancetracker.repositories.FeedRepository;
import denny.com.attendancetracker.utils.Helper;

public class FeedsActivity extends BaseActivity implements FeedContract.View {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.textViewNoData)
    TextView textViewNoData;

    private FeedPresenter presenter;
    private FeedAdapter adapter;
    private List<Feed> feedList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_feeds;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_feeds;
    }

    @Override
    public void initContentView() {
        presenter = new FeedPresenter(this, this, new FeedRepository());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        feedList = new ArrayList<>();
        adapter = new FeedAdapter(feedList);
        recyclerView.setAdapter(adapter);

        presenter.getAllFeeds();
    }

    @Override
    public void initHeaderView() {
        textViewTitleMenu.setText(getResources().getString(R.string.feeds));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void success(List<Feed> feedList) {
        this.feedList.clear();
        if(feedList.size() == 0){
            textViewNoData.setVisibility(View.VISIBLE);
        }else {
            textViewNoData.setVisibility(View.GONE);
            for(int i=0; i<feedList.size();i++){
                this.feedList.add(feedList.get(i));
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void noInternetConnection() {
        Helper.showSnackBar(recyclerView, this);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void error(String errorMessage) {
        Helper.showToast(this, errorMessage);
    }
}
