package com.thing.rest;

import com.thing.core.Hotel;
import com.thing.core.Room;
import com.thing.impl.HotelRepositoryImpl;
import com.thing.runtime.Main;

public class HTMLBuilder {
    public String getHotelAsHTML(Hotel hotel) {
        String template = Main.htmlTemplate;
        assert template != null;
        template = template.replaceAll("HOTEL_NAME", hotel.getName());
        template = template.replaceFirst("DESCRIPTION", hotel.getDescription());
        template = template.replaceFirst("RATING_", hotel.getRating());
        template = template.replaceFirst("ADDRESS_", hotel.getAddress());
        template = template.replaceFirst("AIR_CONDITIONING", hotel.getAirConditioning());

        StringBuilder rooms = new StringBuilder();
        for (Room room : hotel.getRooms()) {
            rooms.append(buildRoom(room));
        }

        template = template.replaceFirst("ROOM_FILL", rooms.toString());
        template = template.replaceFirst("QR_CODE", new HotelRepositoryImpl().getQRCodeURL(hotel));
        return template;
    }
    public String buildRoom(Room room) {
        return "<ul>\n" +
            "  <li>Floor: " + room.getFloor() + "</li>\n" +
            "  <li>Room number: " + room.getRoomNumber() + "</li>\n" +
            "</ul>";
    }
}
