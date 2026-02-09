package com.example.anilist;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    public VBox studioList;

    public AnchorPane studioPane;

    public void initialize() throws Exception {
        Anime.searchAnime();

        for (Anime anime : Anime.getAnimeList()) {
            animeData.getItems().add(anime);
        }

        AtomicReference<String> imageUrl = new AtomicReference<>(Anime.getAnimeList().getFirst().getImageUrl());
        String name = Anime.getAnimeList().getFirst().getTitle();
        AtomicReference<Image> image = new AtomicReference<>(new Image(imageUrl.get(), true));
        AnimeCover.setImage(image.get());
        AnimeName.setText(name);
        studioList.getChildren().clear();
        Text studioText = new Text("Studio: ");
        AtomicReference<Button> studioButton = new AtomicReference<>(new Button(Anime.getAnimeList().getFirst().getStudio().getStudioName()));
        studioButton.get().setId(Anime.getAnimeList().getFirst().getStudio().getStudioName());
//        studioButton.get().setOnAction();
        studioList.getChildren().add(studioText);
        studioList.getChildren().add(studioButton.get());

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

        animeData.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    AnimeName.setText(newValue.getTitle());
                    imageUrl.set(newValue.getImageUrl());
                    image.set(new Image(imageUrl.get(), true));
                    studioList.getChildren().clear();
                    studioList.getChildren().add(new Text("Studio: "));
                    studioButton.set(new Button(newValue.getStudio().getStudioName()));
                    studioButton.get().setId(newValue.getStudio().getStudioName());
                    studioList.getChildren().add(studioButton.get());
                    AnimeCover.setImage(image.get());
                }
        );
    }

    public void openStudio(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        studioPane.setVisible(true);
        studioPane.setDisable(false);
    }
}
