package com.example.anilist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Anime {
    String url;
    String title;
    String titleJP;
    String imageUrl;
    String type;
    int episodes;
    boolean finishedAiring;
    LocalDate airDate;
    int durationMinutes;
    String rating;
    int popularity;
    int season;
    String genre;

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
