package com.example.anilist;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import static com.example.anilist.Anime.getJSONfromURL;

public class anilistController {
    public GridPane animeList;

    public AnchorPane homePane;
    public AnchorPane animePane;
    public AnchorPane studioPane;

    public ImageView animeCover;
    public Label animeTitle;
    public Label animeDescription;
    public Label jpTitle;
    public Label genre;
    public Label type;
    public Label episodes;
    public Label duration;
    public Label season;
    public Label finishedAiring;
    public Label airDate;
    public Label rating;
    public Label popularity;
    public Label studio;
    public Button favorite;

    public Label studioName;
    public Label studioDescription;
    public GridPane studioProductions;
    public ImageView studioImage;

    public void initialize() throws Exception {
        Anime.searchAnime();

        int column = 0;
        int row = 0;

        for (Anime anime : Anime.getAnimeList()) {
            Image animeImage = new Image(anime.getImageUrl());
            ImageView animeCover = new ImageView(animeImage);
            animeCover.setPreserveRatio(true);
            animeCover.setX(80);

            Button animeButton = new Button(anime.getTitle());
            animeButton.setGraphic(animeCover);
            animeButton.setContentDisplay(ContentDisplay.TOP);
            animeButton.setId(anime.getTitle());
            animeButton.setOnAction(this::searchAnime);

            animeList.add(animeButton, column, row); // Column 1, Row 1
            if (column == 2) {
                column = 0;
                row++;
            } else {
                column++;
            }
        }
    }

    public void searchAnime(ActionEvent event) {
        Button animeButton = (Button) event.getSource();
        String animeName = animeButton.getId();
        Anime currentAnime = null;
        for (Anime anime : Anime.getAnimeList()) {
            if (anime.getTitle().equals(animeName)) {
                currentAnime = anime;
            }
        }

        Image animeImage = new Image(currentAnime.getImageUrl());
        animeCover.setImage(animeImage);
        animeTitle.setText(currentAnime.getTitle());
        animeDescription.setText(currentAnime.getSynopsis());
        jpTitle.setText("JP Title: " + currentAnime.getTitleJP());
        genre.setText("Genre: " + currentAnime.getGenre());;
        type.setText("Type: " + currentAnime.getType());;
        episodes.setText("Episodes: " + currentAnime.getEpisodes());
        duration.setText("Duration: " + currentAnime.getDurationMinutes() + " Minutes");
        season.setText("Season: " + currentAnime.getSeason());
        finishedAiring.setText("Finished Airing: " + currentAnime.isFinishedAiring());
        airDate.setText("Air Date: " + currentAnime.getAirDate());
        rating.setText("Rating: " + currentAnime.getRating());
        popularity.setText("Popularity: " + currentAnime.getPopularity());
        studio.setText("Studio: " + currentAnime.getStudio().getStudioName());
        studio.setId(currentAnime.getStudio().getStudioName());

        if (currentAnime.isFavorite()){
            favorite.setStyle("-fx-background-color: #C4B454; ");
        } else {
            favorite.setStyle("-fx-background-color: #FDFDFD; ");
        }

        homePane.setDisable(true);
        homePane.setVisible(false);
        studioPane.setDisable(true);
        studioPane.setVisible(false);
        animePane.setDisable(false);
        animePane.setVisible(true);
    }

    public void setFavorite() {
        String animeName = animeTitle.getText();
        Anime currentAnime = null;
        for (Anime anime : Anime.getAnimeList()) {
            if (anime.getTitle().equals(animeName)) {
                currentAnime = anime;
            }
        }

        currentAnime.setFavorite(!currentAnime.isFavorite());

        if (currentAnime.isFavorite()){
            favorite.setStyle("-fx-background-color: #C4B454; ");
        } else {
            favorite.setStyle("-fx-background-color: #FDFDFD; ");
        }
    }

    public void exitAnimePane() {
        homePane.setDisable(false);
        homePane.setVisible(true);
        animePane.setDisable(true);
        animePane.setVisible(false);
    }

    public void searchStudio(MouseEvent event) throws Exception {
        Label studioLabel = (Label) event.getSource();
        String studioname = studioLabel.getText().replace("Studio: ", "");
        Studio currentStudio = null;

        for (Studio studio : Studio.getStudios()) {
            if (studio.getStudioName().equals(studioname)) {
                currentStudio = studio;
                break;
            }
        }
        assert currentStudio != null;
        if (currentStudio.getDescription() == null) {
            String jsonStudio=Anime.getJSONfromURL("https://api.jikan.moe/v4/producers/" + currentStudio.getStudioId());
            System.out.println("JSONs: " + jsonStudio);

            // Read JSON objects using JsonNode after readTree()
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonStudio);
            // By reading the JSON tree, the code can now get individual "key":"value" pairs
            // The value of the "result" key is an ARRAY of JSON objects
            JsonNode studioInfo = jsonNode.get("data");
            currentStudio.setDescription(studioInfo.get("about").asText());
            currentStudio.setImageURL(studioInfo.get("images").get("jpg").get("image_url").asText());
        }

        studioName.setText(studioname);
        studioDescription.setText(currentStudio.getDescription());
        Image studioimg = new Image(currentStudio.getImageURL());
        studioImage.setImage(studioimg);

        int column = 0;
        int row = 0;

        for (Anime anime : currentStudio.getProducedAnimes()) {
            Image animeImage = new Image(anime.getImageUrl());
            ImageView animeCover = new ImageView(animeImage);
            animeCover.setPreserveRatio(true);
            animeCover.setX(80);

            Button animeButton = new Button(anime.getTitle());
            animeButton.setGraphic(animeCover);
            animeButton.setContentDisplay(ContentDisplay.TOP);
            animeButton.setId(anime.getTitle());
            animeButton.setOnAction(this::searchAnime);

            studioProductions.add(animeButton, column, row);
            if (column == 1) {
                column = 0;
                row++;
            } else {
                column++;
            }
        }

        homePane.setDisable(true);
        homePane.setVisible(false);
        animePane.setDisable(true);
        animePane.setVisible(false);
        studioPane.setDisable(false);
        studioPane.setVisible(true);
    }

    public void exitStudioPane() {
        homePane.setDisable(false);
        homePane.setVisible(true);
        studioPane.setDisable(true);
        studioPane.setVisible(false);
    }
}
