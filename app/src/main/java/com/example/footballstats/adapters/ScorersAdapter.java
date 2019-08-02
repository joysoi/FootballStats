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
import com.example.footballstats.models.Scorers;

public class ScorersAdapter extends ListAdapter<Scorers, ScorersAdapter.ViewHolder> {

    public ScorersAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Scorers> DIFF_CALLBACK = new DiffUtil.ItemCallback<Scorers>() {
        @Override
        public boolean areItemsTheSame(@NonNull Scorers oldItem, @NonNull Scorers newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Scorers oldItem, @NonNull Scorers newItem) {
            return false;
        }
    };

    @NonNull
    @Override
    public ScorersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_scorers_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScorersAdapter.ViewHolder holder, int position) {
        Scorers scorers = getItem(position);
        holder.textViewTeamName.setText(scorers.getTeam().getName());
        holder.textViewPlayerName.setText(scorers.getPlayer().getPlayerName());
        holder.textViewGoals.setText(String.valueOf(scorers.getNumberOfGoals()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTeamName;
        private TextView textViewPlayerName;
        private TextView textViewGoals;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTeamName = itemView.findViewById(R.id.textViewTeamNameScorers);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            textViewGoals = itemView.findViewById(R.id.textViewGoals);
        }
    }
}