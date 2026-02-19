package com.example.anilist;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class anilistController {
    public GridPane animeList;

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

            animeList.add(animeButton, column, row); // Column 1, Row 1
            if (column == 2) {
                column = 0;
                row++;
            } else {
                column++;
            }
        }
    }
}
