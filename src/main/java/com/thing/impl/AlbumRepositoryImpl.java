package com.thing.impl;

import com.thing.core.Album;
import com.thing.core.AlbumRepository;

import java.util.*;
import java.util.stream.Collectors;

public class AlbumRepositoryImpl implements AlbumRepository {
    private final List<Album> albumCollection;

    public AlbumRepositoryImpl() {
        albumCollection = new ArrayList<Album>();
    }

    public boolean addAlbum(Album album) {
        Optional<Album> matchingID = albumCollection
                .stream()
                .filter(a -> a.getIsrc().equals(album.getIsrc()))
                .findFirst();

        if (matchingID.isPresent()) {
            return false;
        }

        albumCollection.add(album);
        return true;
    }

    public boolean updateAlbum(Album album) {
        int index;
        boolean found = false;
        for (index = 0; index < albumCollection.size(); index++) {
            if (albumCollection.get(index).getIsrc().equals(album.getIsrc())) {
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        albumCollection.set(index, album);
        return true;
    }

    public boolean removeAlbum(String isrc) {
        return albumCollection.removeIf(a -> a.getIsrc().equals(isrc));
    }

    public Album getAlbum(String isrc) {
        return albumCollection
            .stream()
            .filter(a -> isrc.equals(a.getIsrc()))
            .findFirst()
            .orElse(null);
    }
}
