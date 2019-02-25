package denny.com.attendancetracker.repositories;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import denny.com.attendancetracker.contracts.SignUpContract;
import denny.com.attendancetracker.models.BaseResult;
import denny.com.attendancetracker.models.Employee;
import denny.com.attendancetracker.utils.ConnectivityReceiver;
import denny.com.attendancetracker.utils.Helper;
import denny.com.attendancetracker.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class SignUpRepository implements SignUpContract.Repository {

    @Override
    public void doSignUp(Context context, Employee employee, final Callback callback) {
        String dob = new Helper().getSubmitDobFormattedDate(employee.getDob());
        employee.setDob(dob);
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(!isConnected) {
            callback.noInternetConnection();
            return;
        }
        Call<BaseResult> signUp = new RetrofitClient(context).getApi().signUp(employee);
        signUp.enqueue(new retrofit2.Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body().getMessage());
                }else{
                    InputStream i = response.errorBody().byteStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(i));
                    StringBuilder errorResult = new StringBuilder();
                    String line;
                    try {
                        while ((line = r.readLine()) != null) {
                            errorResult.append(line);
                        }
                        try {
                            JSONObject obj = new JSONObject(errorResult.toString());
                            String message = obj.getString("message");
                            callback.onError(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
