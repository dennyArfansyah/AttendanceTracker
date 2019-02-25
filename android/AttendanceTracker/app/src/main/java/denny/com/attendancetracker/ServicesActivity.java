package denny.com.attendancetracker;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import denny.com.attendancetracker.adapter.ServiceAdapter;
import denny.com.attendancetracker.contracts.ServiceContract;
import denny.com.attendancetracker.models.Service;
import denny.com.attendancetracker.presenter.ServicePresenter;
import denny.com.attendancetracker.repositories.ServiceRepository;
import denny.com.attendancetracker.utils.Helper;

public class ServicesActivity extends BaseActivity implements ServiceContract.View {

    @BindView(R.id.editTextSearchService)
    EditText editTextSearchService;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ServiceAdapter adapter;
    private List<Service> serviceList;
    private ServicePresenter presenter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_services;
    }

    @Override
    public int getNavigationMenuItemId() {
        return 0;
    }

    @Override
    public void initContentView() {
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Helper.GridSpacingItemDecoration gridSpacingItemDecoration = new Helper.GridSpacingItemDecoration(
                2, Helper.dpToPx(30, getResources()), true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(gridSpacingItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        serviceList = new ArrayList<>();
        adapter = new ServiceAdapter(serviceList);
        recyclerView.setAdapter(adapter);

        presenter = new ServicePresenter(this, new ServiceRepository());
        presenter.getAllServices();

        editTextSearchService.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }

    @Override
    public void initHeaderView() {
        textViewTitleMenu.setText(getResources().getString(R.string.services));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void success(List<Service> serviceList) {
        this.serviceList.clear();
        if(serviceList.size() > 0){
            for(int i=0; i<serviceList.size();i++){
                this.serviceList.add(serviceList.get(i));
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void noInternetConnection() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void error(String errorMessage) {

    }
}
