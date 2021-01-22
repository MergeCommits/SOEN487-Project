package com.thing.core;

public class Album implements Comparable<Album> {
    private String isrc;

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Album(String isrc, String title, int year, Artist artist, String description) {
        this.isrc = isrc;
        this.title = title;
        this.year = year;
        this.artist = artist;
        this.description = description;
    }

    public Album(String isrc, String title, int year, String description) {
        this(isrc, title, year, null, description);
    }

    @Override
    public int compareTo(Album o) {
        return getIsrc().compareTo(o.getIsrc());
    }
}
