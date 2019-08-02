package com.example.footballstats;

import android.database.sqlite.SQLiteConstraintException;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.persistance.FootballDataBase;
import com.example.footballstats.util.LiveDataTestUtil;
import com.example.footballstats.util.TestUtil;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FootballDaoTest extends FootballDataBaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /*
        Insert, Read
     */
    @Test
    public void testInsertRead() throws Exception {
        Competitions[] competitions = new Competitions[]{TestUtil.TEST_COMPETITIONS_2, TestUtil.TEST_COMPETITIONS_1};

        //insert
        getFootballDao().insertCompetitions(competitions);

        //read
        LiveDataTestUtil<List<Competitions>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Competitions> insertedCompetitions = listLiveDataTestUtil.getValue(getFootballDao().getAllCompetitions());

        //null not returned from DB
        assertNotNull(insertedCompetitions);

        //check values from DB
        assertEquals(competitions[0].getCompetitionName(), insertedCompetitions.get(0).getCompetitionName());
        assertEquals(competitions[0].getTimestamp(), insertedCompetitions.get(0).getTimestamp());

        //assign an Id
        competitions[0].setCompetitionId(insertedCompetitions.get(0).getCompetitionId());

        //check if values are equal
        assertEquals(competitions[0], insertedCompetitions.get(0));
    }

    /*
        Insert, Read and Update
     */

    @Test
    public void testInsertReadUpdate() throws Exception {
        Competitions[] competitions = new Competitions[]{TestUtil.TEST_COMPETITIONS_2, TestUtil.TEST_COMPETITIONS_1};

        //insert
        getFootballDao().insertCompetitions(competitions);

        //read
        LiveDataTestUtil<List<Competitions>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Competitions> insertedCompetitions = listLiveDataTestUtil.getValue(getFootballDao().getAllCompetitions());

        //null not returned from DB
        assertNotNull(insertedCompetitions);

        //check values from DB
        assertEquals(competitions[0].getCompetitionName(), insertedCompetitions.get(0).getCompetitionName());
        assertEquals(competitions[0].getTimestamp(), insertedCompetitions.get(0).getTimestamp());

        //assign an Id
        competitions[0].setCompetitionId(insertedCompetitions.get(0).getCompetitionId());

        //check if values are equal
        assertEquals(competitions[0], insertedCompetitions.get(0));

        //update
        int compId = competitions[0].getCompetitionId();
        String compName = competitions[0].getCompetitionName();
        getFootballDao().updateCompetitions(compId, compName);

        //read again
        insertedCompetitions = listLiveDataTestUtil.getValue(getFootballDao().getAllCompetitions());

        assertEquals(compId, insertedCompetitions.get(0).getCompetitionId());
        assertEquals(compName, insertedCompetitions.get(0).getCompetitionName());
    }
}
