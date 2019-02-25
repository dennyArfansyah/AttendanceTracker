package denny.com.attendancetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import denny.com.attendancetracker.contracts.MoreContract;
import denny.com.attendancetracker.presenter.MorePresenter;
import denny.com.attendancetracker.repositories.MoreRepository;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.Helper;

public class MoreActivity extends BaseActivity implements MoreContract.View {

    @BindView(R.id.layoutLogout)
    LinearLayout layoutLogout;
    @BindView(R.id.textViewName)
    TextView textViewName;

    private MorePresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    public int getContentViewId() {
        return R.layout.activity_more;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_more;
    }

    @Override
    public void initContentView() {
        layoutLogout.setOnClickListener(this);
        initProgressBar();
        textViewName.setText("Hi " + DatabaseHelper.getEmployee().getName());
        presenter = new MorePresenter(this, new MoreRepository());
    }

    private void initProgressBar(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
    }

    @Override
    public void initHeaderView() {
        textViewTitleMenu.setText(getResources().getString(R.string.more));
    }

    @Override
    public void onClick(View v) {
        if(v == layoutLogout){
            presenter.signOut();
        }
    }

    @Override
    public void success() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra("isFromLogout", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void noInternetConnection() {
        Helper.showSnackBar(layoutLogout, this);
    }

    @Override
    public void hideLoading() {
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                progressDialog.hide();
            }
        }.start();

    }

    @Override
    public void error(String errorMessage) {
        Helper.showToast(this, errorMessage);
    }
}
