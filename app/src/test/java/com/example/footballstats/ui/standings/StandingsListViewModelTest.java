package com.example.footballstats.ui.standings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.util.InstantExecutorExtension;
import com.example.footballstats.util.LiveDataTestUtil;
import com.example.footballstats.util.Resource;
import com.example.footballstats.util.TestUtil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


@ExtendWith(InstantExecutorExtension.class)
public class StandingsListViewModelTest {

    //system under test StandingsListViewModel
    private StandingsListViewModel standingsListViewModel;


    @Mock
    FootballRepo footballRepo;

    @BeforeEach
    private void init() {
        footballRepo = mock(FootballRepo.class);
        standingsListViewModel = new StandingsListViewModel(footballRepo);
    }


        /*
       observe Feed when set
     */

    @Test
    void observeFeedWhenSuccessful() throws Exception {
        // Arrange


        // Act


        // Assert

    }


    @AfterEach
    private void tearDown() throws Exception {
        footballRepo = null;
    }

}
