package com.example.anilist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

public class Anime {
    private String url;
    private String title;
    private String titleJP;
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

    static public ArrayList<Anime> animeList = new ArrayList<>();

    public Anime(String url, String title, String titleJP, String imageUrl, String type, int episodes, boolean finishedAiring, LocalDate airDate, int durationMinutes, int rating, int popularity, int season, String genre) {
        this.url = url;
        this.title = title;
        this.titleJP = titleJP;
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
    }

    public Anime() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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


    public void searchAnime() throws Exception {
        String jsonAnime=getJSONfromURL("https://api.jikan.moe/v4/random/anime");
        System.out.println("JSONs: " + jsonAnime);

        // Read JSON objects using JsonNode after readTree()
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonAnime);
        // By reading the JSON tree, the code can now get individual "key":"value" pairs
        // The value of the "result" key is an ARRAY of JSON objects
        JsonNode arrayOfAnime = jsonNode.get("data");
        // read 1 JSON object (its "key":"value" pairs) into the fields of a Anime object.
        Anime newAnime = new Anime();
        newAnime.setUrl(arrayOfAnime.get("url").asText());
        newAnime.setImageUrl(arrayOfAnime.get("images").get("jpg").get("image_url").asText());
        newAnime.setTitle(arrayOfAnime.get("title").asText());
        newAnime.setTitleJP(arrayOfAnime.get("title_japanese").asText());
        newAnime.setType(arrayOfAnime.get("type").asText());
        newAnime.setEpisodes(arrayOfAnime.get("episodes").asInt());
        newAnime.setFinishedAiring(arrayOfAnime.get("status").asText().equals("Finished Airing"));
        String dateTimeString = arrayOfAnime.get("aired").get("from").asText();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LocalDate localDate = offsetDateTime.toLocalDate();
        newAnime.setAirDate(localDate);
        //newAnime.setDurationMinutes(); // Parse HR Minute into Minutes
        newAnime.setRating(arrayOfAnime.get("rank").asInt());
        newAnime.setPopularity(arrayOfAnime.get("popularity").asInt());
        newAnime.setSeason(arrayOfAnime.get("season").asInt());
        newAnime.setGenre(arrayOfAnime.get("genres").get("name").asText());
        animeList.add(newAnime);
        System.out.println("OBJECT: " + newAnime);
    }


    //    {
//        "data": {
//        "mal_id": 23865,
//                "url": "https://myanimelist.net/anime/23865/Ojisan_Kaizou_Kouza",
//                "images": {
//            "jpg": {
//                "image_url": "https://cdn.myanimelist.net/images/anime/8/61743.jpg",
//                        "small_image_url": "https://cdn.myanimelist.net/images/anime/8/61743t.jpg",
//                        "large_image_url": "https://cdn.myanimelist.net/images/anime/8/61743l.jpg"
//            },
//            "webp": {
//                "image_url": "https://cdn.myanimelist.net/images/anime/8/61743.webp",
//                        "small_image_url": "https://cdn.myanimelist.net/images/anime/8/61743t.webp",
//                        "large_image_url": "https://cdn.myanimelist.net/images/anime/8/61743l.webp"
//            }
//        },
//        "trailer": {
//            "youtube_id": null,
//                    "url": null,
//                    "embed_url": null,
//                    "images": {
//                "image_url": null,
//                        "small_image_url": null,
//                        "medium_image_url": null,
//                        "large_image_url": null,
//                        "maximum_image_url": null
//            }
//        },
//        "approved": true,
//                "titles": [
//        {
//            "type": "Default",
//                "title": "Ojisan Kaizou Kouza"
//        },
//        {
//            "type": "Japanese",
//                "title": "おじさん改造講座"
//        }
//    ],
//        "title": "Ojisan Kaizou Kouza",
//                "title_english": null,
//                "title_japanese": "おじさん改造講座",
//                "title_synonyms": [],
//        "type": "Movie",
//                "source": "Unknown",
//                "episodes": 1,
//                "status": "Finished Airing",
//                "airing": false,
//                "aired": {
//            "from": "1990-02-24T00:00:00+00:00",
//                    "to": null,
//                    "prop": {
//                "from": {
//                    "day": 24,
//                            "month": 2,
//                            "year": 1990
//                },
//                "to": {
//                    "day": null,
//                            "month": null,
//                            "year": null
//                }
//            },
//            "string": "Feb 24, 1990"
//        },
//        "duration": "1 hr 31 min",
//                "rating": "R+ - Mild Nudity",
//                "score": null,
//                "scored_by": null,
//                "rank": 19527,
//                "popularity": 17822,
//                "members": 537,
//                "favorites": 1,
//                "synopsis": "Adapted from Shimizu Chinami and Furuya Yoshi's popular manga in Weekly Bunshun.",
//                "background": "",
//                "season": null,
//                "year": null,
//                "broadcast": {
//            "day": null,
//                    "time": null,
//                    "timezone": null,
//                    "string": null
//        },
//        "producers": [
//        {
//            "mal_id": 144,
//                "type": "anime",
//                "name": "Pony Canyon",
//                "url": "https://myanimelist.net/anime/producer/144/Pony_Canyon"
//        },
//        {
//            "mal_id": 170,
//                "type": "anime",
//                "name": "Imagica",
//                "url": "https://myanimelist.net/anime/producer/170/Imagica"
//        }
//    ],
//        "licensors": [],
//        "studios": [
//        {
//            "mal_id": 73,
//                "type": "anime",
//                "name": "TMS Entertainment",
//                "url": "https://myanimelist.net/anime/producer/73/TMS_Entertainment"
//        },
//        {
//            "mal_id": 94,
//                "type": "anime",
//                "name": "Telecom Animation Film",
//                "url": "https://myanimelist.net/anime/producer/94/Telecom_Animation_Film"
//        }
//    ],
//        "genres": [
//        {
//            "mal_id": 4,
//                "type": "anime",
//                "name": "Comedy",
//                "url": "https://myanimelist.net/anime/genre/4/Comedy"
//        }
//    ],
//        "explicit_genres": [],
//        "themes": [],
//        "demographics": []
//    }
//    }
}
