package com.example.footballstats.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ScorersTest {

    private Player player1, player2 = null;
    private Team team1, team2 = null;

    @BeforeEach
    void beforeAllTests() {
        player1 = new Player("Ronaldo");
        team1 = new Team("Juventus");

        player2 = new Player("Benzema");
        team2 = new Team("Real Madrid");


        Scorers scorers1 = new Scorers();
        Scorers scorers2 = new Scorers();

        scorers1.setPlayer(player1);
        scorers1.setTeam(team1);


        scorers2.setPlayer(player2);
        scorers2.setTeam(team2);
    }


      /*
      Compare two equal Scorers
     */

    @Test
    void areScorersEqual_sameValue_returnTrue() throws Exception {
        //Arrange
        Scorers scorer1 = new Scorers(player1, team1);
        scorer1.setId_Scorers(1);
        Scorers scorer2 = new Scorers(player1, team1);
        scorer2.setId_Scorers(1);
        //Act

        //Assert
        assertEquals(scorer1, scorer2);
        System.out.println("The scorers are equal");
    }


    /*
             Compare Scorers with two different ids
         */

    @Test
    void areScorersEqual_diffIds_returnFalse() throws Exception {
        //Arrange
        Scorers scorer1 = new Scorers(player1, team1);
        scorer1.setId_Scorers(1);
        Scorers scorer2 = new Scorers(player1, team1);
        scorer2.setId_Scorers(2);
        //Act

        //Assert
        assertNotEquals(scorer1, scorer2);
        System.out.println("The scorers are not equal");
    }

    /*
        Compare same Scorers from a different teams
     */

    @Test
    void areScorersEqual_diffTeams_returnFalse() throws Exception {
        //Arrange
        Scorers scorer1 = new Scorers(player1, team1);
        scorer1.setId_Scorers(1);
        Scorers scorer2 = new Scorers(player1, team2);
        scorer2.setId_Scorers(2);
        //Act

        //Assert
        assertNotEquals(scorer1, scorer2);
        System.out.println("The scorers are not equal");
    }


    /*
        Compare different Scorers from a same team
         */

    @Test
    void areScorersDiff_sameTeam_returnTrue() throws Exception {
        //Arrange
        Scorers scorer1 = new Scorers(player1, team1);
        scorer1.setId_Scorers(1);
        Scorers scorer2 = new Scorers(player2, team1);
        scorer2.setId_Scorers(2);
        //Act

        //Assert
        assertNotEquals(scorer1, scorer2);
        System.out.println("The scorers are not equal");
    }


    /*
        Compare different Scorers, same team with same number of goals
         */

    @Test
    void areScorersSame_diffNumberGoals_returnFalse() throws Exception {
        //Arrange
        Scorers scorer1 = new Scorers(3, player1, team1);
        scorer1.setId_Scorers(1);
        Scorers scorer2 = new Scorers(3, player2, team1);
        scorer2.setId_Scorers(2);
        //Act

        //Assert
        assertNotEquals(scorer1, scorer2);
        System.out.println("The scorers are not equal");
    }

      /*
        Compare same Scorers, same team with same number of goals
         */

    @Test
    void areScorersSame_sameValues_returnTrue() throws Exception {
        //Arrange
        Scorers scorer1 = new Scorers(3, player1, team1);
        scorer1.setId_Scorers(1);
        Scorers scorer2 = new Scorers(3, player1, team1);
        scorer2.setId_Scorers(1);
        //Act

        //Assert
        assertEquals(scorer1, scorer2);
        System.out.println("The scorers are equal");
    }

     /*
        Compare diff Scorers, diff team with diff number of goals
         */

    @Test
    void areScorersEqual_diffValues_returnFalse() throws Exception {
        //Arrange
        Scorers scorer1 = new Scorers(3, player1, team1);
        scorer1.setId_Scorers(1);
        Scorers scorer2 = new Scorers(4, player2, team2);
        scorer2.setId_Scorers(2);
        //Act

        //Assert
        assertNotEquals(scorer1, scorer2);
        System.out.println("The scorers are not equal");
    }
}
