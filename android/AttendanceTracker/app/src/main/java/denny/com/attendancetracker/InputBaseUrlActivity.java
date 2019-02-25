package denny.com.attendancetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import denny.com.attendancetracker.contracts.InputBaseUrlContract;
import denny.com.attendancetracker.presenter.InputBaseUrlPresenter;
import denny.com.attendancetracker.repositories.InputBaseUrlRepository;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.Helper;

public class InputBaseUrlActivity extends AppCompatActivity implements InputBaseUrlContract.InputBaseUrlView {

    @BindView(R.id.editTextBaseUrl)
    EditText editTextBaseUrl;
    @BindView(R.id.buttonCheckServerConnection)
    Button buttonCheckServerConnection;
    private ProgressDialog progressDialog;

    private InputBaseUrlPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_base_url);
        ButterKnife.bind(this);

        presenter = new InputBaseUrlPresenter(this, this, new InputBaseUrlRepository());
        buttonCheckServerConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkServerConnection(editTextBaseUrl.getText().toString());
            }
        });

        initProgressBar();
    }

    private void initProgressBar(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
    }

    @Override
    public void success(String message) {
        Helper.showToast(this, message);

        if(DatabaseHelper.getEmployee() == null){
            startActivity(new Intent(this, AuthenticationActivity.class));
        }else {
            startActivity(new Intent(this, ServicesActivity.class));
        }

        finish();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void noInternetConnection() {
        Helper.showSnackBar(buttonCheckServerConnection, this);
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void error(String errorMessage) {
        Helper.showToast(this, errorMessage);
    }
}
