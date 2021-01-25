package com.thing.core;

public interface ArtistRepository {
    boolean addArtist(Artist p);
    Artist getArtist(String isrc);
    boolean updateArtist(Artist p);
    boolean removeArtist(String isrc);
    String listArtists();
}
