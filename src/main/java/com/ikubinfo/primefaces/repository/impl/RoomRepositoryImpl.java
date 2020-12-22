package com.ikubinfo.primefaces.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.model.RoomCategory;
import com.ikubinfo.primefaces.repository.mapper.RoomAbilityRowMapper;
import com.ikubinfo.primefaces.repository.mapper.RoomCategoryRowMapper;
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
import com.ikubinfo.primefaces.repository.mapper.RoomRowMapper;

@Repository
class RoomRepositoryImpl implements RoomRepository {

	Logger logger = LoggerFactory.getLogger(RoomRepositoryImpl.class);

	private static final String GET_ROOMS = "select room_id,ue.username as created_by, u.username as updated_by, room_name, description, price, beds_number, facilities,category_name,\n" +
			"ability_name, r.created_on, r.updated_on\n" +
			"from room r  join category ct on r.category_id=ct.category_id \n" +
			"join room_ability ra on r.room_ability_id=ra.room_ability_id \n" +
			"join user_ u on r.updated_by=u.user_id  join user_ ue on r.created_by=ue.user_id where r.is_valid=true";
	private static final String UPDATE_ROOM ="update room set room_name= :name, description= :description, price= :price, " +
			"beds_number= :bedsNumber, category_id=:category, room_ability_id=:ability where room_id=:id";
	private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
	private static final String DELETE_ROOM = "update room set is_valid= false where room_id=:id";
	private static final String GET_CATEGORIES = "select category_id, category_name from category";
	private static final String GET_ABILITIES = "select room_ability_id, ability_name from room_ability";
	private static final String GET_ROOM = "select room_id,ue.username as created_by, u.username as updated_by, room_name, description, price, \n" +
			"beds_number, facilities,category_name,ability_name, r.created_on, r.updated_on\n" +
			"\t\t\tfrom room r  join category ct on r.category_id=ct.category_id \n" +
			"\t\t\tjoin room_ability ra on r.room_ability_id=ra.room_ability_id \n" +
			"\t\t\tjoin user_ u on r.updated_by=u.user_id  join user_ ue on r.created_by=ue.user_id where r.is_valid=true\n" +
			"\t\t\tand room_id=1";


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
		parameters.put("created_by", room.getCreatedBy());
		parameters.put("created_on", room.getCreatedOn());
		parameters.put("updated_by", room.getUpdatedBy());
		parameters.put("updated_on", room.getUpdatedOn());
		parameters.put("is_valid", room.isValid());

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


		String queryString = GET_CATEGORIES;

		return namedParameterJdbcTemplate.query(queryString, new RoomCategoryRowMapper());
	}

	@Override
	public List<RoomAbility> getRoomAbilities(){


		String queryString = GET_ABILITIES;

		return namedParameterJdbcTemplate.query(queryString, new RoomAbilityRowMapper());
	}

	@Override
	public Room getRoom(int id) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id );
		String queryString = GET_ROOM;

		return  namedParameterJdbcTemplate.queryForObject(queryString, params, new RoomRowMapper());

	}

}
