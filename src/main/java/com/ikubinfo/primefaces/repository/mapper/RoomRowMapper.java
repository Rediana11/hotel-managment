package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ikubinfo.primefaces.model.*;
import org.springframework.jdbc.core.RowMapper;

public class RoomRowMapper implements RowMapper<Room> {
	RoomCategoryRowMapper roomCategoryRowMapper;


	@Override
	public Room mapRow(ResultSet result, int rowNum) throws SQLException {
		Room room = new Room();
		RoomCategory category = new RoomCategory();
		RoomAbility roomAbility = new RoomAbility();
		User user = new User();
		User user1 = new User();
		room.setId(result.getInt("room_id"));
		room.setName(result.getString("room_name"));
		room.setDescription(result.getString("description"));
		room.setPrice(result.getDouble("price"));
		room.setFacilities(result.getString("facilities"));
		room.setBedsNumber(result.getInt("beds_number"));
		category.setName( result.getString("category_name"));
		roomAbility.setName(result.getString("ability_name"));
		user.setUsername(result.getString("created_by"));
		user1.setUsername(result.getString("updated_by"));
		room.setCreatedBy(user);
		room.setUpdatedBy(user1);
		room.setCreatedOn(result.getDate("created_on"));
		room.setUpdatedOn(result.getDate("updated_on"));
		room.setRoomAbility(roomAbility);


		room.setRoomCategory(category);
		return room;
	}


}
