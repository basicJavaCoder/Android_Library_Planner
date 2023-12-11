package com.example.library_planner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<LibEvent> eventList;
    private final RecyclerViewInterface recyclerViewInterface;


    public MainAdapter(ArrayList<LibEvent> eventList, RecyclerViewInterface recyclerViewInterface) {
        this.eventList = eventList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        return new ViewHolder(view, recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {

        String name, start, duration, date;

        name = eventList.get(position).getEventName();
        start  = eventList.get(position).getEventStart();
        duration = eventList.get(position).getEventDuration();
        date = eventList.get(position).getEventDate();

        holder.tvName.setText(name);
        holder.tvStart.setText(start);
        holder.tvDuration.setText(duration);
        holder.tvDate.setText(date);

        holder.btMainRow.setOnClickListener(v -> {

        });

    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvStart, tvDuration, tvDate;
        Button btMainRow;


        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {

            super(itemView);

            tvName = itemView.findViewById(R.id.txtMainName);
            tvStart = itemView.findViewById(R.id.txtMainStart);
            tvDuration = itemView.findViewById(R.id.txtMainDuration);
            tvDate = itemView.findViewById(R.id.txtMainDate);
            btMainRow = itemView.findViewById(R.id.btMainRow);

        }

    }

}
