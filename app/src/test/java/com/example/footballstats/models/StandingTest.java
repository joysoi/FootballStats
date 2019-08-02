package com.example.footballstats.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandingTest {
    private static final String TYPE = "Total";

    /*
        are standing of Total games of season presented
     */

    @Test
    void areStandingsFull_typeTotal_returnTrue() throws Exception {
        //Arrange
        Standing standing1 = new Standing(TYPE);
        Standing standing2 = new Standing(TYPE);
        //Act

        //Assert
        assertEquals(standing1, standing2);
        System.out.println("Standings are Total");
    }
}
