package com.example.footballstats.repository;

import com.bumptech.glide.load.engine.Resource;
import com.example.footballstats.models.Competitions;
import com.example.footballstats.models.Scorers;
import com.example.footballstats.models.Table;
import com.example.footballstats.models.Team;
import com.example.footballstats.persistance.FootballDao;
import com.example.footballstats.requests.FootballDataApi;
import com.example.footballstats.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.*;

public class FootballRepoTest {
    public static final List<Competitions> COMPETITIONS_LIST =
            new ArrayList<>(TestUtil.TEST_COMPETITIONS_LIST);

    public static final List<Table> TABLE_DATA_LIST =
            new ArrayList<>(TestUtil.TEST_TABLE_DATA_LIST);

    public static final List<Scorers> TABLE_SCORERS_LIST =
            new ArrayList<>(TestUtil.TEST_SCORERS_LIST);

    //system under test
    private FootballRepo footballRepo;

    @Mock
    private FootballDao footballDao;
    @Mock
    private FootballDataApi footballDataApi;


    @BeforeEach
    public void initEach() {
        footballDao = mock(FootballDao.class);
        footballDataApi = mock(FootballDataApi.class);
        footballRepo = new FootballRepo(footballDao, footballDataApi);

        // single thread work
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }


    /* insert Competition */

    /*
        verify the correct method is called
        confirm that new rows are being inserted
     */
    @Test
    void insertCompetition_returnRow() throws Exception {
        //Arrange
        final long insertedRow = 1L;
        final List<Long> insertedRowsList = new ArrayList<>();
        insertedRowsList.add(insertedRow);
        final long[] returnedData = new long[insertedRowsList.size()];
        when(footballDao.insertCompetitions(any(Competitions.class)))
                .thenReturn(returnedData);

        //Act
        Competitions[] returnedValue = footballRepo.insertCompetitions(COMPETITIONS_LIST).blockingFirst();


        //Assert
        verify(footballDao, atLeast(1)).insertCompetitions(any(Competitions.class));
        verifyNoMoreInteractions(footballDao);

        System.out.println("Returned value: " + Arrays.toString(returnedValue));
        assertEquals(COMPETITIONS_LIST.toArray(returnedValue), returnedValue);

    }


    /*
        insert Competition
        return Failure (-1)
     */
    @Test
    void insertCompetition_returnFailure() throws Exception {
        //Arrange
        final long insertedRow = -1L;
        final List<Long> insertedRowList = new ArrayList<>();
        insertedRowList.add(insertedRow);
        final long[] returnedData = new long[insertedRowList.size()];
        when(footballDao.insertCompetitions(any(Competitions.class)))
                .thenReturn(returnedData);

        //Act
        Competitions[] returnedValue = footballRepo.insertCompetitions(COMPETITIONS_LIST).blockingFirst();

        //Assert
        verify(footballDao, atLeast(1)).insertCompetitions(any(Competitions.class));
        verifyNoMoreInteractions(footballDao);

        assertNotEquals(insertedRow, returnedValue);
    }


    /* update Competition */

    /*
        verify the correct method is called
        confirm that new rows are being inserted
     */
    @Test
    void updateCompetition_returnRowsUpdated() throws Exception {
        //Arrange
        Competitions[] competitions = new Competitions[]{TestUtil.TEST_COMPETITIONS_1};
        int insertedValueId = competitions[0].getCompetitionId();
        String insertedValueName = competitions[0].getCompetitionName();
        when(footballDao.updateCompetitions(insertedValueId, insertedValueName))
                .thenReturn(competitions[0].getCompetitionId());

        //Act
        int returnedValue = footballRepo.updateCompetitions(TestUtil.COMP_ID_1, TestUtil.COMP_NAME_1);

        //Assert
        verify(footballDao).updateCompetitions(insertedValueId, insertedValueName);
        verifyNoMoreInteractions(footballDao);

        assertEquals(insertedValueId, returnedValue);
    }


      /*
        update Competition
        return Failure
     */

