package com.thing.core;

import java.util.Date;
import java.util.List;

public interface AlbumRepository {
    boolean addAlbum(Album p);
    boolean updateAlbum(Album p);
    boolean removeAlbum(String isrc);
    Album getAlbum(String isrc);
    List<Album> allAlbums();

    boolean updateAlbumImage(String isrc, AlbumCoverImage albumCoverImage);
    boolean removeAlbumImage(String isrc);
    AlbumCoverImage getAlbumImage(String isrc);

    List<Log> getChangeLogs(String isrc, Date from, Date to, String changeType);
    boolean clearLogs(Album album);
}
