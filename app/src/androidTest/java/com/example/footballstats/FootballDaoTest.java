package com.example.footballstats;

import android.database.sqlite.SQLiteConstraintException;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Table;
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
        Insert, Read Competitions
     */
    @Test
    public void testInsertReadCompetitions() throws Exception {
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
        Insert, Read and Update Competitions
     */

    @Test
    public void testInsertReadUpdateCompetitions() throws Exception {
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


    /*
        Insert Read and Update Table data
     */


    @Test
    public void testInsertReadUpdateTableData() throws Exception{
        Table[] tables = new Table[]{TestUtil.TEST_TABLE_DATA_1, TestUtil.TEST_TABLE_DATA_2};

        //insert
        getFootballDao().insertTableData(tables);

        //read
        LiveDataTestUtil<List<Table>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Table> insertedTableList = listLiveDataTestUtil.getValue(getFootballDao().getAllTableData());

        //null not returned from DB
        assertNotNull(insertedTableList);

        //check values from DB
        assertEquals(tables[0].getTeam().getName(), insertedTableList.get(0).getTeam().getName());
        assertEquals(tables[0].getPoints(), insertedTableList.get(0).getPoints());
        assertEquals(tables[0].getPosition(), insertedTableList.get(0).getPosition());

        //check if values are equal
        assertEquals(tables[0], insertedTableList.get(0));

    }

    /*
        Insert Read and Update Scorers data
     */

    @Test
    public void testInsertReadUpdateScorersData() throws Exception{
        Scorers[] scorers = new Scorers[]{TestUtil.TEST_SCORERS_DATA_1, TestUtil.TEST_SCORERS_DATA_2};

        //insert Scorers
        getFootballDao().insertScorers(scorers);

        //read Scorers
        LiveDataTestUtil<List<Scorers>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Scorers> insertedScorersList = listLiveDataTestUtil.getValue(getFootballDao().getAllScorers());

        //null not returned from DB
        assertNotNull(insertedScorersList);

        //check values from DB
        assertEquals(scorers[0].getNumberOfGoals(), insertedScorersList.get(0).getNumberOfGoals());
        assertEquals(scorers[0].getPlayer().getPlayerName(), insertedScorersList.get(0).getPlayer().getPlayerName());
        assertEquals(scorers[0].getTeam().getName(), insertedScorersList.get(0).getTeam().getName());

       // check if values are equal
        assertEquals(scorers[0], insertedScorersList.get(0));
    }
}
