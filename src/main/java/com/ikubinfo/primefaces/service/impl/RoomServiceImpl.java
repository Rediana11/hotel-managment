package com.ikubinfo.primefaces.service.impl;

import java.util.List;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.repository.RoomRepository;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;

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
	public Room getRoom(int id) {
		return roomRepository.getRoom(id);
	}

	@Override
	public boolean save(Room room) {
		//category.setLastUpdated(new Date());
		return roomRepository.save(room);

	}

	@Override
	public boolean create(Room room) {
		//category.setLastUpdated(new Date());
		return roomRepository.create(room);

	}

	@Override
	public void delete(Room room) throws CategoryInUseException {
		/* if (roomRepository.isCategoryInUse(category)) {
			throw new CategoryInUseException("Cannot delete this category because it is already in use.");
		} else {

		 */
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
