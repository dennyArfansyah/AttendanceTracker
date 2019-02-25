package denny.com.attendancetracker;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AttendanceTrackerApplication extends Application {

    private static AttendanceTrackerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(getResources().getString(R.string.DB_NAME))
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public static synchronized AttendanceTrackerApplication getInstance() {
        return instance;
    }

}
