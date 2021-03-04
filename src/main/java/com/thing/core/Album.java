package com.thing.core;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "album")
public class Album implements Comparable<Album> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Column(columnDefinition="TEXT")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "firstName", column = @Column(name = "artist_first_name")),
        @AttributeOverride(name = "lastName", column = @Column(name = "artist_last_name"))
    })
    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Column(name = "cover_image")
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "image", column = @Column(name = "cover_image", columnDefinition="LONGBLOB")),
        @AttributeOverride(name = "mimeType", column = @Column(name = "cover_image_mime"))
    })
    private AlbumCoverImage coverImage;

    public AlbumCoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(AlbumCoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    @OneToMany()
    @JoinColumn(name = "album_id")
    private Set<Log> logs;

    @Override
    public int compareTo(Album o) {
        return getTitle().compareTo(o.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return getId() == album.getId() && getIsrc().equals(album.getIsrc());
    }
}
