package edu.bristol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IMDBRatingTest
{
    @Test
    public void testAverageRating()
    {
        float averageRating;
        IMDBRating rater = new IMDBRating();
        averageRating = rater.addNewRating(2);
        assertTrue(averageRating == 2.0, "Adding 1st rating: average should be 2.0");

        averageRating = rater.addNewRating(4);
        assertTrue(averageRating == 3.0, "Adding 2nd rating: average should be 3.0");

        averageRa = rater.addNewRating(6);
        assertTrue(averageRating == 4.0, "Adding 3rd rating: average should be 4.0");

        averageRating = rater.addNewRating(4);
        assertEquals(averageRating == 4.0, true);

        averageRating = rater.addNewRating(9); //25
        assertTrue(averageRating == 5.0, "Adding 5th rating: average should be 5.0");

        averageRating = rater.addNewRating(7); //32
        assertTrue(averageRating == 5.3333333333F, "Adding 6th rating: average should be 5.333.....");

        averageRating = rater.addNewRating(10); //40
        assertFalse(averageRating == 5.714285714285F, "Adding 7th rating: average should be 5.714285.....");
    }
}