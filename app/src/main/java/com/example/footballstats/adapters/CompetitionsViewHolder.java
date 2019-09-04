package com.example.footballstats.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballstats.R;

public class CompetitionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView leagueName;
    AppCompatImageView countryFlag;
    private OnCompetitionsListener onCompetitionsListener;

    CompetitionsViewHolder(@NonNull View itemView, OnCompetitionsListener onCompetitionsListener) {
        super(itemView);
        this.onCompetitionsListener = onCompetitionsListener;
        leagueName = itemView.findViewById(R.id.textLeagueName);
        countryFlag = itemView.findViewById(R.id.imageFlag);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (onCompetitionsListener != null && position != RecyclerView.NO_POSITION) {
            onCompetitionsListener.onCompetitionsClick(position);
        }
    }
}