    @Test
    void updateCompetition_returnFailure() throws Exception {
        //Arrange
        Competitions[] competitions = new Competitions[]{TestUtil.TEST_COMPETITIONS_2};
        int insertedValueId = competitions[0].getCompetitionId();
        String insertedValueName = competitions[0].getCompetitionName();
        when(footballRepo.updateCompetitions(insertedValueId, insertedValueName))
                .thenReturn(competitions[0].getCompetitionId());

        //Act
        int returnedValue = footballRepo.updateCompetitions(TestUtil.COMP_ID_1, TestUtil.COMP_NAME_1);

        //Assert
        verify(footballDao).updateCompetitions(TestUtil.COMP_ID_1, TestUtil.COMP_NAME_1);
        verifyNoMoreInteractions(footballDao);

        assertNotEquals(insertedValueId, returnedValue);
    }



    /* insert League Standings Table data */

    /*
        verify the correct method is called
        confirm that new rows are being inserted
     */

    @Test
    void insertTableData_returnRow() throws Exception {
        //Arrange
        final long insertedRow = 1L;
        final List<Long> longList = new ArrayList<>();
        longList.add(insertedRow);
        final long[] returnedData = new long[]{longList.size()};
        when(footballDao.insertTableData(any(Table.class)))
                .thenReturn(returnedData);

        //Act
        Table[] returnedValue = footballRepo.insertTableStandings(TABLE_DATA_LIST).blockingFirst();

        //Assert
        verify(footballDao, atLeast(1)).insertTableData(any(Table.class));
        verifyNoMoreInteractions(footballDao);

        System.out.println("Returned value: " + Arrays.toString(returnedValue));
        assertEquals(TABLE_DATA_LIST.toArray(returnedValue), returnedValue);
    }


    /*
    insert League Standings Table data
    return Failure (-1)
 */
    @Test
    void insertLeagueTableData_returnFailure() throws Exception {
        //Arrange
        final long insertedRow = 1L;
        final List<Long> longList = new ArrayList<>();
        longList.add(insertedRow);
        final long[] returnedData = new long[]{longList.size()};
        when(footballDao.insertTableData(any(Table.class)))
                .thenReturn(returnedData);

        //Act
        Table[] returnedValue = footballRepo.insertTableStandings(TABLE_DATA_LIST).blockingFirst();

        //Assert
        verify(footballDao, atLeast(1)).insertTableData(any(Table.class));
        verifyNoMoreInteractions(footballDao);

        assertNotEquals(insertedRow, returnedValue);
    }


    /* update League Standings Table data */

    /*
        verify the correct method is called
        confirm that new rows are being inserted
     */
    @Test
    void updateLeagueTableData_returnRowsUpdated() throws Exception {
        //Arrange
        Table[] tables = new Table[]{TestUtil.TEST_TABLE_DATA_1};
        int insertedPosition = tables[0].getPosition();
        String insertedTeamName = tables[0].getTeam().getName();
        int insertedPoints = tables[0].getPoints();

        when(footballDao.updateTableData(insertedPosition, insertedTeamName, insertedPoints))
                .thenReturn(tables[0].getPosition());

        //Act
        int returnedValue = footballRepo.updateTableStandings(TestUtil.POSITION_1,
                TestUtil.TEAM_NAME_1, TestUtil.POINTS_1);

        //Assert
        verify(footballDao).updateTableData(insertedPosition, insertedTeamName, insertedPoints);
        verifyNoMoreInteractions(footballDao);

        assertEquals(insertedPoints, returnedValue);

    }

       /*
        update League Standings Table data
        return Failure
     */

    @Test
    void updateLeagueTableData_returnFailure() throws Exception {
        //Arrange
        Table[] tables = new Table[]{TestUtil.TEST_TABLE_DATA_1};
        int insertedPosition = tables[0].getPosition();
        String insertedTeamName = tables[0].getTeam().getName();
        int insertedPoints = tables[0].getPoints();

        when(footballDao.updateTableData(insertedPosition, insertedTeamName, insertedPoints))
                .thenReturn(tables[0].getPosition());

        //Act
        int returnedValue = footballRepo.updateTableStandings(TestUtil.POSITION_1,
                TestUtil.TEAM_NAME_1, TestUtil.POINTS_1);

        //Assert
        verify(footballDao).updateTableData(insertedPosition, insertedTeamName, insertedPoints);
        verifyNoMoreInteractions(footballDao);

        assertNotEquals(insertedPosition, returnedValue);

    }


