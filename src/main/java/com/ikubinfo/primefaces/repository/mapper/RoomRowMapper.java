package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ikubinfo.primefaces.model.*;
import org.springframework.jdbc.core.RowMapper;

public class RoomRowMapper implements RowMapper<Room> {

	@Override
	public Room mapRow(ResultSet result, int rowNum) throws SQLException {
		Room room = new Room();
		RoomCategory category = new RoomCategory();
		RoomAbility roomAbility = new RoomAbility();
		RoomFacility roomFacility = new RoomFacility();
		List<RoomFacility> facilities = new ArrayList<RoomFacility>();
		User user = new User();
		User user1 = new User();
		roomFacility.setName(result.getString("facilities"));
		room.setFacilities(result.getString("facilities"));
		room.setId(result.getInt("room_id"));
		room.setName(result.getString("room_name"));
		room.setDescription(result.getString("description"));
		room.setPrice(result.getDouble("price"));
		room.setBedsNumber(result.getInt("beds_number"));
		category.setId(result.getInt("category_id"));
		category.setName( result.getString("category_name"));
		roomAbility.setId(result.getInt("room_ability_id"));
		roomAbility.setName(result.getString("ability_name"));
		user.setUsername(result.getString("created_by"));
		user1.setUsername(result.getString("updatedBy"));
		room.setCreatedBy(user);
		room.setUpdatedBy(user1);
		room.setCreatedOn(result.getDate("created_on"));
		room.setUpdatedOn(result.getDate("updated_on"));
		room.setRoomAbility(roomAbility);
		facilities.add(roomFacility);
		room.setRoomFacilities(facilities);


		room.setRoomCategory(category);
		return room;
	}


}
