package denny.com.attendancetracker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import denny.com.attendancetracker.R;
import denny.com.attendancetracker.models.Approval;
import denny.com.attendancetracker.utils.Helper;

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ViewHolder> {

    private List<Approval> approvalList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textLeaveType) TextView textLeaveType;
        @BindView(R.id.textLeaveReason) TextView textLeaveReason;
        @BindView(R.id.textLeaveDuration) TextView textLeaveDuration;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ApprovalAdapter(List<Approval> feedList) {
        this.approvalList = feedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_approval,
                viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Approval approval = approvalList.get(position);

        viewHolder.textLeaveType.setText("Leave Type : "+ approval.getType());
        viewHolder.textLeaveReason.setText("Reason : "+ approval.getReason());
        String since = new Helper().getApprovalFormattedDate(approval.getSince().split("T")[0]);
        String until = new Helper().getApprovalFormattedDate(approval.getUntil().split("T")[0]);
        viewHolder.textLeaveDuration.setText("Duration : "+ since + " - " + until);
    }

    @Override
    public int getItemCount() {
        return approvalList.size();
    }
}
