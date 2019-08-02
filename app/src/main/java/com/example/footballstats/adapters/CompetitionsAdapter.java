package com.example.footballstats.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.footballstats.R;
import com.example.footballstats.models.Competitions;

public class CompetitionsAdapter extends ListAdapter<Competitions, CompetitionsAdapter.ViewHolder> {

    private OnClickCompetitionListener onCompetitionsListener;
    public CompetitionsAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<Competitions> DIFF_CALLBACK = new DiffUtil.ItemCallback<Competitions>() {
        @Override
        public boolean areItemsTheSame(@NonNull Competitions oldItem, @NonNull Competitions newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Competitions oldItem, @NonNull Competitions newItem) {
            return false;
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_competition_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Competitions competitions = getItem(position);
        holder.leagueName.setText(competitions.getCompetitionName());
        switch (getItem(position).getCompetitionName()) {
            case "Premier League":
                holder.countryFlag.setImageResource(R.drawable.flag_england);
                break;
            case "Bundesliga":
                holder.countryFlag.setImageResource(R.drawable.flag_germany);
                break;
            case "Ligue 1":
                holder.countryFlag.setImageResource(R.drawable.flag_france);
                break;
            case "Primeira Liga":
                holder.countryFlag.setImageResource(R.drawable.flag_portugal);
                break;
            case "Eredivisie":
                holder.countryFlag.setImageResource(R.drawable.flag_nederland);
                break;
            case "Serie A":
                holder.countryFlag.setImageResource(R.drawable.flag_italy);
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView leagueName;
        AppCompatImageView countryFlag;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueName = itemView.findViewById(R.id.textLeagueName);
            countryFlag = itemView.findViewById(R.id.imageFlag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onCompetitionsListener != null && position != RecyclerView.NO_POSITION) {
                        onCompetitionsListener.competitionListClick(getItem(position));
                    }
                }
            });
        }

    }

    public interface OnClickCompetitionListener {
        void competitionListClick(Competitions competitions);
    }

    public void setOnCompetitionsItemClickListener(OnClickCompetitionListener clickListener) {
        this.onCompetitionsListener = clickListener;
    }
}
