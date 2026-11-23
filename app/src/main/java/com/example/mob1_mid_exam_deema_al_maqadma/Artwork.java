package com.example.mob1_mid_exam_deema_al_maqadma;

public class Artwork {
    private final int imageResId;
    private final String title;
    private final String artist;
    private final String year;
    private final String webUrl;

    public Artwork(int imageResId, String title, String artist, String year, String webUrl) {
        this.imageResId = imageResId;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.webUrl = webUrl;
    }

    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getYear() { return year; }
    public String getWebUrl() { return webUrl; }
}