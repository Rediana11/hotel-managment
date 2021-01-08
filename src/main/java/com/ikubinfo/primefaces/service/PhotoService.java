package com.ikubinfo.primefaces.service;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomPhoto;

import java.util.List;

public interface PhotoService {

    List<RoomPhoto> getAll(int id);

    RoomPhoto getRoomPhoto(int id);

    void delete(RoomPhoto roomPhoto);

    boolean create(RoomPhoto photo);

}
