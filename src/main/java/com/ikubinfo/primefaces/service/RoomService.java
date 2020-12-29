package com.ikubinfo.primefaces.service;

import java.util.List;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;

public interface RoomService {

	List<Room> getAll(String name);

	List<Room> getAllVacantRooms();

	List<Room> getReservedRoomsForBooking(int id);

	Room getRoom(int id);

	boolean save(Room room);

	boolean create(Room room);

	void delete(Room room) throws CategoryInUseException;

	List<RoomCategory> getCategories();

	List<RoomAbility> getRoomAbilities();


}