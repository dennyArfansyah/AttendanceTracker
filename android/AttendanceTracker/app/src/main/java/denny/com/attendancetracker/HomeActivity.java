package denny.com.attendancetracker;

import android.view.View;

public class HomeActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    @Override
    public void initContentView() {
        textViewTitleMenu.setText(getResources().getString(R.string.home));
    }

    @Override
    public void initHeaderView() {

    }

    @Override
    public void onClick(View v) {

    }
}
