package com.thing.rest;

import com.thing.core.Hotel;
import com.thing.core.Room;
import com.thing.runtime.Main;

import java.util.ArrayList;

public class HTMLBuilder {
    public String getHotelAsHTML(Hotel hotel) {
        String template = Main.htmlTemplate;
        assert template != null;
        template = template.replaceAll("HOTEL_NAME", hotel.getName());

        StringBuilder rooms = new StringBuilder();
        for (Room room : hotel.getRooms()) {
            rooms.append(buildRoom(room));
        }

        template = template.replaceFirst("ROOM_FILL", rooms.toString());
        return template;
    }
    public String buildRoom(Room room) {
        return "<ul>\n" +
            "  <li>Floor: " + room.getFloor() + "</li>\n" +
            "  <li>Room number: " + room.getRoomNumber() + "</li>\n" +
            "</ul>";
    }
}
