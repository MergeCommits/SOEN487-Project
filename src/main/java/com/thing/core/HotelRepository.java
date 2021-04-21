package com.thing.core;

public interface HotelRepository {
    void addOrReplaceHotel(Hotel p);
    boolean removeHotel(String name);
    Hotel getHotel(String name);

    boolean addOrReplaceRoom(Room room, String hotelName);
    boolean removeRoom(int roomNumber);
    Room getRoom(int roomNumber);

    String getQRCodeURL(Hotel hotel);
}
