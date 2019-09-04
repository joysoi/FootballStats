package com.example.footballstats.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;
import com.example.footballstats.models.Scorers;


import java.util.ArrayList;
import java.util.List;

public class ScorersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SCORERS_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Scorers> scorersList;

    public ScorersAdapter() {
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
            case SCORERS_TYPE:
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_scorers_list_items, parent, false);
                return new ScorersViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == SCORERS_TYPE) {
            ((ScorersViewHolder) holder).textViewGoals
                    .setText(String.valueOf(scorersList.get(position).getNumberOfGoals()));
            ((ScorersViewHolder) holder).textViewPlayerName
                    .setText(scorersList.get(position).getPlayer().getPlayerName());
            ((ScorersViewHolder) holder).textViewTeamName
                    .setText(scorersList.get(position).getTeam().getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (scorersList.get(position).getNumberOfGoals() == 123) {
            return LOADING_TYPE;
        } else {
            return SCORERS_TYPE;
        }
    }

    public void displayLoading(){
        clearScorersList();
        Scorers scorers = new Scorers();
        scorers.setNumberOfGoals(123);
        scorersList.add(scorers);
        notifyDataSetChanged();
    }

    private void clearScorersList(){
        if (scorersList == null){
            scorersList = new ArrayList<>();
        }else {
            scorersList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (scorersList != null) {
            return scorersList.size();
        }
        return 0;
    }

    public void setScorersList(List<Scorers> list){
        scorersList = list;
        notifyDataSetChanged();
    }
}