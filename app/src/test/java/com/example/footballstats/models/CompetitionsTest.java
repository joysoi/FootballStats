package com.example.footballstats.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionsTest {

    private static final int TIMESTAMP_1 = 6;
    private static final int TIMESTAMP_2 = 5;

    /*
      Compare two equal Competitions
     */

    @Test
    void areCompetitionsEqual_sameProperties_returnTrue() throws Exception {
        //Arrange
        Competitions competitions1 = new Competitions("Premier League", TIMESTAMP_1);
        competitions1.setCompetitionId(1);
        Competitions competitions2 = new Competitions("Premier League", TIMESTAMP_1);
        competitions2.setCompetitionId(1);
        //Act

        //Assert
        assertEquals(competitions1, competitions2);
        System.out.println("The competitions are equal");
    }

    /*
            Compare Competitions with two different ids
         */
    @Test
    void areCompetitionsEqual_diffIds_returnFalse() throws Exception {
        //Arrange
        Competitions competitions1 = new Competitions("Premier League", TIMESTAMP_1);
        competitions1.setCompetitionId(1);
        Competitions competitions2 = new Competitions("Premier League", TIMESTAMP_1);
        competitions2.setCompetitionId(2);
        //Act

        //Assert
        assertNotEquals(competitions1, competitions2);
        System.out.println("The competitions are not equal");
    }


    /*
    Compare Competitions with different timestamps
 */
    @Test
    void areCompetitionsEqual_diffTimestamps_returnTrue() throws Exception {
        //Arrange
        Competitions competitions1 = new Competitions("Premier League", TIMESTAMP_1);
        competitions1.setCompetitionId(1);
        Competitions competitions2 = new Competitions("Premier League", TIMESTAMP_2);
        competitions2.setCompetitionId(1);
        //Act

        //Assert
        assertEquals(competitions1, competitions2);
        System.out.println("The competitions are equal with a different time stamp");
    }


        /*
        Compare Competitions with different names
     */

    @Test
    void areCompetitionsEqual_diffNames_returnFalse() throws Exception {
        //Arrange
        Competitions competitions1 = new Competitions("Premier League", TIMESTAMP_1);
        competitions1.setCompetitionId(1);
        Competitions competitions2 = new Competitions("Serie A", TIMESTAMP_1);
        competitions2.setCompetitionId(1);
        //Act

        //Assert
        assertNotEquals(competitions1, competitions2);
        System.out.println("The competitions are equal! The have different names!");
    }

}
