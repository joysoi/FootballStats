package com.example.footballstats.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;
import com.example.footballstats.models.Table;

public class StandingsAdapter extends ListAdapter<Table, StandingsAdapter.ViewHolder> {

    public StandingsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Table> DIFF_CALLBACK = new DiffUtil.ItemCallback<Table>() {
        @Override
        public boolean areItemsTheSame(@NonNull Table oldItem, @NonNull Table newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Table oldItem, @NonNull Table newItem) {
            return false;
        }
    };

    @NonNull
    @Override
    public StandingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_standings_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StandingsAdapter.ViewHolder holder, int position) {
        Table table = getItem(position);
        holder.textViewPosition.setText(String.valueOf(table.getPosition()));
        holder.textViewPoints.setText(String.valueOf(table.getPoints()));
        holder.textViewTeamName.setText(table.getTeam().getName());
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTeamName;
        private TextView textViewPosition;
        private TextView textViewPoints;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTeamName = itemView.findViewById(R.id.textViewTeamName);
            textViewPosition = itemView.findViewById(R.id.textViewPosition);
            textViewPoints = itemView.findViewById(R.id.textViewPoints);
        }
    }
}
