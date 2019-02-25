package denny.com.attendancetracker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import denny.com.attendancetracker.R;
import denny.com.attendancetracker.models.Feed;
import denny.com.attendancetracker.utils.Helper;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Feed> feedList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.textViewDate) TextView textViewDate;
        @BindView(R.id.textViewContent) TextView textViewContent;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public FeedAdapter(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed,
                viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Feed feed = feedList.get(position);

        viewHolder.textViewContent.setText(feed.getContent());
        String date = new Helper().getApprovalFormattedDate(feed.getDate().split("T")[0]);
        viewHolder.textViewDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
