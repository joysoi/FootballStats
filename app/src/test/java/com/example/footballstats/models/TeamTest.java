package com.example.footballstats.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    /*
        Compare two equal Teams with equal id
     */

    @Test
    void areTeamsEqual_sameId_returnTrue() throws Exception {
        //Arrange
        Team team1 = new Team("Arsenal");
        team1.setIdTeam(1);
        Team team2 = new Team("Arsenal");
        team2.setIdTeam(1);
        //Act

        //Assert
        assertEquals(team1,team2);
        System.out.println("They are equal");
    }

    /*
        Compare two equal Teams with different id
     */

    @Test
    void areTeamsEqual_differentId_returnFalse() throws Exception {
        //Arrange
        Team team1 = new Team("Arsenal");
        team1.setIdTeam(1);
        Team team2 = new Team("Arsenal");
        team2.setIdTeam(2);
        //Act

        //Assert
        assertNotEquals(team1,team2);
        System.out.println("They are not equal");
    }

    /*
        Compare two different Teams with same id
     */
    @Test
    void areTeamsEqual_diffTeamsSameId_returnFalse() throws Exception {
        //Arrange
        Team team1 = new Team("Arsenal");
        team1.setIdTeam(1);
        Team team2 = new Team("Liverpool");
        team2.setIdTeam(1);
        //Act

        //Assert
        assertNotEquals(team1,team2);
        System.out.println("They are not equal");
    }

    /*
        Compare two different Teams with diff id
     */
    @Test
    void areTeamsEqual_diffTeamsDiffId_returnTrue() throws Exception {
        //Arrange
        Team team1 = new Team("Arsenal");
        team1.setIdTeam(1);
        Team team2 = new Team("Liverpool");
        team2.setIdTeam(2);
        //Act

        //Assert
        assertNotEquals(team1,team2);
        System.out.println("They are not equal");
    }

}
