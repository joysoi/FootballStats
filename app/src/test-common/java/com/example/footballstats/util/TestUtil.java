package com.example.footballstats.util;

import androidx.lifecycle.LiveData;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Player;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Table;
import com.example.footballstats.models.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {

    //Competitions
    private static final int TIMESTAMP_1 = 2;
    private static final int TIMESTAMP_2 = 3;
    public static final int COMP_ID_1 = 1;
    public static final int COMP_ID_2 = 2;
    public static final String COMP_NAME_1 = "Premier League";
    public static final String COMP_NAME_2 = "Serie A";

    public static final Competitions TEST_COMPETITIONS_1 = new Competitions(1,
            "Premier League",
            TIMESTAMP_1);
    public static final Competitions TEST_COMPETITIONS_2 = new Competitions(-1,
            "Serie A",
            TIMESTAMP_2);

    public static final List<Competitions> TEST_COMPETITIONS_LIST = Collections.unmodifiableList(
            new ArrayList<Competitions>() {{
                add(new Competitions(2002, "Bundesliga"));
            }}
    );

    public static final List<Competitions> COMPETITIONS_LIST =
            new ArrayList<>(TestUtil.TEST_COMPETITIONS_LIST);


    //Table data
    public static final Table TEST_TABLE_DATA_1 = new Table(1,
            new Team("Arsenal"),
            5);
    public static final Table TEST_TABLE_DATA_2 = new Table(2,
            new Team("Tottenham"),
            9);

    public static final List<Table> TEST_TABLE_DATA_LIST = Collections.unmodifiableList(
            new ArrayList<Table>() {{
                add(new Table(1, new Team("Arsenal"), 5));
            }}
    );

    public static final int POSITION_1 = 1;
    public static final String TEAM_NAME_1 = "Arsenal";
    public static final int POINTS_1 = 5;


    //Scorers data
    public static final Scorers TEST_SCORERS_DATA_1 = new Scorers(5,
            new Player("Pepe"),
            new Team("Arsenal"));

    public static final Scorers TEST_SCORERS_DATA_2 = new Scorers(4,
            new Player("Benzema"),
            new Team("Real Madrid"));

    public static final List<Scorers> TEST_SCORERS_LIST = Collections.unmodifiableList(
            new ArrayList<Scorers>() {{
                add(new Scorers(5, new Player("Pepe"),new Team("Arsenal")));
            }}
    );

    public static final int NUMBER_OF_GOALS_1 = 5;
    public static final String PLAYER_1 = "Pepe";
}
