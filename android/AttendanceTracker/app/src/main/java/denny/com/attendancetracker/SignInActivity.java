package denny.com.attendancetracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import denny.com.attendancetracker.contracts.SignInContract;
import denny.com.attendancetracker.presenter.SignInPresenter;
import denny.com.attendancetracker.repositories.SignInRepository;
import denny.com.attendancetracker.utils.Helper;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.buttonSignIn)
    Button buttonSignIn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SignInPresenter presenter;
    private ProgressDialog progressDialog;
    boolean isFromLogout = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        presenter = new SignInPresenter(this, this, new SignInRepository());

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.signIn(editTextEmail.getText().toString(),
                        editTextPassword.getText().toString());
            }
        });
        isFromLogout = getIntent().getBooleanExtra("isFromLogout", false);
        initProgressBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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
        if(isFromLogout == false){
            Intent intent = new Intent();
            intent.putExtra("isSignedIn", true);
            setResult(Activity.RESULT_OK, intent);
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
        Helper.showSnackBar(buttonSignIn, this);
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
