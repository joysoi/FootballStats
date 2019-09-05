package com.example.footballstats.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {


    /*
        Compare two different Teams names
     */
    @Test
    void areTeamsEqual_diffTeamsNames_returnFalse() throws Exception {
        //Arrange
        Team team1 = new Team("Arsenal");
        Team team2 = new Team("Liverpool");
        //Act

        //Assert
        assertNotEquals(team1,team2);
        System.out.println("They are not equal");
    }

    /*
        Compare two same Teams names
     */
    @Test
    void areTeamsEqual_sameTeamNames_returnTrue() throws Exception {
        //Arrange
        Team team1 = new Team("Arsenal");
        Team team2 = new Team("Arsenal");
        //Act

        //Assert
        assertEquals(team1,team2);
        System.out.println("They are equal");
    }

}
