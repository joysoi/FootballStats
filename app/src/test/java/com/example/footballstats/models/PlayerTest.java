package com.example.footballstats.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PlayerTest {
    /*
      Compare two equal Players with equal id
     */

    @Test
    void arePlayersEqual_sameProperties_returnTrue() throws Exception {
        //Arrange
        Player player1 = new Player("Ronaldo");
        player1.setIdPlayer(1);
        Player player2 = new Player("Ronaldo");
        player2.setIdPlayer(1);
        //Act

        //Assert
        assertEquals(player1, player2);
        System.out.println("The players are equal");
    }

    /*
            Compare Players with two different ids
         */
    @Test
    void arePlayersEqual_diffIds_returnFalse() throws Exception {
        //Arrange
        Player player1 = new Player("Ronaldo");
        player1.setIdPlayer(1);
        Player player2 = new Player("Ronaldo");
        player2.setIdPlayer(2);
        //Act

        //Assert
        assertNotEquals(player1, player2);
        System.out.println("The players are not equal");
    }


        /*
        Compare Players with different names
     */

    @Test
    void arePlayersEqual_diffNames_returnFalse() throws Exception {
        //Arrange
        Player player1 = new Player("Ronaldo");
        player1.setIdPlayer(1);
        Player player2 = new Player("Benzema");
        player2.setIdPlayer(2);
        //Act

        //Assert
        assertNotEquals(player1, player2);
        System.out.println("The players are equal! The have different names!");
    }

}
