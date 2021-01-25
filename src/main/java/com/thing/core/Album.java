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

    private String artistNickname;

    public String getArtistNickname() {
        return artistNickname;
    }

    public void setArtistNickname(String artistNickname) {
        this.artistNickname = artistNickname;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Album(String isrc, String title, int year, String artistNickname, String description) {
        this.isrc = isrc;
        this.title = title;
        this.year = year;
        this.artistNickname = artistNickname;
        this.description = description;
    }

    public Album(String isrc, String title, int year, String description) {
        this(isrc, title, year, "", description);
    }

    @Override
    public int compareTo(Album o) {
        return getIsrc().compareTo(o.getIsrc());
    }

    @Override
    public String toString() {
        return title + "{" +
                "isrc='" + isrc + '\'' +
                ", year=" + year +
                ", artist='" + artistNickname + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String toStringIRSCAndTitle() {
        return title + "{" +
                "isrc='" + isrc + '\'' +
                '}';
    }
}
