package com.thing.impl;

import com.thing.core.Album;
import com.thing.core.AlbumRepository;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AlbumRepositoryImpl implements AlbumRepository {
    private List<Album> albumCollection;

    public AlbumRepositoryImpl() {
        albumCollection = new ArrayList<Album>();
    }

    public List<Album> getCollection() {
        return this.albumCollection;
    }

    @Override
    public boolean addAlbum(Album album) {
        Optional<Album> matchingID = getCollection()
                .stream()
                .filter(a -> album.getIsrc().equals(a.getIsrc()))
                .findFirst();

        if (matchingID.isPresent()) {
            return false;
        }

        albumCollection.add(album);
        return true;
    }

    @Override
    public Album getAlbum(String isrc) {

    }

    @Override
    public boolean updateAlbum(Album album) {
        List<Album> collection = getCollection();

        int index;
        boolean found = false;
        for (index = 0; index < collection.size(); index++) {
            if (collection.get(index).getIsrc().equals(album.getIsrc())) {
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        collection.set(index, album);
        return true;
    }

    @Override
    public boolean removeAlbum(String isrc) {

    }
}
