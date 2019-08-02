package com.example.footballstats;


import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.persistance.FootballDataBase;

import org.junit.After;
import org.junit.Before;

public abstract class FootballDataBaseTest {

    //System under test
    private FootballDataBase footballDataBase;


    FootballDao getFootballDao() {
        return footballDataBase.getFootballDao();
    }

    @Before
    public void init() {
        footballDataBase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                FootballDataBase.class
        ).build();
    }

    @After
    public void finish() {
        footballDataBase.close();
    }
}
