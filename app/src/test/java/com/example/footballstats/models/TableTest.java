package com.example.footballstats.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TableTest {
    /*
      1. Compare two Teams same id
      2. Compare two equal Teams with equal positions and equal points
      3. Compare two equal Teams with equal position and diff points
      4. Compare two equal Teams with diff positions and diff points
      5. Compare two diff Teams with diff positions and diff points
      6. Compare two diff Teams with equal positions and diff points
      7. Compare two diff Teams with diff positions and equal points
     */

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


    //  Compare two Teams same id
    @Test
    void areTableTeamsEqual_sameId_returnFalse() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(1, team1, 10);
        tableTeam1.setTeamId(1);
        Table tableTeam2 = new Table(2, team2, 9);
        tableTeam2.setTeamId(1);
        //Act

        //Assert
        assertNotEquals(tableTeam1, tableTeam2);
        System.out.println("They are not equal");
    }

    //  Compare two equal Teams with equal positions and equal points
    @Test
    void areTableTeamsEqual_diffPositionSamePoints_returnFalse() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(1, team1, 10);
        tableTeam1.setTeamId(1);
        Table tableTeam2 = new Table(1, team1, 10);
        tableTeam2.setTeamId(1);
        //Act

        //Assert
        assertEquals(tableTeam1, tableTeam2);
        System.out.println("They are equal");
    }

    //  Compare two equal Teams with equal position and diff points
    @Test
    void areTeamsEqual_samePositionAndDiffPoints_returnFalse() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(1, team1, 10);
        tableTeam1.setTeamId(1);
        Table tableTeam2 = new Table(1, team1, 12);
        tableTeam2.setTeamId(1);
        //Act

        //Assert
        assertNotEquals(tableTeam1, tableTeam2);
        System.out.println("They are not equal");
    }

    //  Compare two equal Teams with diff positions and diff points
    @Test
    void areTeamsEqual_diffPositionAndDiffPoints_returnFalse() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(2, team1, 10);
        tableTeam1.setTeamId(1);
        Table tableTeam2 = new Table(1, team1, 12);
        tableTeam2.setTeamId(1);
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
        tableTeam1.setTeamId(1);
        Table tableTeam2 = new Table(1, team2, 12);
        tableTeam2.setTeamId(2);
        //Act

        //Assert
        assertNotEquals(tableTeam1, tableTeam2);
        System.out.println("They are not equal");
    }

    //  Compare two diff Teams with equal positions and diff points
    @Test
    void areTeamsEqual_diffTeamNamesAndSamePositionAndDiffPoints_returnTrue() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(1, team1, 10);
        tableTeam1.setTeamId(1);
        Table tableTeam2 = new Table(1, team2, 12);
        tableTeam2.setTeamId(2);
        //Act

        //Assert
        assertNotEquals(tableTeam1, tableTeam2);
        System.out.println("They are not equal");
    }

    // Compare two diff Teams with diff positions and equal points
    @Test
    void areTeamsEqual_diffTeamNamesAndDiffPositionAndSamePoints_returnTrue() throws Exception {
        //Arrange
        Table tableTeam1 = new Table(1, team1, 10);
        tableTeam1.setTeamId(1);
        Table tableTeam2 = new Table(2, team2, 10);
        tableTeam2.setTeamId(2);
        //Act

        //Assert
        assertNotEquals(tableTeam1, tableTeam2);
        System.out.println("They are not equal");
    }
}
