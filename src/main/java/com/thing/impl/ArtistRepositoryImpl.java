package com.thing.impl;

import com.thing.core.Artist;
import com.thing.core.ArtistRepository;
import com.thing.core.ArtistRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArtistRepositoryImpl implements ArtistRepository {
    private final List<Artist> artistCollection;

    public ArtistRepositoryImpl() {
        artistCollection = new ArrayList<Artist>();
    }

    @Override
    public boolean addArtist(Artist artist) {
        Optional<Artist> matchingID = artistCollection
            .stream()
            .filter(a -> a.getNickname().equals(artist.getNickname()))
            .findFirst();

        if (matchingID.isPresent()) {
            return false;
        }

        artistCollection.add(artist);
        return true;
    }

    @Override
    public Artist getArtist(String nick) {
        return artistCollection
            .stream()
            .filter(a -> nick.equals(a.getNickname()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean updateArtist(Artist artist) {
        int index;
        boolean found = false;
        for (index = 0; index < artistCollection.size(); index++) {
            if (artistCollection.get(index).getNickname().equals(artist.getNickname())) {
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        artistCollection.set(index, artist);
        return true;
    }

    @Override
    public boolean removeArtist(String nick) {
        return artistCollection.removeIf(a -> a.getNickname().equals(nick));
    }

    public String listArtists() {
        return artistCollection.stream()
            .map(Artist::toStringNicknameAndFullname)
            .collect(Collectors.joining("\n"));
    }
}
