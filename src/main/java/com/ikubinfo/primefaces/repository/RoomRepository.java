package com.ikubinfo.primefaces.repository;

import java.util.List;

import com.ikubinfo.primefaces.model.*;

public interface RoomRepository {

	List<Room> getAll(String name);

	List<Room> getAllVacantRooms(Booking booking);

	List<Room> getReservedRoomsForBooking(int id);

	List<RoomFacility> getRoomFacilities();

	Room getRoom(int id);

	RoomAbility getAbility(int id);

	boolean save(Room room);

	boolean create(List<RoomPhoto> photos,Room room);

	void delete(Room room);

	List<RoomCategory> getCategories();

	List<RoomAbility> getRoomAbilities();


}