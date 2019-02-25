package denny.com.attendancetracker.repositories;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import denny.com.attendancetracker.contracts.InputBaseUrlContract;
import denny.com.attendancetracker.models.Division;
import denny.com.attendancetracker.models.InputBaseUrlResult;
import denny.com.attendancetracker.models.LeaveType;
import denny.com.attendancetracker.utils.ConnectivityReceiver;
import denny.com.attendancetracker.utils.DatabaseHelper;
import denny.com.attendancetracker.utils.PreferenceHelper;
import denny.com.attendancetracker.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class InputBaseUrlRepository implements InputBaseUrlContract.Repository {

    @Override
    public void doCheckServerConnection(Context context, String ipAddress, final Callback callback) {
        new PreferenceHelper(context).setIpAddress("http://" + ipAddress +":8000/");
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(!isConnected) {
            callback.noInternetConnection();
            return;
        }

        Call<InputBaseUrlResult> checkServerConnection = new RetrofitClient(context).getApi()
                .checkingConnectionToServer();
        checkServerConnection.enqueue(new retrofit2.Callback<InputBaseUrlResult>() {
            @Override
            public void onResponse(Call<InputBaseUrlResult> call, Response<InputBaseUrlResult> response) {
                if(response.isSuccessful()){
                    List<Division> divisionList = response.body().getData().getDivisions();
                    if(divisionList != null && divisionList.size() > 0){
                        DatabaseHelper.insertUpdateDivision(divisionList);
                    }

                    List<LeaveType> leaveTypeList = response.body().getData().getLeave_types();
                    if(leaveTypeList != null && leaveTypeList.size() > 0){
                        DatabaseHelper.insertUpdateLeaveType(leaveTypeList);
                    }

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
            public void onFailure(Call<InputBaseUrlResult> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
