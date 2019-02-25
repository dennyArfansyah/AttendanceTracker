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
import denny.com.attendancetracker.contracts.CreateApprovalContract;
import denny.com.attendancetracker.models.CreateApproval;
import denny.com.attendancetracker.models.LeaveType;
import denny.com.attendancetracker.presenter.CreateApprovalPresenter;
import denny.com.attendancetracker.repositories.CreateApprovalRepository;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.Helper;

public class CreateApprovalActivity extends AppCompatActivity implements View.OnClickListener,
        CreateApprovalContract.View {

    @BindView(R.id.spinnerLeaveType)
    Spinner spinnerLeaveType;
    @BindView(R.id.editTextLeaveReason)
    EditText editTextLeaveReason;
    @BindView(R.id.buttonSinceDuration)
    Button buttonSinceDuration;
    @BindView(R.id.buttonUntilDuration)
    Button buttonUntilDuration;
    @BindView(R.id.buttonSubmit)
    Button buttonSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ProgressDialog progressDialog;
    private String selectedLeaveType = "";
    private CreateApprovalPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_approval);
        ButterKnife.bind(this);

        initContent();
        initProgressBar();

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        presenter = new CreateApprovalPresenter(this, this, new CreateApprovalRepository());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initContent(){
        buttonSinceDuration.setOnClickListener(this);
        buttonUntilDuration.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);

        final List<LeaveType> leaveTypeList = DatabaseHelper.getLeaveTypes();
        if(leaveTypeList != null && leaveTypeList.size() > 0){
            String [] leaveTypNames = new String[leaveTypeList.size()];
            for(int i=0; i<leaveTypeList.size(); i++){
                leaveTypNames[i] = leaveTypeList.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, leaveTypNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLeaveType.setAdapter(adapter);

            spinnerLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    for(int i=0; i<leaveTypeList.size(); i++){
                        if(i == position){
                            selectedLeaveType = leaveTypeList.get(i).getName();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }
    }

    private void initProgressBar(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        if(v == buttonSinceDuration){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String dob = new Helper().getFormattedDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            buttonSinceDuration.setText(dob);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }else if(v == buttonUntilDuration){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String dob = new Helper().getFormattedDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            buttonUntilDuration.setText(dob);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }else if(v == buttonSubmit){
            CreateApproval approval = new CreateApproval(DatabaseHelper.getEmployee().get_id(), selectedLeaveType, editTextLeaveReason.getText().toString(),
                    buttonSinceDuration.getText().toString(), buttonUntilDuration.getText().toString(), 0);
            presenter.applyApproval(approval);
        }
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
        Helper.showSnackBar(buttonSubmit, this);
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
