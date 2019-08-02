package com.example.footballstats.repository;

import com.bumptech.glide.load.engine.Resource;
import com.example.footballstats.models.Competitions;
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
        confirm that the observer is triggered
        confirm that new rows are being inserted
     */
    @Test
    void insertCompetition_returnRow() throws Exception {
        //Arrange
        final long insertedRow = 1L;
        final List<Long> insertedRowsList = new ArrayList<>();
        insertedRowsList.add(insertedRow);
        final long[] returnedData = new long[insertedRowsList.size()];
        when(footballDao.insertCompetitions(any(Competitions[].class)))
                .thenReturn(returnedData);

        //Act
        Competitions[] returnedValue = footballRepo.insertCompetitions(COMPETITIONS_LIST).blockingFirst();


        //Assert
        verify(footballDao).insertCompetitions(any(Competitions[].class));
        verifyNoMoreInteractions(footballDao);

        assertEquals(COMPETITIONS_LIST.toArray(returnedValue), returnedValue);

    }


    /*
        insert Competition
        return Failure
     */
    @Test
    void insertCompetition_returnFailure() throws Exception {
        //Arrange
        final long insertedRow = -1L;
        final List<Long> insertedRowList = new ArrayList<>();
        insertedRowList.add(insertedRow);
        final long[] returnedData = new long[insertedRowList.size()];
        when(footballDao.insertCompetitions(any(Competitions[].class)))
                .thenReturn(returnedData);

        //Act
        Competitions[] returnedValue = footballRepo.insertCompetitions(COMPETITIONS_LIST).blockingFirst();

        //Assert
        verify(footballDao).insertCompetitions(any(Competitions[].class));
        verifyNoMoreInteractions(footballDao);

        assertNotEquals(insertedRow, returnedValue);
    }


    /* update Competition */

    /*
        verify the correct method is called
        confirm that the observer is triggered
        confirm that new rows are being inserted
     */
//    @Test
//    void updateCompetition_returnRowsUpdated() throws Exception {
//        //Arrange
//        Competitions[] competitions = new Competitions[]{TestUtil.TEST_COMPETITIONS_1};
//        int insertedValueId = competitions[0].getCompetitionId();
//        String insertedValueName = competitions[0].getCompetitionName();
//        when(footballDao.updateCompetitions(insertedValueId, insertedValueName))
//                .thenReturn(competitions[0].getCompetitionId());
//
//        //Act
//        int returnedValue = footballRepo.updateCompetitions(TestUtil.COMP_ID_1, TestUtil.COMP_NAME_1);
//
//        //Assert
//        verify(footballDao, atLeast(1)).updateCompetitions(insertedValueId, insertedValueName);
//        verifyNoMoreInteractions(footballDao);
//
//        assertEquals(insertedValueId, returnedValue);
//    }


      /*
        update Competition
        return Failure
     */

//    @Test
//    void updateCompetition_returnFailure() throws Exception {
//        //Arrange
//        Competitions[] competitions = new Competitions[]{TestUtil.TEST_COMPETITIONS_2};
//        int insertedValueId = competitions[0].getCompetitionId();
//        String insertedValueName = competitions[0].getCompetitionName();
//        when(footballRepo.updateCompetitions(insertedValueId, insertedValueName))
//                .thenReturn(competitions[0].getCompetitionId());
//
//        //Act
//        int returnedValue = footballRepo.updateCompetitions(TestUtil.COMP_ID_1, TestUtil.COMP_NAME_1);
//
//        //Assert
//        verify(footballDao, atLeast(1)).updateCompetitions(TestUtil.COMP_ID_1,  TestUtil.COMP_NAME_1);
//        verifyNoMoreInteractions(footballDao);
//
//        assertNotEquals(insertedValueId, returnedValue);
//    }
}
