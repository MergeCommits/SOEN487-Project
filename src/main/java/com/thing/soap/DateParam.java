package com.thing.soap;

import com.thing.core.RepException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hibernate.annotations.common.util.StringHelper.isEmpty;

public class DateParam {
    private final Date date;

    public DateParam(String dateStr) throws RepException {
        if (isEmpty(dateStr)) {
            this.date = null;
            return;
        }

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            this.date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RepException("Couldn't parse date string: " + e.getMessage());
        }
    }

    public Date getDate() {
        return date;
    }
}
