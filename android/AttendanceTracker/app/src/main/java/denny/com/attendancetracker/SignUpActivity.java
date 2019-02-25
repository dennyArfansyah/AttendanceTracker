package denny.com.attendancetracker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import denny.com.attendancetracker.contracts.SignUpContract;
import denny.com.attendancetracker.models.Division;
import denny.com.attendancetracker.models.Employee;
import denny.com.attendancetracker.presenter.SignUpPresenter;
import denny.com.attendancetracker.repositories.SignUpRepository;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.Helper;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,
        SignUpContract.View {

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.buttonDob)
    Button buttonDob;
    @BindView(R.id.spinnerDivision)
    Spinner spinnerDivision;
    @BindView(R.id.buttonSignUp)
    Button buttonSignUp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String selecetedDivision;
    private SignUpPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        buttonDob.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
        initContentView();
        initProgressBar();

        presenter = new SignUpPresenter(this, this, new SignUpRepository());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initContentView(){
        final List<Division> divisionList = DatabaseHelper.getDivisions();
        if(divisionList != null && divisionList.size() > 0){
            String [] divisionName = new String[divisionList.size()];
            for(int i=0; i<divisionList.size(); i++){
                divisionName[i] = divisionList.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, divisionName);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDivision.setAdapter(adapter);

            spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    for(int i=0; i<divisionList.size(); i++){
                        if(i == position){
                            selecetedDivision = divisionList.get(i).getName();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonDob){
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String dob = new Helper().getFormattedDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            buttonDob.setText(dob);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }else {
            Employee employee = new Employee();
            employee.setName(editTextName.getText().toString());
            employee.setEmail(editTextEmail.getText().toString());
            employee.setPassword(editTextPassword.getText().toString());
            employee.setDob(buttonDob.getText().toString());
            employee.setDivision(selecetedDivision);
            presenter.signUp(employee);
        }
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
        finish();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void noInternetConnection() {
        Helper.showSnackBar(buttonSignUp, this);
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
