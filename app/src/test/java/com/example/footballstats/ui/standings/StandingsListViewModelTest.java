package com.example.footballstats.ui.standings;

import androidx.lifecycle.Observer;

import com.example.footballstats.models.Competitions;
import com.example.footballstats.repository.FootballRepo;
import com.example.footballstats.util.InstantExecutorExtension;
import com.example.footballstats.util.LiveDataTestUtil;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


@ExtendWith(InstantExecutorExtension.class)
public class StandingsListViewModelTest {
    //system under test StandingsListViewModel
    StandingsListViewModel standingsListViewModel;

    @Mock
    FootballRepo footballRepo;

    @Mock
    Observer<List<Competitions>> listObserver;

    @BeforeEach
    private void init() {
        MockitoAnnotations.initMocks(this);
        standingsListViewModel = new StandingsListViewModel(footballRepo);
        standingsListViewModel.getFeed().observeForever(listObserver);
    }


    @Test
    private void testNull(){
        when(footballRepo.observeFeed()).thenReturn(null);
        assertNotNull(standingsListViewModel.getFeed());
        assertTrue(standingsListViewModel.getFeed().hasObservers());
    }


    @AfterEach
    private void tearDown() throws Exception{
        footballRepo = null;
    }

}
