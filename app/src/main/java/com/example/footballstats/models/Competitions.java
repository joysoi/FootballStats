package com.example.footballstats.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "competitions_table")
public class Competitions implements Parcelable {


    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int competitionId;

    @SerializedName("name")
    @Expose
    private String competitionName;


    private int timestamp;

    public Competitions() {
    }

    @Ignore
    public Competitions(int competitionId) {
        this.competitionId = competitionId;
    }

    @Ignore
    public Competitions(String competitionName) {
        this.competitionName = competitionName;
    }

    @Ignore
    public Competitions(int competitionId, String competitionName) {
        this.competitionId = competitionId;
        this.competitionName = competitionName;
    }

    @Ignore
    public Competitions(int competitionId, @NonNull String competitionName, int timestamp) {
        this.competitionId = competitionId;
        this.competitionName = competitionName;
        this.timestamp = timestamp;
    }

    @Ignore
    public Competitions(@NonNull String competitionName, int timestamp) {
        this.competitionName = competitionName;
        this.timestamp = timestamp;
    }

    @Ignore
    public Competitions(Competitions competitions) {
        competitionId = competitions.competitionId;
        competitionName = competitions.competitionName;
        timestamp = competitions.timestamp;
    }

    public Competitions(Parcel in) {
        competitionId = in.readInt();
        competitionName = in.readString();
        timestamp = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(competitionId);
        dest.writeString(competitionName);
        dest.writeInt(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Competitions> CREATOR = new Creator<Competitions>() {
        @Override
        public Competitions createFromParcel(Parcel in) {
            return new Competitions(in);
        }

        @Override
        public Competitions[] newArray(int size) {
            return new Competitions[size];
        }
    };

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Competitions competitions = (Competitions) obj;
        return competitions.getCompetitionId() == getCompetitionId() &&
                competitions.getCompetitionName().equals(getCompetitionName());
    }
}
