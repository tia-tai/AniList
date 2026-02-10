package com.example.anilist;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Anime {
    private String url;
    private int id;
    private String title;
    private String titleJP;
    private String synopsis;
    private String imageUrl;
    private String type;
    private int episodes;
    private boolean finishedAiring;
    private LocalDate airDate;
    private int durationMinutes;
    private int rating;
    private int popularity;
    private int season;
    private String genre;
    private Studio studio;

    static public ArrayList<Anime> animeList = new ArrayList<>();

    public Anime(String url, int id, String title, String titleJP, String synopsis, String imageUrl, String type, int episodes, boolean finishedAiring, LocalDate airDate, int durationMinutes, int rating, int popularity, int season, String genre, Studio studio) {
        this.url = url;
        this.id = id;
        this.title = title;
        this.titleJP = titleJP;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
        this.type = type;
        this.episodes = episodes;
        this.finishedAiring = finishedAiring;
        this.airDate = airDate;
        this.durationMinutes = durationMinutes;
        this.rating = rating;
        this.popularity = popularity;
        this.season = season;
        this.genre = genre;
        this.studio = studio;
    }

    public Anime() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleJP() {
        return titleJP;
    }

    public void setTitleJP(String titleJP) {
        this.titleJP = titleJP;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public boolean isFinishedAiring() {
        return finishedAiring;
    }

    public void setFinishedAiring(boolean finishedAiring) {
        this.finishedAiring = finishedAiring;
    }

    public LocalDate getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDate airDate) {
        this.airDate = airDate;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public static ArrayList<Anime> getAnimeList() {
        return animeList;
    }

    public static void setAnimeList(ArrayList<Anime> animeList) {
        Anime.animeList = animeList;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    static String getJSONfromURL(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        InputStreamReader inStream = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(inStream);
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }


    static void searchAnime() throws Exception {
        String jsonAnime=getJSONfromURL("https://api.jikan.moe/v4/anime");
        System.out.println("JSONs: " + jsonAnime);

        // Read JSON objects using JsonNode after readTree()
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonAnime);
        // By reading the JSON tree, the code can now get individual "key":"value" pairs
        // The value of the "result" key is an ARRAY of JSON objects
        JsonNode arrayOfAnimes = jsonNode.get("data");
        // read 1 JSON object (its "key":"value" pairs) into the fields of a Anime object.
        int listNumber = 0;
        for (JsonNode arrayOfAnime : arrayOfAnimes) {
            if (listNumber < 100) {
                Anime newAnime = new Anime();
                newAnime.setSynopsis(arrayOfAnime.get("synopsis").asText());
                newAnime.setUrl(arrayOfAnime.get("url").asText());
                newAnime.setId(arrayOfAnime.get("mal_id").asInt());
                newAnime.setImageUrl(arrayOfAnime.get("images").get("jpg").get("image_url").asText());
                newAnime.setTitle(arrayOfAnime.get("title").asText());
                newAnime.setTitleJP(arrayOfAnime.get("title_japanese").asText());
                newAnime.setType(arrayOfAnime.get("type").asText());
                newAnime.setEpisodes(arrayOfAnime.get("episodes").asInt());
                newAnime.setFinishedAiring(arrayOfAnime.get("status").asText().equals("Finished Airing"));
                String dateTimeString = arrayOfAnime.get("aired").get("from").asText();
                System.out.println(dateTimeString);
                if (dateTimeString != null){
                    OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                    LocalDate localDate = offsetDateTime.toLocalDate();
                    newAnime.setAirDate(localDate);
                }
                String unfiltheredDuration = arrayOfAnime.get("duration").asText();
                ArrayList<String> splitedDuration = new ArrayList<>();
                int totalDuration = 0;
                splitedDuration.addAll(Arrays.asList(unfiltheredDuration.split(" ")));
                if (!splitedDuration.getFirst().equals("Unknown")) {
                    if (splitedDuration.get(1).equals("hr")) {
                        totalDuration += 60 * Integer.parseInt(splitedDuration.getFirst());
                        totalDuration += Integer.parseInt(splitedDuration.getFirst());
                    } else if (splitedDuration.get(1).equals("min")) {
                        totalDuration += Integer.parseInt(splitedDuration.getFirst());
                    }
                }
                newAnime.setDurationMinutes(totalDuration);
                newAnime.setRating(arrayOfAnime.get("rank").asInt());
                newAnime.setPopularity(arrayOfAnime.get("popularity").asInt());
                newAnime.setSeason(arrayOfAnime.get("season").asInt());
                JsonNode genreNode = objectMapper.readTree(arrayOfAnime.get("genres").traverse());
                for (JsonNode node : genreNode) {
                    newAnime.setGenre(node.get("name").asText());
                    break;
                }
                JsonNode studioNode = objectMapper.readTree(arrayOfAnime.get("studios").traverse());
                Studio foundStudio = null;
                for (JsonNode node : studioNode) {
                    boolean found = false;
                    for (Studio studio : Studio.getStudios()){
                        if (studio.getStudioId() == node.get("mal_id").asInt()) {
                            foundStudio = studio;
                            studio.addProducedAnimes(newAnime);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        Studio studio = new Studio(node.get("mal_id").asInt(), node.get("name").asText());
                        studio.addProducedAnimes(newAnime);
                        foundStudio = studio;
                    }
                }
                newAnime.setStudio(foundStudio);
                animeList.add(newAnime);
                System.out.println("OBJECT: " + newAnime.getTitle());
                listNumber++;
            } else {
                break;
            }
        }
    }
}
