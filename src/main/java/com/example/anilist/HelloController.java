package com.example.anilist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class HelloController {

    public void initialize() throws Exception {
        String jsonAnime=Anime.getJSONfromURL("https://api.jikan.moe/v4/random/anime");
        System.out.println("JSONs: " + jsonAnime);

        // Read JSON objects using JsonNode after readTree()
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonAnime);
        // By reading the JSON tree, the code can now get individual "key":"value" pairs
        // The value of the "result" key is an ARRAY of JSON objects
        JsonNode arrayOfAnime = jsonNode.get("data");
        String unfiltheredDuration = arrayOfAnime.get("duration").asText();
        String[] splitedDurations = unfiltheredDuration.split(" ");
        int totalDuration = 0;
        for (String string : splitedDurations) {
            if (string.equals("hr")) {
                //totalDuration working on adding the hours and minutes together to get the total duration
            }
        }
        System.out.println(splitedDurations.toString());
    }

}
