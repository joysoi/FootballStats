package com.example.footballstats.util;

import androidx.lifecycle.LiveData;

import com.example.footballstats.models.Competitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {
    private static final int TIMESTAMP_1 = 2;
    private static final int TIMESTAMP_2 = 3;
    public static final int COMP_ID_1 = 1;
    public static final int COMP_ID_2 = 2;
    public static final String COMP_NAME_1 = "Premier League";
    public static final String COMP_NAME_2 = "Serie A";
    public static final Competitions TEST_COMPETITIONS_1 = new Competitions(1, "Premier League", TIMESTAMP_1);
    public static final Competitions TEST_COMPETITIONS_2 = new Competitions(-1, "Serie A", TIMESTAMP_2);

    public static final Competitions[] TEST_COMPETITIONS_ARRAY = new Competitions[]{TEST_COMPETITIONS_1, TEST_COMPETITIONS_2};


    public static final List<Competitions> TEST_COMPETITIONS_LIST = Collections.unmodifiableList(
            new ArrayList<Competitions>() {{
                add(new Competitions(1, "Bundesliga", TIMESTAMP_1));
            }}
    );
}
