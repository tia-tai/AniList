package com.example.anilist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController {
    public TableView<Anime> animeData;

    public TableColumn<Anime, String> nameColumn;
    public TableColumn<Anime, String> titleJPColumn;
    public TableColumn<Anime, String> urlColumn;
    public TableColumn<Anime, String> typeColumn;
    public TableColumn<Anime, Integer> episodesColumn;
    public TableColumn<Anime, Boolean> finishedAiringColumn;
    public TableColumn<Anime, LocalDate> airDateColumn;
    public TableColumn<Anime, Integer> durationColumn;
    public TableColumn<Anime, Integer> ratingColumn;
    public TableColumn<Anime, Integer> popularityColumn;
    public TableColumn<Anime, Integer> seasonColumn;
    public TableColumn<Anime, String> genreColumn;

    public ImageView AnimeCover;
    public Label AnimeName;

    public void initialize() throws Exception {
        for (int i = 0; i < 10; i++) {
            Anime.searchAnime();
        }

        for (Anime anime : Anime.getAnimeList()) {
            animeData.getItems().add(anime);
            wait(1000);
        }

        String imageUrl = Anime.getAnimeList().getFirst().getImageUrl();
        String name = Anime.getAnimeList().getFirst().getTitle();
        Image image = new Image(imageUrl, true);
        AnimeCover.setImage(image);
        AnimeName.setText(name);
        animeData.getSelectionModel().select(Anime.getAnimeList().getFirst());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleJPColumn.setCellValueFactory(new PropertyValueFactory<>("titleJP"));
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        episodesColumn.setCellValueFactory(new PropertyValueFactory<>("episodes"));
        finishedAiringColumn.setCellValueFactory(new PropertyValueFactory<>("finishedAiring"));
        airDateColumn.setCellValueFactory(new PropertyValueFactory<>("airDate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationMinutes"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        popularityColumn.setCellValueFactory(new PropertyValueFactory<>("popularity"));
        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("season"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }

}
