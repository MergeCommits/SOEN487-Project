package com.thing.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HibernateSession implements AutoCloseable {
    private final Session session;

    public HibernateSession(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public int save(Object o) {
        return (Integer)session.save(o);
    }

    public void saveOrUpdate(Object o) {
        session.saveOrUpdate(o);
    }

    public void clear() {
        session.clear();
    }

    public Transaction beginTransaction() {
        return session.beginTransaction();
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
