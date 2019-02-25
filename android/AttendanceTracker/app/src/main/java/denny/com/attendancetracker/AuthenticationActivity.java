package denny.com.attendancetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.buttonSignIn)
    Button buttonSignIn;
    @BindView(R.id.buttonSignUp)
    Button buttonSignUp;

    private final static int REQUEST_SIGNIN = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);

        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSignIn){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivityForResult(intent, REQUEST_SIGNIN);
        }else {
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_SIGNIN){
            if(resultCode == Activity.RESULT_OK){
                if(data.getBooleanExtra("isSignedIn", true)){
                    startActivity(new Intent(this, ServicesActivity.class));
                    finish();
                }
            }
        }
    }
}
