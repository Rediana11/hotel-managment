package com.ikubinfo.primefaces.repository.impl;

import java.util.*;

import javax.sql.DataSource;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.repository.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.repository.RoomRepository;

@Repository
class RoomRepositoryImpl implements RoomRepository {

	Logger logger = LoggerFactory.getLogger(RoomRepositoryImpl.class);

	private static final String GET_ROOMS = "select room_id,  r.room_ability_id, r.category_id,  room_name,  description,beds_number, price, facilities, category_name,ability_name, r.updated_on,\n" +
			"            r.created_on, (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
			"            CASE WHEN r.updated_by is not null \n" +
			"            then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=r.updated_by) else '' end as UpdatedBy\n" +
			"            from room r\n" +
			"            join user_ ue on r.created_by=ue.user_id \n" +
			"\t\t\tjoin category on r.category_id=category.category_id \n" +
			"\t\t\tjoin room_ability ra on ra.room_ability_id=r.room_ability_id\n" +
			"\t\t\twhere r.is_valid=true ";
	private static final String UPDATE_ROOM ="update room set room_name= :name, description= :description, price= :price, " +
			"beds_number= :bedsNumber, category_id=:category, room_ability_id=:ability where room_id=:id";
	private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
	private static final String GET_VACANT_ROOMS = "select room_id,room_name,description, facilities,beds_number, price, category_name from \n" +
			"room join room_ability ra on room.room_ability_id= ra.room_ability_id\n" +
			"join category on room.category_id=category.category_id\n" +
			"where ra.code='V'" ;
	private static final String DELETE_ROOM = "update room set is_valid= false where room_id=:id";
	private static final String GET_CATEGORIES = "select category_id, category_name from category";
	private static final String GET_ABILITIES = "select room_ability_id, ability_name from room_ability";
	private static final String GET_ABILITY = "select room_ability_id, ability_name from room_ability where room_ability_id=:id";

	private static final String GET_ROOM = "select room_id, r.room_ability_id, r.category_id,  room_name,  description,beds_number, price, facilities, category_name,ability_name, r.updated_on,\n" +
			"            r.created_on, (ue.first_name || ' ' || ue.last_name) as created_by,\n" +
			"            CASE WHEN r.updated_by is not null \n" +
			"            then (select (u.first_name || ' ' || u.last_name) from user_ u where u.user_id=r.updated_by) else '' end as UpdatedBy\n" +
			"            from room r\n" +
			"            join user_ ue on r.created_by=ue.user_id \n" +
			"\t\t\tjoin category on r.category_id=category.category_id \n" +
			"\t\t\tjoin room_ability ra on ra.room_ability_id=r.room_ability_id\n" +
			"\t\t\twhere r.is_valid=true and room_id=:id";

	private static final String RESERVED_ROOMS_FOR_BOOKING ="select room.room_id,room_name,description, facilities,beds_number, room.price, category_name from \n" +
			"room join room_booking rb on room.room_id = rb.room_id\n" +
			"join booking on booking.booking_id=rb.booking_id\n" +
			"join category on room.category_id=category.category_id\n" +
			"where rb.booking_id=:id";


	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertRoomQuery;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public RoomRepositoryImpl(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.insertRoomQuery = new SimpleJdbcInsert(datasource).withTableName("room")
				.usingGeneratedKeyColumns("room_id");
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Room> getAll(String name) {

		Map<String, Object> params = new HashMap<>();
		params.put("name", "%" + name + "%");

		String queryString = GET_ROOMS;

		if (!Objects.isNull(name) && !name.isEmpty()) {
			queryString = queryString.concat(" and  room_name like  :name ");
		}
		return namedParameterJdbcTemplate.query(queryString, params, new RoomRowMapper());

	}

	@Override
	public List<Room> getAllVacantRooms() {

		return namedParameterJdbcTemplate.query(GET_VACANT_ROOMS, new VacantRoomRowMapper());

	}

	@Override
	public List<Room> getReservedRoomsForBooking(int id) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id );
		return namedParameterJdbcTemplate.query(RESERVED_ROOMS_FOR_BOOKING,params, new VacantRoomRowMapper());
	}


	@Override
	public boolean save(Room room) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("name", room.getName());
		namedParameters.addValue("description", room.getDescription());
		namedParameters.addValue("price", room.getPrice());
		namedParameters.addValue("bedsNumber", room.getBedsNumber());
		namedParameters.addValue("category", room.getRoomCategory().getId());
		namedParameters.addValue("ability", room.getRoomAbility().getId());
		namedParameters.addValue("id", room.getId());

		int updatedCount = this.namedParameterJdbcTemplate.update(UPDATE_ROOM, namedParameters);

		return updatedCount > 0;
	}

	@Override
	public boolean create(Room room) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("room_id", room.getId());
		parameters.put("room_name", room.getName());
		parameters.put("description", room.getDescription());
		parameters.put("price", room.getPrice());
		parameters.put("beds_number", room.getBedsNumber());
		parameters.put("category_id", room.getRoomCategory().getId());
		parameters.put("room_ability_id", room.getRoomAbility().getId());
		//parameters.put("created_by", room.getCreatedBy());
		parameters.put("created_by", 2); //TODO replace this with line 129
		parameters.put("created_on", new Date());
		parameters.put("is_valid", "true");

		return insertRoomQuery.execute(parameters) > 0;

	}

	/* @Override
	public boolean isCategoryInUse(Role category) {

		return jdbcTemplate.queryForObject(CATEGORY_IN_USE, Integer.class, category.getId()) > 0;

	}

	 */

	@Override
	public void delete(Room room) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("id", room.getId());

		this.namedParameterJdbcTemplate.update(DELETE_ROOM, namedParameters);

	}

	@Override
	public List<RoomCategory> getCategories(){


		return namedParameterJdbcTemplate.query(GET_CATEGORIES, new RoomCategoryBaseRowMapper());
	}

	@Override
	public List<RoomAbility> getRoomAbilities(){


		return namedParameterJdbcTemplate.query(GET_ABILITIES, new RoomAbilityRowMapper());
	}

	@Override
	public Room getRoom(int id) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id );
		String queryString = GET_ROOM;

		return  namedParameterJdbcTemplate.queryForObject(queryString, params, new RoomRowMapper());

	}

	@Override
	public RoomAbility getAbility(int id) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id );
		String queryString = GET_ABILITY;

		return  namedParameterJdbcTemplate.queryForObject(queryString, params, new RoomAbilityRowMapper());

	}

}
