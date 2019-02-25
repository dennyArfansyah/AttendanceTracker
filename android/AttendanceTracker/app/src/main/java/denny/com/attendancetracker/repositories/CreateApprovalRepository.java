package denny.com.attendancetracker.repositories;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import denny.com.attendancetracker.contracts.CreateApprovalContract;
import denny.com.attendancetracker.models.BaseResult;
import denny.com.attendancetracker.models.CreateApproval;
import denny.com.attendancetracker.utils.ConnectivityReceiver;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.Helper;
import denny.com.attendancetracker.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class CreateApprovalRepository implements CreateApprovalContract.Repository {

    @Override
    public void doApplyApproval(Context context, CreateApproval approval, final Callback callback) {
        String since = new Helper().getSubmitDobFormattedDate(approval.getSince());
        String until = new Helper().getSubmitDobFormattedDate(approval.getUntil());
        approval.setSince(since);
        approval.setUntil(until);
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
        Call<BaseResult> applyResult = new RetrofitClient(context).getApi()
                .applyApproval(token, employeeId, approval);
        applyResult.enqueue(new retrofit2.Callback<BaseResult>() {
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
