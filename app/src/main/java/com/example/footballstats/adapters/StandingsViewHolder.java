package com.example.footballstats.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;

class StandingsViewHolder extends RecyclerView.ViewHolder {

    TextView textViewTeamName;
    TextView textViewPosition;
    TextView textViewPoints;


    StandingsViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTeamName = itemView.findViewById(R.id.textViewTeamName);
        textViewPosition = itemView.findViewById(R.id.textViewPosition);
        textViewPoints = itemView.findViewById(R.id.textViewPoints);
    }
}
