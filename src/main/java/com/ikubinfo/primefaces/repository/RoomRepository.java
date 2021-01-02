package com.ikubinfo.primefaces.repository;

import java.util.List;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;

public interface RoomRepository {

	List<Room> getAll(String name);

	List<Room> getAllVacantRooms();

	List<Room> getReservedRoomsForBooking(int id);

	Room getRoom(int id);

	RoomAbility getAbility(int id);

	boolean save(Room room);

	boolean create(Room room);

	//boolean isCategoryInUse(Room room);

	void delete(Room room);

	List<RoomCategory> getCategories();

	List<RoomAbility> getRoomAbilities();


}