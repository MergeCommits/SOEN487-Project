package com.thing.impl;

import com.thing.core.*;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.annotations.common.util.StringHelper.isEmpty;

public class AlbumRepositoryImpl implements AlbumRepository {
    private void performLog(String logType, Album album) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Log log = new Log();
            log.setAlbum(album);
            log.setAlbumISRC(album.getIsrc());
            log.setChange(logType);

            session.save(log);
        }
    }

    @Override
    public boolean addAlbum(Album album) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album");
            List<Album> albumCollection = query.list();

            Optional<Album> matchingID = albumCollection
                .stream()
                .filter(a -> a.equals(album))
                .findFirst();

            if (matchingID.isPresent()) {
                return false;
            }

            session.save(album);
        }

        performLog(Log.TYPE_CREATE, album);
        return true;
    }

    @Override
    public boolean updateAlbum(Album album) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album as a where a.isrc = :isrc");
            query.setParameter("isrc", album.getIsrc());

            try {
                Album found = query.getSingleResult();
                album.setId(found.getId());
                album.setCoverImage(found.getCoverImage());

                Transaction tx = session.beginTransaction();

                session.clear();
                session.saveOrUpdate(album);
                tx.commit();

            } catch (NoResultException _e) {
                return false;
            }
        }

        performLog(Log.TYPE_UPDATE, album);
        return true;
    }

    @Override
    public boolean removeAlbum(String isrc) {
        Album album = null;
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album as a where a.isrc = :isrc");
            query.setParameter("isrc", isrc);
            try {
                album = query.getSingleResult();
            } catch (NoResultException ignored) {
            }
        }

        if (album == null) {
            return false;
        }

        performLog(Log.TYPE_DELETE, album);

        try (HibernateSession session = HibernateUtil.startSession()) {
            Transaction tx = session.beginTransaction();

            session.delete(album);

            tx.commit();
        }

        return true;
    }

    @Override
    public Album getAlbum(String isrc) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album as a where a.isrc = :isrc");
            query.setParameter("isrc", isrc);
            try {
                return query.getSingleResult();
            } catch (NoResultException _e) {
                return null;
            }

        }
    }

    @Override
    public List<Album> allAlbums() {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album");
            return query.list().stream().sorted().collect(Collectors.toList());
        }
    }

    @Override
    public boolean updateAlbumImage(String isrc, AlbumCoverImage albumCoverImage) {
        Album found;

        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album as a where a.isrc = :isrc");
            query.setParameter("isrc", isrc);

            try {
                found = query.getSingleResult();
                found.setCoverImage(albumCoverImage);

                Transaction tx = session.beginTransaction();
                session.save(found);
                tx.commit();
            } catch (NoResultException _e) {
                return false;
            }
        }

        performLog(Log.TYPE_UPDATE, found);
        return true;
    }

    @Override
    public boolean removeAlbumImage(String isrc) {
        Album found;

        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album as a where a.isrc = :isrc");
            query.setParameter("isrc", isrc);

            try {
                found = query.getSingleResult();
                found.setCoverImage(null);

                Transaction tx = session.beginTransaction();
                session.save(found);
                tx.commit();
            } catch (NoResultException _e) {
                return false;
            }
        }

        performLog(Log.TYPE_UPDATE, found);
        return true;
    }

    @Override
    public AlbumCoverImage getAlbumImage(String isrc) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album as a where a.isrc = :isrc");
            query.setParameter("isrc", isrc);

            try {
                Album found = query.getSingleResult();
                return found.getCoverImage();
            } catch (NoResultException _e) {
                return null;
            }
        }
    }

    @Override
    public List<Log> getChangeLogs(String isrc, Date from, Date to, String changeType) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Album> query = session.createQuery("from Album as a where a.isrc = :isrc");
            query.setParameter("isrc", isrc);
            try {
                Album found = query.getSingleResult();

                List<Log> uh =  found.getLogs()
                    .stream()
                    .filter(a -> from == null || a.getTimestamp().after(from))
                    .filter(a -> to == null || a.getTimestamp().before(to))
                    .filter(a -> changeType == null || isEmpty(changeType) || a.getChange().equals(changeType))
                    .sorted()
                    .collect(Collectors.toList());
                return uh;
            } catch (NoResultException e) {
                System.err.print(e.getCause().toString());
                return null;
            }

        }
    }

    @Override
    public boolean clearLogs(Album album) {
        throw new RepException("the method is not yet supported.");
    }
}
