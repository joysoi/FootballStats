package com.example.footballstats.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TableTest {

    private Team team1;
    private Team team2;

    @BeforeEach
    private void beforeEach() {
        team1 = new Team("Arsenal");
        team2 = new Team("Chelsea");
        Table tableTeamName1 = new Table();
        Table tableTeamName2 = new Table();
        tableTeamName1.setTeam(team1);
        tableTeamName2.setTeam(team2);
    }


    //  Compare two equal Teams with equal positions and equal points
    @Test
    void areTableTeamsEqual_diffPositionSamePoints_returnFalse() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(1, team1, 10);
        Table tableTeam2 = new Table(1, team1, 10);
        //Act

        //Assert
        assertEquals(tableTeam1, tableTeam2);
        System.out.println("They are equal");
    }



    //  Compare two equal Teams with diff positions and diff points
    @Test
    void areTeamsEqual_diffPositionAndDiffPoints_returnFalse() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(2, team1, 10);
        Table tableTeam2 = new Table(1, team1, 12);
        //Act

        //Assert
        assertNotEquals(tableTeam1, tableTeam2);
        System.out.println("They are not equal");
    }

    //  Compare two diff Teams with diff positions and diff points
    @Test
    void areTeamsEqual_diffTeamNamesAndDifferentPositionAndPoints_returnTrue() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(2, team1, 10);
        Table tableTeam2 = new Table(1, team2, 12);
        //Act

        //Assert
        assertNotEquals(tableTeam1, tableTeam2);
        System.out.println("They are not equal");
    }

}
