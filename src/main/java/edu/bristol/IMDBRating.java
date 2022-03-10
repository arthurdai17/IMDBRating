
package edu.bristol;
public class IMDBRating
{
    private float currentAverage = 0;
    private int ratingCount = 0;
    public float addNewRating(int newRating)
    {
        int previousTotal = (int) (currentAverage * ratingCount);
        int newTotal = previousTotal + newRating; //32
        ratingCount++; //6
        // 32/6 = 5.33F
        currentAverage = (float) newTotal / ratingCount; // 5.714285F
        return currentAverage;
    }
}