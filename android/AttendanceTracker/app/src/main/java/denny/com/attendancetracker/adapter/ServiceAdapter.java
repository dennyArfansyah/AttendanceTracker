package denny.com.attendancetracker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import denny.com.attendancetracker.R;
import denny.com.attendancetracker.models.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>
        implements Filterable {

    private List<Service> serviceList;
    private List<Service> serviceListFiltered;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageService)
        ImageView imageService;
        @BindView(R.id.textServiceName)
        TextView textServiceName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ServiceAdapter(List<Service> serviceList) {
        this.serviceList = serviceList;
        this.serviceListFiltered = serviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service,
                viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder viewHolder, int position) {
        Service service = serviceListFiltered.get(position);

        viewHolder.textServiceName.setText(service.getName());
        viewHolder.imageService.setImageResource(service.getImage());
    }

    @Override
    public int getItemCount() {
        return serviceListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();

                List<Service> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = serviceList;
                } else {
                    for(int i=0;i<serviceList.size();i++){
                        if(serviceList.get(i).getName().toLowerCase().contains(query.toLowerCase())){
                            filtered.add(serviceList.get(i));
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                serviceListFiltered = (ArrayList<Service>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
