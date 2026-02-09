package com.example.anilist;

import java.util.ArrayList;

public class Studio {
    private int studioId;
    private String studioName;
    private ArrayList<Anime> producedAnimes = new ArrayList<>();
    static ArrayList<Studio> studios = new ArrayList<>();

    public Studio(int studioId, String studioName, ArrayList<Anime> producedAnimes) {
        this.studioId = studioId;
        this.studioName = studioName;
        this.producedAnimes = producedAnimes;
        studios.add(this);
    }


    public Studio(int studioId, String studioName) {
        this.studioId = studioId;
        this.studioName = studioName;
        studios.add(this);
    }

    public int getStudioId() {
        return studioId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public ArrayList<Anime> getProducedAnimes() {
        return producedAnimes;
    }

    public void setProducedAnimes(ArrayList<Anime> producedAnimes) {
        this.producedAnimes = producedAnimes;
    }

    public void addProducedAnimes(Anime anime) {
        this.producedAnimes.add(anime);
    }

    public static ArrayList<Studio> getStudios() {
        return studios;
    }

    public static void setStudios(ArrayList<Studio> studios) {
        Studio.studios = studios;
    }
}
