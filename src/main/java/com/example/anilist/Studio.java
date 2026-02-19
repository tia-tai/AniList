package com.example.anilist;

import java.util.ArrayList;

public class Studio {
    private int studioId;
    private String studioName;
    private String discription;
    private String imageURL;
    private ArrayList<Anime> producedAnimes = new ArrayList<>();
    static ArrayList<Studio> studios = new ArrayList<>();

    public Studio(int studioId, String studioName, String discription, String imageURL, ArrayList<Anime> producedAnimes) {
        this.studioId = studioId;
        this.studioName = studioName;
        this.discription = discription;
        this.imageURL = imageURL;
        this.producedAnimes = producedAnimes;
        studios.add(this);
    }

    public Studio(int malId, String name) {
        this.studioId = studioId;
        this.studioName = studioName;
        studios.add(this);
    }

    public static ArrayList<Studio> getStudios() {
        return studios;
    }

    public static void setStudios(ArrayList<Studio> studios) {
        Studio.studios = studios;
    }

    public ArrayList<Anime> getProducedAnimes() {
        return producedAnimes;
    }

    public void setProducedAnimes(ArrayList<Anime> producedAnimes) {
        this.producedAnimes = producedAnimes;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public int getStudioId() {
        return studioId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public void addProducedAnimes(Anime anime) {
        this.producedAnimes.add(anime);
    }
}
