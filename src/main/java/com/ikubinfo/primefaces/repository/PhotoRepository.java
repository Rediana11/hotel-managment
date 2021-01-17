package com.ikubinfo.primefaces.repository;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomPhoto;

import java.util.List;

public interface PhotoRepository {

    List<RoomPhoto> getAll(int id);

    RoomPhoto getRoomPhoto(int id);

    void delete(RoomPhoto roomPhoto);

    boolean create(RoomPhoto photo);
}