    /* insert Scorers data */

    /*
        verify the correct method is called
        confirm that new rows are being inserted
     */

    @Test
    void insertScorersData_returnRow() throws Exception {
        //Arrange
        final long insertedRow = 1L;
        final List<Long> longList = new ArrayList<>();
        longList.add(insertedRow);
        final long[] returnedData = new long[]{longList.size()};
        when(footballDao.insertScorers(any(Scorers.class)))
                .thenReturn(returnedData);

        //Act
        Scorers[] returnedValue = footballRepo.insertScorersList(TABLE_SCORERS_LIST).blockingFirst();

        //Assert
        verify(footballDao, atLeast(1)).insertScorers(any(Scorers.class));
        verifyNoMoreInteractions(footballDao);

        System.out.println("Returned value: " + Arrays.toString(returnedValue));
        assertEquals(TABLE_SCORERS_LIST.toArray(returnedValue), returnedValue);
    }

    /*
  insert Scorers data
  return Failure (-1)
*/
    @Test
    void insertScorersData_returnFailure() throws Exception {
        //Arrange
        final long insertedRow = 1L;
        final List<Long> longList = new ArrayList<>();
        longList.add(insertedRow);
        final long[] returnedData = new long[]{longList.size()};
        when(footballDao.insertScorers(any(Scorers.class)))
                .thenReturn(returnedData);

        //Act
        Scorers[] returnedValue = footballRepo.insertScorersList(TABLE_SCORERS_LIST).blockingFirst();

        //Assert
        verify(footballDao, atLeast(1)).insertScorers(any(Scorers.class));
        verifyNoMoreInteractions(footballDao);

        System.out.println("Returned value: " + Arrays.toString(returnedValue));
        assertEquals(TABLE_SCORERS_LIST.toArray(returnedValue), returnedValue);
    }

    /* update Scorers data */

    /*
        verify the correct method is called
        confirm that new rows are being inserted
     */
    @Test
    void updateScorersData_returnRowsUpdated() throws Exception {
        //Arrange
        Scorers[] scorers = new Scorers[]{TestUtil.TEST_SCORERS_DATA_1};
        int insertedNumberOfGoals = scorers[0].getNumberOfGoals();
        String insertedTeamName = scorers[0].getTeam().getName();
        String insertedPlayerName = scorers[0].getPlayer().getPlayerName();

        when(footballDao.updateScorers(insertedNumberOfGoals, insertedTeamName, insertedPlayerName))
                .thenReturn(scorers[0].getNumberOfGoals());

        //Act
        int returnedValue = footballRepo.updateScorersTable(TestUtil.NUMBER_OF_GOALS_1,
                TestUtil.TEAM_NAME_1, TestUtil.PLAYER_1);

        //Assert
        verify(footballDao).updateScorers(insertedNumberOfGoals, insertedTeamName, insertedPlayerName);
        verifyNoMoreInteractions(footballDao);

        assertEquals(insertedNumberOfGoals, returnedValue);

    }

           /*
        update Scorers data
        return Failure
     */

    @Test
    void updateScorersData_returnFailure() throws Exception {
        //Arrange
        Scorers[] scorers = new Scorers[]{TestUtil.TEST_SCORERS_DATA_1};
        int insertedNumberOfGoals = scorers[0].getNumberOfGoals();
        String insertedTeamName = scorers[0].getTeam().getName();
        String insertedPlayerName = scorers[0].getPlayer().getPlayerName();

        when(footballDao.updateScorers(insertedNumberOfGoals, insertedTeamName, insertedPlayerName))
                .thenReturn(scorers[0].getNumberOfGoals());

        //Act
        int returnedValue = footballRepo.updateScorersTable(TestUtil.NUMBER_OF_GOALS_1,
                TestUtil.TEAM_NAME_1, TestUtil.PLAYER_1);

        //Assert
        verify(footballDao).updateScorers(insertedNumberOfGoals, insertedTeamName, insertedPlayerName);
        verifyNoMoreInteractions(footballDao);

        assertNotEquals(4, returnedValue);

    }
}
