package com.thing.core;

public interface AlbumRepository {
    boolean addAlbum(Album p);
    boolean updateAlbum(Album p);
    boolean removeAlbum(String isrc);
    Album getAlbum(String isrc);
    String listAlbums();
}
