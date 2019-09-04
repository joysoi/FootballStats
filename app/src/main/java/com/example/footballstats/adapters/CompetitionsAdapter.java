package com.example.footballstats.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;
import com.example.footballstats.models.Competitions;

import java.util.ArrayList;
import java.util.List;

public class CompetitionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int COMPETITIONS_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Competitions> competitions;
    private OnCompetitionsListener onCompetitionsListener;


    public CompetitionsAdapter(OnCompetitionsListener onCompetitionsListener) {
        this.onCompetitionsListener = onCompetitionsListener;
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
            case COMPETITIONS_TYPE:
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_competition_list_items, parent, false);
                return new CompetitionsViewHolder(view, onCompetitionsListener);
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == COMPETITIONS_TYPE) {
            ((CompetitionsViewHolder) holder).leagueName.setText(competitions.get(position).getCompetitionName());
            switch (competitions.get(position).getCompetitionName()) {
                case "Premier League":
                    ((CompetitionsViewHolder) holder).countryFlag.setImageResource(R.drawable.flag_england);
                    break;
                case "Bundesliga":
                    ((CompetitionsViewHolder) holder).countryFlag.setImageResource(R.drawable.flag_germany);
                    break;
                case "Ligue 1":
                    ((CompetitionsViewHolder) holder).countryFlag.setImageResource(R.drawable.flag_france);
                    break;
                case "Primeira Liga":
                    ((CompetitionsViewHolder) holder).countryFlag.setImageResource(R.drawable.flag_portugal);
                    break;
                case "Eredivisie":
                    ((CompetitionsViewHolder) holder).countryFlag.setImageResource(R.drawable.flag_nederland);
                    break;
                case "Serie A":
                    ((CompetitionsViewHolder) holder).countryFlag.setImageResource(R.drawable.flag_italy);
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (competitions.get(position).getCompetitionName().equals("LOADING...")) {
            return LOADING_TYPE;
        } else {
            return COMPETITIONS_TYPE;
        }
    }

    public void displayLoading(){
        clearCompetitionsList();
        Competitions comp = new Competitions();
        comp.setCompetitionName("LOADING...");
        competitions.add(comp);
        notifyDataSetChanged();
    }


    private void clearCompetitionsList(){
        if(competitions == null){
            competitions = new ArrayList<>();
        }
        else {
            competitions.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (competitions != null) {
            return competitions.size();
        }
        return 0;
    }

    public void setCompetitions(List<Competitions> competitionsList) {
        competitions = competitionsList;
        notifyDataSetChanged();
    }

    public Competitions getSelectedCompetition(int position) {
        if (competitions != null) {
            if (competitions.size() > 0) {
                return competitions.get(position);
            }
        }
        return null;
    }
}