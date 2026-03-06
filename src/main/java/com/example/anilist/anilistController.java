package com.example.anilist;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.*;

import static com.example.anilist.Anime.getJSONfromURL;

public class anilistController {
    public GridPane animeList;

    public AnchorPane homePane;
    public AnchorPane animePane;
    public AnchorPane studioPane;
    public AnchorPane savePane;
    public AnchorPane genrePane;

    public ChoiceBox<String> filter;

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

    public HBox savedList;

    public Label genreLabel;
    public GridPane genreList;

    private int mainColumn = 0;
    private int mainRow = 0;

//    private ArrayList<String> navigation = new ArrayList<>();

    public void initialize() throws Exception {
        Anime.searchAnime();

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

            animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
            if (mainColumn == 2) {
                mainColumn = 0;
                mainRow++;
            } else {
                mainColumn++;
            }
        }

        filter.getItems().add("Title A-Z");
        filter.getItems().add("Title Z-A");
        filter.getItems().add("Year Ascending");
        filter.getItems().add("Year Descending");
        filter.getItems().add("Popularity Ascending");
        filter.getItems().add("Popularity Descending");
        filter.getItems().add("Movie");
        filter.getItems().add("Anime Series");
    }

    public void loadAnime() throws Exception {
        int currentItem = 0;
        if (filter.getValue() == null) {
            currentItem = (mainRow * 3) + mainColumn;
        } else {
            mainRow = 0;
            mainColumn = 0;
        }
        Anime.searchAnime();

        for (Anime anime : Anime.getAnimeList().subList(currentItem, Anime.getAnimeList().size())) {
            Image animeImage = new Image(anime.getImageUrl());
            ImageView animeCover = new ImageView(animeImage);
            animeCover.setPreserveRatio(true);
            animeCover.setX(80);

            Button animeButton = new Button(anime.getTitle());
            animeButton.setGraphic(animeCover);
            animeButton.setContentDisplay(ContentDisplay.TOP);
            animeButton.setId(anime.getTitle());
            animeButton.setOnAction(this::searchAnime);

            animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
            if (mainColumn == 2) {
                mainColumn = 0;
                mainRow++;
            } else {
                mainColumn++;
            }
        }
        filter.setValue(null);
    }

    public void searchAnime(ActionEvent event) {
        Button animeButton = (Button) event.getSource();
        String animeName = animeButton.getId();
        Anime currentAnime = null;
        //navigation.add("Anime: " + currentAnime);
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
        savePane.setDisable(true);
        savePane.setVisible(false);
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

    public void exitAnimePane() throws Exception {
        homePane.setDisable(false);
        homePane.setVisible(true);
        animePane.setDisable(true);
        animePane.setVisible(false);
    }

    public void searchStudio() throws Exception {
        String studioname = studio.getText().replace("Studio: ", "");
        //navigation.add("Studio:" + studioname);
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

        studioProductions.getChildren().clear();

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

    public void exitStudioPane() throws Exception {
        homePane.setDisable(false);
        homePane.setVisible(true);
        studioPane.setDisable(true);
        studioPane.setVisible(false);
    }

    public void getFavorite() {
        savedList.getChildren().clear();
        //navigation.add("Favorite");
        for (Anime anime : Anime.getAnimeList()) {
            if (anime.isFavorite()) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);
                savedList.getChildren().add(animeButton);
            }
        }
        homePane.setDisable(true);
        homePane.setVisible(false);
        animePane.setDisable(true);
        animePane.setVisible(false);
        savePane.setDisable(false);
        savePane.setVisible(true);
    }

    public void exitSave() throws Exception {
        homePane.setDisable(false);
        homePane.setVisible(true);
        savePane.setDisable(true);
        savePane.setVisible(false);
    }

    public void filterList() {
        String choice = filter.getValue();
        mainColumn = 0;
        mainRow = 0;
        animeList.getChildren().clear();
        ArrayList<Anime> animes = new ArrayList<>(Anime.getAnimeList());
        if (choice.equals("Title A-Z")) {
            animes.sort(Comparator.comparing(Anime::getTitle));
            for (Anime anime : animes) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);

                animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                if (mainColumn == 2) {
                    mainColumn = 0;
                    mainRow++;
                } else {
                    mainColumn++;
                }
            }
        } else if (choice.equals("Title Z-A")) {
            animes.sort(Comparator.comparing(Anime::getTitle).reversed());
            for (Anime anime : animes) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);

                animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                if (mainColumn == 2) {
                    mainColumn = 0;
                    mainRow++;
                } else {
                    mainColumn++;
                }
            }
        } else if (choice.equals("Year Ascending")) {
            animes.sort(Comparator.comparing(Anime::getAirDate));
            for (Anime anime : animes) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);

                animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                if (mainColumn == 2) {
                    mainColumn = 0;
                    mainRow++;
                } else {
                    mainColumn++;
                }
            }
        } else if (choice.equals("Year Descending")) {
            animes.sort(Comparator.comparing(Anime::getAirDate).reversed());
            for (Anime anime : animes) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);

                animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                if (mainColumn == 2) {
                    mainColumn = 0;
                    mainRow++;
                } else {
                    mainColumn++;
                }
            }
        } else if (choice.equals("Popularity Ascending")) {
            animes.sort(Comparator.comparing(Anime::getPopularity));
            for (Anime anime : animes) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);

                animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                if (mainColumn == 2) {
                    mainColumn = 0;
                    mainRow++;
                } else {
                    mainColumn++;
                }
            }
        } else if (choice.equals("Popularity Descending")) {
            animes.sort(Comparator.comparing(Anime::getPopularity).reversed());
            for (Anime anime : animes) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);

                animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                if (mainColumn == 2) {
                    mainColumn = 0;
                    mainRow++;
                } else {
                    mainColumn++;
                }
            }
        } else if (choice.equals("Movie")) {
            for (Anime anime : Anime.getAnimeList()) {
                if (anime.getType().equals("Movie")) {
                    Image animeImage = new Image(anime.getImageUrl());
                    ImageView animeCover = new ImageView(animeImage);
                    animeCover.setPreserveRatio(true);
                    animeCover.setX(80);

                    Button animeButton = new Button(anime.getTitle());
                    animeButton.setGraphic(animeCover);
                    animeButton.setContentDisplay(ContentDisplay.TOP);
                    animeButton.setId(anime.getTitle());
                    animeButton.setOnAction(this::searchAnime);

                    animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                    if (mainColumn == 2) {
                        mainColumn = 0;
                        mainRow++;
                    } else {
                        mainColumn++;
                    }
                }
            }
        } else if (choice.equals("Anime Series")) {
            for (Anime anime : Anime.getAnimeList()) {
                if (anime.getType().equals("TV")) {
                    Image animeImage = new Image(anime.getImageUrl());
                    ImageView animeCover = new ImageView(animeImage);
                    animeCover.setPreserveRatio(true);
                    animeCover.setX(80);

                    Button animeButton = new Button(anime.getTitle());
                    animeButton.setGraphic(animeCover);
                    animeButton.setContentDisplay(ContentDisplay.TOP);
                    animeButton.setId(anime.getTitle());
                    animeButton.setOnAction(this::searchAnime);

                    animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
                    if (mainColumn == 2) {
                        mainColumn = 0;
                        mainRow++;
                    } else {
                        mainColumn++;
                    }
                }
            }
        }
    }

    public void reset() {
        mainColumn = 0;
        mainRow = 0;
        animeList.getChildren().clear();
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

            animeList.add(animeButton, mainColumn, mainRow); // Column 1, Row 1
            if (mainColumn == 2) {
                mainColumn = 0;
                mainRow++;
            } else {
                mainColumn++;
            }
        }
        filter.setValue(null);
    }

    public void searchGenre() throws Exception {
        String genrename = genre.getText().replace("Genre: ", "");
        //navigation.add("Genre:" + genrename);

        genreLabel.setText("Genre: " + genrename);

        int column = 0;
        int row = 0;

        genreList.getChildren().clear();

        for (Anime anime : Anime.getAnimeList()) {
            if (anime.getGenre().equals(genrename)) {
                Image animeImage = new Image(anime.getImageUrl());
                ImageView animeCover = new ImageView(animeImage);
                animeCover.setPreserveRatio(true);
                animeCover.setX(80);

                Button animeButton = new Button(anime.getTitle());
                animeButton.setGraphic(animeCover);
                animeButton.setContentDisplay(ContentDisplay.TOP);
                animeButton.setId(anime.getTitle());
                animeButton.setOnAction(this::searchAnime);

                genreList.add(animeButton, column, row);
                if (column == 1) {
                    column = 0;
                    row++;
                } else {
                    column++;
                }
            }
        }

        homePane.setDisable(true);
        homePane.setVisible(false);
        animePane.setDisable(true);
        animePane.setVisible(false);
        genrePane.setDisable(false);
        genrePane.setVisible(true);
    }

    public void exitGenre() throws Exception {
        homePane.setDisable(false);
        homePane.setVisible(true);
        genrePane.setDisable(true);
        genrePane.setVisible(false);
    }

