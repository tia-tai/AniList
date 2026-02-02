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
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() throws Exception {
        String yourAPIurl = "https://api.jikan.moe/v4/random/anime";
        String yourAPIkey = "";
        URL APIurl = new URL(yourAPIurl);
        HttpURLConnection APIconnection = (HttpURLConnection) APIurl.openConnection();
        APIconnection.setRequestMethod("GET");
        APIconnection.setRequestProperty("Accept", "application/json");
        if (yourAPIkey != null) {
            APIconnection.setRequestProperty("Authorization", yourAPIkey);
        }
        InputStreamReader APIinStream = new InputStreamReader(APIconnection.getInputStream());
        BufferedReader APIreader = new BufferedReader(APIinStream);
        StringBuilder JSONstring = new StringBuilder();
        String line;
        while ((line = APIreader.readLine()) != null) {
            JSONstring.append(line);
        }
        APIreader.close();
        System.out.println(JSONstring);
        // JSONString has already been read from URL
        // read 1 JSON object ("key":"value" pairs) into fields of MODEL object
        ObjectMapper objectMapper = new ObjectMapper();
        MYMODEL myData = objectMapper.readValue(JSONstring, MYMODEL.class);
        System.out.println("OBJECT: " + myData);
    }

}
