package denny.com.attendancetracker.repositories;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import denny.com.attendancetracker.contracts.ApprovalContract;
import denny.com.attendancetracker.models.ApprovalResult;
import denny.com.attendancetracker.models.BaseResult;
import denny.com.attendancetracker.utils.ConnectivityReceiver;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.Helper;
import denny.com.attendancetracker.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class ApprovalRepository implements ApprovalContract.Repository {

    @Override
    public void allApprovals(Context context, final Callback callback) {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(!isConnected) {
            callback.noInternetConnection();
            return;
        }

        if(!new Helper().isTokenValid()){
            String email = DatabaseHelper.getEmployee().getEmail();
            Call<BaseResult> tokenCall = new RetrofitClient(context)
                    .getApi().getnewToken(email);
            tokenCall.enqueue(new retrofit2.Callback<BaseResult>() {
                @Override
                public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                    if(response.isSuccessful()){
                        DatabaseHelper.refreshToken(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BaseResult> call, Throwable t) {}
            });
        }

        String token = "Bearer " + DatabaseHelper.getEmployee().getToken();
        String employeeId = DatabaseHelper.getEmployee().get_id();
        Call<ApprovalResult> getAllApprovals = new RetrofitClient(context).getApi()
                .allApprovals(token, employeeId);
        getAllApprovals.enqueue(new retrofit2.Callback<ApprovalResult>() {
            @Override
            public void onResponse(Call<ApprovalResult> call, Response<ApprovalResult> response) {
                if(response.isSuccessful()){
                    DatabaseHelper.insertUpdateApproval(response.body().getData());
                    callback.onSuccess(DatabaseHelper.getApprovals(0));
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
            public void onFailure(Call<ApprovalResult> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void allApprovalsBasedOnStatus(int status, Callback callback) {
        callback.onSuccess(DatabaseHelper.getApprovals(status));
    }
}
