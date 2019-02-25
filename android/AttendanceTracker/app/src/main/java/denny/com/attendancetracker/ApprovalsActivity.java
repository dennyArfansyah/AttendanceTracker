package denny.com.attendancetracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import denny.com.attendancetracker.adapter.ApprovalAdapter;
import denny.com.attendancetracker.contracts.ApprovalContract;
import denny.com.attendancetracker.models.Approval;
import denny.com.attendancetracker.presenter.ApprovalPresenter;
import denny.com.attendancetracker.repositories.ApprovalRepository;
import denny.com.attendancetracker.utils.Helper;

public class ApprovalsActivity extends BaseActivity implements ApprovalContract.View {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.textViewNoData)
    TextView textViewNoData;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.buttonPending)
    Button buttonPending;
    @BindView(R.id.buttonApproved)
    Button buttonApproved;
    @BindView(R.id.buttonRejected)
    Button buttonRejected;

    private ApprovalPresenter presenter;
    private ApprovalAdapter adapter;
    private List<Approval> approvalList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_approvals;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_approvals;
    }

    @Override
    public void initContentView() {
        presenter = new ApprovalPresenter(this, this, new ApprovalRepository());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        approvalList = new ArrayList<>();
        adapter = new ApprovalAdapter(approvalList);
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(this);

        buttonPending.setOnClickListener(this);
        buttonApproved.setOnClickListener(this);
        buttonRejected.setOnClickListener(this);
    }

    private void initBackgroundButtonColor(int index){
        if(index == 0){
            buttonPending.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_green));
            buttonPending.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));

            buttonApproved.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_grey));
            buttonApproved.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

            buttonRejected.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_grey));
            buttonRejected.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
            presenter.getAllApprovalsBasedOnStatus(0);
        }else if(index == 1){
            buttonPending.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_grey));
            buttonPending.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

            buttonApproved.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_green));
            buttonApproved.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));

            buttonRejected.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_grey));
            buttonRejected.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
            presenter.getAllApprovalsBasedOnStatus(1);
        }else if(index == 2){
            buttonPending.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_grey));
            buttonPending.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

            buttonApproved.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_grey));
            buttonApproved.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

            buttonRejected.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background_green));
            buttonRejected.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            presenter.getAllApprovalsBasedOnStatus(2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllApprovals();
    }

    @Override
    public void initHeaderView() {
        imageViewSecondRight.setVisibility(View.GONE);
        textViewTitleMenu.setText(getResources().getString(R.string.approvals));
    }

    @Override
    public void onClick(View v) {
        if(v == floatingActionButton){
            startActivity(new Intent(this, CreateApprovalActivity.class));
        }else if(v == buttonPending){
            initBackgroundButtonColor(0);
        }else if(v == buttonApproved){
            initBackgroundButtonColor(1);
        }else if(v == buttonRejected){
            initBackgroundButtonColor(2);
        }
    }

    @Override
    public void success(List<Approval> approvalList) {
        this.approvalList.clear();
        if(approvalList.size() == 0){
            textViewNoData.setVisibility(View.VISIBLE);
        }else {
            textViewNoData.setVisibility(View.GONE);
            for(int i=0; i<approvalList.size();i++){
                this.approvalList.add(approvalList.get(i));
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
