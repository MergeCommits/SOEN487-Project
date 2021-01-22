package com.thing.core;

public interface AlbumRepository {
    boolean addAlbum(Album p);
    Album getAlbum(String isrc);
    boolean updateAlbum(Album p);
    boolean removeAlbum(String isrc);
}