//    public void prev() throws Exception {
//        ArrayList<String> navigationSplit = new ArrayList<>(List.of(navigation.get(-2).split(":")));
//        String prefix = navigationSplit.getFirst();
//        String suffix = navigationSplit.getLast();
//        System.out.println(prefix + " " + suffix);
//
//        if (prefix.equals("Anime")) {
//            String animeName = suffix;
//            Anime currentAnime = null;
//            navigation.add("Anime: " + currentAnime);
//            for (Anime anime : Anime.getAnimeList()) {
//                if (anime.getTitle().equals(animeName)) {
//                    currentAnime = anime;
//                }
//            }
//
//            Image animeImage = new Image(currentAnime.getImageUrl());
//            animeCover.setImage(animeImage);
//            animeTitle.setText(currentAnime.getTitle());
//            animeDescription.setText(currentAnime.getSynopsis());
//            jpTitle.setText("JP Title: " + currentAnime.getTitleJP());
//            genre.setText("Genre: " + currentAnime.getGenre());;
//            type.setText("Type: " + currentAnime.getType());;
//            episodes.setText("Episodes: " + currentAnime.getEpisodes());
//            duration.setText("Duration: " + currentAnime.getDurationMinutes() + " Minutes");
//            season.setText("Season: " + currentAnime.getSeason());
//            finishedAiring.setText("Finished Airing: " + currentAnime.isFinishedAiring());
//            airDate.setText("Air Date: " + currentAnime.getAirDate());
//            rating.setText("Rating: " + currentAnime.getRating());
//            popularity.setText("Popularity: " + currentAnime.getPopularity());
//            studio.setText("Studio: " + currentAnime.getStudio().getStudioName());
//            studio.setId(currentAnime.getStudio().getStudioName());
//
//            if (currentAnime.isFavorite()){
//                favorite.setStyle("-fx-background-color: #C4B454; ");
//            } else {
//                favorite.setStyle("-fx-background-color: #FDFDFD; ");
//            }
//
//            homePane.setDisable(true);
//            homePane.setVisible(false);
//            studioPane.setDisable(true);
//            studioPane.setVisible(false);
//            savePane.setDisable(true);
//            savePane.setVisible(false);
//            animePane.setDisable(false);
//            animePane.setVisible(true);
//        } else if (prefix.equals("Studio")) {
//            studio.setText("Studio: " + suffix);
//            searchStudio();
//        }  else if (prefix.equals("Genre")) {
//            genre.setText("Genre: " + suffix);
//            searchGenre();
//        } else {
//            getFavorite();
//        }
//        navigation.remove(navigation.getLast());
//    }
}
