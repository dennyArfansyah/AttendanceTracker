package denny.com.attendancetracker.repositories;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import denny.com.attendancetracker.contracts.SignInContract;
import denny.com.attendancetracker.models.Employee;
import denny.com.attendancetracker.models.SignInResult;
import denny.com.attendancetracker.utils.ConnectivityReceiver;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class SignInRepository implements SignInContract.Repository {

    @Override
    public void doLogin(Context context, Employee employee, final Callback callback) {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(!isConnected) {
            callback.noInternetConnection();
            return;
        }
        Call<SignInResult> signInCall = new RetrofitClient(context).getApi()
                .signIn(employee);
        signInCall.enqueue(new retrofit2.Callback<SignInResult>() {
            @Override
            public void onResponse(Call<SignInResult> call, Response<SignInResult> response) {
                if(response.isSuccessful()){
                    DatabaseHelper.insertEmployee(response.body().getData());
                    callback.onSuccess(response.body().getMessage());
                }else {
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
            public void onFailure(Call<SignInResult> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
