package com.ikubinfo.primefaces.service;

import java.util.List;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import org.primefaces.event.FileUploadEvent;

public interface RoomService {

	List<Room> getAll(String name);

	List<Room> getAllVacantRooms(Booking booking);

	List<Room> getReservedRoomsForBooking(int id);

	List<RoomFacility> getRoomFacilities();

	RoomAbility getAbility (int id);

	Room getRoom(int id);

	boolean updateRoom(Room room);

	boolean create(List<RoomPhoto> photos,Room room);

	void delete(Room room);

	List<RoomCategory> getCategories();

	List<RoomAbility> getRoomAbilities();

	void upload(FileUploadEvent event, List<RoomPhoto> photos);



}