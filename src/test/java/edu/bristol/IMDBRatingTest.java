package edu.bristol;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IMDBRatingTest
{   //Example code -> Release Testing
    @Test
    public void bristol() throws IOException {
        URL url = new URL("https://www.imdb.com/search/title/?locations=bristol&role=nm0263368");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String nextLine = reader.readLine();

        //IMDB code
        ArrayList<String> episodes = new ArrayList<>();
        while (nextLine != null) {
            if (nextLine.contains("Episode:")){
                nextLine = reader.readLine();
                nextLine = reader.readLine();
                episodes.add(nextLine);
            }
            nextLine = reader.readLine();
        }

        //Sanitise episodes
        sanitiseEpisodes(episodes);

        //Wiki code
        URL wikiurl = new URL("https://en.wikipedia.org/wiki/Shoestring_(TV_series)#Episodes");
        HttpURLConnection nconnection = (HttpURLConnection) wikiurl.openConnection();
        nconnection.setDoOutput(true);
        nconnection.setRequestMethod("GET");
        InputStream nstream = nconnection.getInputStream();
        BufferedReader nreader = new BufferedReader(new InputStreamReader(nstream));
        String nnextLine = nreader.readLine();

        ArrayList<String> wiki = new ArrayList<>();
        while (nnextLine != null) {

            boolean found = false;
            for (String word : nnextLine.split(" ")){
                if (found == true){
                    wiki.add(word);
                }
                if (word.equals("class=\"anchor\"")) {
                    found = true;
                } else {
                    found = false;
                }
                //System.out.println(word);
            }
            nnextLine = nreader.readLine();
        }

        for (int i=0; i < wiki.size(); i++){
            String temp = wiki.get(i);
            temp = temp.substring(4, temp.lastIndexOf("\""));
            temp = temp.replace("_", " ");
            wiki.set(i, temp);
        }

        ArrayList<String> missing = new ArrayList<>();

        for (int i=0; i < wiki.size(); i++){
            boolean notfound = true;
            for (int j=0; j < episodes.size(); j++){
                if (wiki.get(i).equals(episodes.get(j))){
                    notfound = false;
                }
            }
            if (notfound == true) {
                missing.add(wiki.get(i));
            }
        }

        System.out.println(missing);
    }

    public void sanitiseEpisodes(ArrayList<String> episodes){
        for (int i=0; i < episodes.size(); i++){
            String temp = episodes.get(i);
            temp = temp.substring(1, temp.lastIndexOf("<"));
            episodes.set(i, temp);
        }
    }

    @Test
    public void testAverageRating()
    {
        float averageRating;
        IMDBRating rater = new IMDBRating();
        averageRating = rater.addNewRating(2);
        assertTrue(averageRating == 2.0, "Adding 1st rating: average should be 2.0");

        averageRating = rater.addNewRating(4);
        assertTrue(averageRating == 3.0, "Adding 2nd rating: average should be 3.0");

        averageRating = rater.addNewRating(6);
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