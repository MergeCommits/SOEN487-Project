package com.thing.impl;

import com.thing.core.*;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.*;

public class HotelRepositoryImpl implements HotelRepository {
    @Override
    public void addOrReplaceHotel(Hotel hotel) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Hotel> query = session.createQuery("from Hotel");
            List<Hotel> hotelCollection = query.list();

            Optional<Hotel> matchingID = hotelCollection
                .stream()
                .filter(a -> a.equals(hotel))
                .findFirst();


            Transaction tx = session.beginTransaction();
            session.clear();

            if (matchingID.isPresent()) {
                Hotel found = matchingID.get();
                hotel.setId(found.getId());
                session.saveOrUpdate(hotel);
            } else {
                int id = session.save(hotel);
            }

            tx.commit();
        }
    }

    @Override
    public boolean removeHotel(String name) {
        Hotel hotel = null;
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Hotel> query = session.createQuery("from Hotel as a where a.name = :name");
            query.setParameter("name", name);
            try {
                hotel = query.getSingleResult();
            } catch (NoResultException ignored) {
            }
        }

        if (hotel == null) {
            return false;
        }

        try (HibernateSession session = HibernateUtil.startSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(hotel);
            tx.commit();
        }

        return true;
    }

    @Override
    public Hotel getHotel(String name) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Hotel> query = session.createQuery("from Hotel as a where a.name = :name");
            query.setParameter("name", name);
            try {
                return query.getSingleResult();
            } catch (NoResultException _e) {
                return null;
            }

        }
    }


    @Override
    public boolean addOrReplaceRoom(Room room, String hotelName) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Hotel hotel = getHotel(hotelName);
            if (hotel == null) {
                return false;
            }

            Query<Room> roomQuery = session.createQuery("from Room");
            List<Room> roomCollection = roomQuery.list();

            Optional<Room> matchingID = roomCollection
                .stream()
                .filter(a -> a.equals(room))
                .findFirst();


            Transaction tx = session.beginTransaction();
            session.clear();

            room.setHotel(hotel);

            if (matchingID.isPresent()) {
                Room found = matchingID.get();
                room.setId(found.getId());
                session.saveOrUpdate(room);
            } else {
                int id = session.save(room);
            }

            tx.commit();
            return true;
        }
    }

    @Override
    public boolean removeRoom(int roomNumber) {
        Room room = null;
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Room> query = session.createQuery("from Room as a where a.roomNumber = :roomNumber");
            query.setParameter("roomNumber", roomNumber);
            try {
                room = query.getSingleResult();
            } catch (NoResultException ignored) {
            }
        }

        if (room == null) {
            return false;
        }

        try (HibernateSession session = HibernateUtil.startSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(room);
            tx.commit();
        }

        return true;
    }

    @Override
    public Room getRoom(int roomNumber) {
        try (HibernateSession session = HibernateUtil.startSession()) {
            Query<Room> query = session.createQuery("from Room as a where a.roomNumber = :roomNumber");
            query.setParameter("roomNumber", roomNumber);
            try {
                return query.getSingleResult();
            } catch (NoResultException _e) {
                return null;
            }

        }
    }

//    @Override
//    public List<Album> allAlbums() {
//        try (HibernateSession session = HibernateUtil.startSession()) {
//            Query<Album> query = session.createQuery("from Album");
//            return query.list().stream().sorted().collect(Collectors.toList());
//        }
//    }


}
