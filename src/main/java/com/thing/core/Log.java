package com.thing.core;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement
@Entity
@Table(name = "log")
public class Log implements Comparable<Log> {
    public static final String TYPE_CREATE = "CREATE";
    public static final String TYPE_UPDATE = "UPDATE";
    public static final String TYPE_DELETE = "DELETE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Column(name = "change_type")
    private String change;

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @XmlTransient
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getAlbumISRC() {
        return albumISRC;
    }

    public void setAlbumISRC(String albumISRC) {
        this.albumISRC = albumISRC;
    }

    private Date timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "album_isrc")
    private String albumISRC;

    public Log() {
        setTimestamp(new Date());
    }

    @Override
    public int compareTo(Log o) {
        return o.getTimestamp().compareTo(getTimestamp());
    }
}
