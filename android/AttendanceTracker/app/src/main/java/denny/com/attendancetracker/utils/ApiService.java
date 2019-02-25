package denny.com.attendancetracker.utils;

import denny.com.attendancetracker.models.ApprovalResult;
import denny.com.attendancetracker.models.BaseResult;
import denny.com.attendancetracker.models.CreateApproval;
import denny.com.attendancetracker.models.Employee;
import denny.com.attendancetracker.models.FeedResult;
import denny.com.attendancetracker.models.InputBaseUrlResult;
import denny.com.attendancetracker.models.SignInResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("welcome/")
    Call<InputBaseUrlResult> checkingConnectionToServer();

    @POST("employees/signin")
    Call<SignInResult> signIn(@Body Employee employee);

    @POST("employees/signup")
    Call<BaseResult> signUp(@Body Employee employee);

    @GET("employees/token/{email}")
    Call<BaseResult> getnewToken(@Path("email") String email);

    @GET("feeds")
    Call<FeedResult> allFeeds(@Header("Authorization") String authorization);

    @GET("approvals/{approvalId}")
    Call<ApprovalResult> allApprovals(@Header("Authorization") String authorization,
                                      @Path("approvalId") String approvalId);

    @POST("approvals/{employeeId}")
    Call<BaseResult> applyApproval(@Header("Authorization") String authorization,
                                   @Path("employeeId") String employeeId,
                                   @Body CreateApproval createApproval);



}
