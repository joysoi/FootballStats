package com.example.footballstats.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;
import com.example.footballstats.models.Table;

import java.util.ArrayList;
import java.util.List;

public class StandingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TABLE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Table> tableList;

    public StandingsAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
            case TABLE_TYPE:
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_standings_list_items, parent, false);
                return new StandingsViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TABLE_TYPE) {
            ((StandingsViewHolder) holder).textViewPosition.setText(String.valueOf(tableList.get(position).getPosition()));
            ((StandingsViewHolder) holder).textViewPoints.setText(String.valueOf(tableList.get(position).getPoints()));
            ((StandingsViewHolder) holder).textViewTeamName.setText(tableList.get(position).getTeam().getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (tableList.get(position).getPoints().equals(123)) {
            return LOADING_TYPE;
        } else {
            return TABLE_TYPE;
        }
    }

    public void displayLoading() {
        clearTableList();
        Table table = new Table();
        table.setPoints(123);
        tableList.add(table);
        notifyDataSetChanged();
    }

    private void clearTableList() {
        if (tableList == null) {
            tableList = new ArrayList<>();
        }else {
            tableList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (tableList != null) {
            return tableList.size();
        }
        return 0;
    }

    public void setTableList(List<Table> list) {
        tableList = list;
        notifyDataSetChanged();
    }
}

