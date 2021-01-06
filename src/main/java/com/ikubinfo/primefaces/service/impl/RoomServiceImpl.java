package com.ikubinfo.primefaces.service.impl;

import java.util.List;

import com.ikubinfo.primefaces.model.*;
import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.repository.RoomRepository;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import org.springframework.transaction.annotation.Transactional;

@Service("roomService")
 class RoomServiceImpl implements RoomService {

	private RoomRepository roomRepository;

	public RoomServiceImpl(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Override
	public List<Room> getAll(String name) {
		return roomRepository.getAll(name);
	}

	@Override
	public List<Room> getAllVacantRooms(Booking booking) {
		return roomRepository.getAllVacantRooms(booking);
	}

	@Override
	public List<Room> getReservedRoomsForBooking(int id) {
		return roomRepository.getReservedRoomsForBooking(id);
	}

	@Override
	public List<RoomFacility> getRoomFacilities() {
		return roomRepository.getRoomFacilities();
	}

	@Override
	public RoomAbility getAbility(int id) {
		return roomRepository.getAbility(id);
	}

	@Override
	public Room getRoom(int id) {
		return roomRepository.getRoom(id);
	}

	@Override
	public boolean save(Room room) {
		//category.setLastUpdated(new Date());
		return roomRepository.save(room);

	}

	@Override
	@Transactional
	public boolean create(Room room) {

		return roomRepository.create(room);

	}

	@Override
	public void delete(Room room) {

			roomRepository.delete(room);

	}

	@Override
	public List<RoomCategory> getCategories() {
		return roomRepository.getCategories();
	}

	@Override
	public List<RoomAbility> getRoomAbilities() {
		return roomRepository.getRoomAbilities();
	}
}
