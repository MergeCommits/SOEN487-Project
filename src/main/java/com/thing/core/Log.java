package com.thing.core;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log")
public class Log {
    public final String CHANGE_CREATE = "CREATE";
    public final String CHANGE_UPDATE = "UPDATE";
    public final String CHANGE_DELETE = "DELETE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    private String change;

    private Date timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "album_isrc")
    private String albumISRC;
}
