package denny.com.attendancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.navigationView) protected BottomNavigationView navigationView;
    @BindView(R.id.imageViewEmployeeAvatar) protected ImageView imageViewEmployeeAvatar;
    @BindView(R.id.imageViewRight) protected ImageView imageViewRight;
    @BindView(R.id.imageViewSecondRight) protected ImageView imageViewSecondRight;
    @BindView(R.id.textViewTitleMenu) protected TextView textViewTitleMenu;

    public abstract int getContentViewId();
    public abstract int getNavigationMenuItemId();
    public abstract void initContentView();
    public abstract void initHeaderView();

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        navigationView.setOnNavigationItemSelectedListener(this);

        initHeaderView();
        initContentView();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_services:
                startActivity(new Intent(this, ServicesActivity.class));
                finish();
                return true;
            case R.id.navigation_home:
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;
            case R.id.navigation_feeds:
                startActivity(new Intent(this, FeedsActivity.class));
                finish();
                return true;
            case  R.id.navigation_approvals:
                startActivity(new Intent(this, ApprovalsActivity.class));
                finish();
                return true;
            case R.id.navigation_more:
                startActivity(new Intent(this, MoreActivity.class));
                finish();
                return true;
        }

        return false;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

}
