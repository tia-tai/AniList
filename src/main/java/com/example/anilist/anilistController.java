package com.example.anilist;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class anilistController {
    public GridPane animeList;

    public AnchorPane homePane;
    public AnchorPane animePane;

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
}
