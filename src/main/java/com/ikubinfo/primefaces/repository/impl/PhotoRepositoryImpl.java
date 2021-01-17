package com.ikubinfo.primefaces.repository.impl;

import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.repository.PhotoRepository;
import com.ikubinfo.primefaces.repository.mapper.PhotoRowMapper;
import com.ikubinfo.primefaces.repository.mapper.RoomRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {

    private static final String GET_PHOTOS = "select room_photo_id,r.is_valid,  r.room_id, file_name,  file_path, file_type, file_size, room_name,\n" +
            "\t\t           r.created_on, (ue.first_name || ' ' || ue.last_name) as created_by\n" +
            "\t\t           from room_photo r join user_ ue on r.created_by= ue.user_id \n" +
            "\t\t\tjoin room on room.room_id=r.room_id\n" +
            "\t\t\twhere r.is_valid=true and r.room_id=:id";

    private static final String GET_PHOTO = "select room_photo_id,  r.room_id, file_name,  file_path, file_type, file_size, room_name,\n" +
            "\t\t           r.created_on, (ue.first_name || ' ' || ue.last_name) as created_by\n" +
            "\t\t           from room_photo r join user_ ue on r.created_by= ue.user_id \n" +
            "\t\t\tjoin room on room.room_id=r.room_id\n" +
            "\t\t\twhere r.is_valid=true  and r.room_id =: id";

    private static final String DELETE_PHOTO = "update room_photo set is_valid='false' where room_photo_id=:id";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertPhotoQuery;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoRepositoryImpl(DataSource datasource) {
        super();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.insertPhotoQuery = new SimpleJdbcInsert(datasource).withTableName("room_photo")
                .usingGeneratedKeyColumns("room_photo_id");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<RoomPhoto> getAll(int id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);

            return namedParameterJdbcTemplate.query(GET_PHOTOS, params, new PhotoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public RoomPhoto getRoomPhoto(int id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return namedParameterJdbcTemplate.queryForObject(GET_PHOTO, params, new PhotoRowMapper());

    }

    @Override
    public void delete(RoomPhoto roomPhoto) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("id", roomPhoto.getId());

        this.namedParameterJdbcTemplate.update(DELETE_PHOTO, namedParameters);

    }

    @Override
    public boolean create(RoomPhoto room) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("room_photo_id", room.getId());
        parameters.put("file_name", room.getName());
        parameters.put("file_size", room.getSize());
        parameters.put("file_type", room.getType());
        parameters.put("file_path", room.getPath());
        parameters.put("room_id", room.getRoom().getId());
        parameters.put("created_on", new Date());
        parameters.put("is_valid", "true");

        return insertPhotoQuery.execute(parameters) > 0;

    }
}
