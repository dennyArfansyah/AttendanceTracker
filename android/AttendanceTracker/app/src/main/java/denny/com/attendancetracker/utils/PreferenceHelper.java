package denny.com.attendancetracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import denny.com.attendancetracker.R;

public class PreferenceHelper {

    private String TAG = PreferenceHelper.class.getSimpleName();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "ATTENDANCE";

    public static String IP_ADDRESS = "IP_ADDRESS";

    public PreferenceHelper(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIpAddress(String ipAddress){
        editor.putString(IP_ADDRESS, ipAddress);
        editor.commit();
    }

    public String getApiAddress(){
        return pref.getString(IP_ADDRESS,
                context.getResources().getString(R.string.default_api_address));
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
