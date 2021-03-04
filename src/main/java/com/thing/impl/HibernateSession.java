package com.thing.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateSession implements AutoCloseable {
    private final Session session;

    public HibernateSession(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public void save(Object o) {
        session.save(o);
    }

    public void delete(Object o) {
        session.delete(o);
    }

    @SuppressWarnings("unchecked")
    public <E> Query<E> createQuery(String fromWhere) {
        return (Query<E>) session.createQuery(fromWhere);
    }

    @Override
    public void close() {
        session.close();
    }
}
