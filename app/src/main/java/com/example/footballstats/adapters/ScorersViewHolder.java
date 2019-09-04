package com.example.footballstats.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;

class ScorersViewHolder extends RecyclerView.ViewHolder {

    TextView textViewTeamName;
    TextView textViewPlayerName;
    TextView textViewGoals;

    ScorersViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTeamName = itemView.findViewById(R.id.textViewTeamNameScorers);
        textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
        textViewGoals = itemView.findViewById(R.id.textViewGoals);
    }
}
