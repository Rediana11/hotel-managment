package com.ikubinfo.primefaces.repository.mapper;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;



public class PhotoRowMapper implements RowMapper<RoomPhoto> {

    @Override
    public RoomPhoto mapRow(ResultSet result, int rowNum) throws SQLException {
        RoomPhoto photo= new RoomPhoto();
        User user = new User();
        Room room = new Room();

        photo.setId(result.getInt("room_photo_id"));
        photo.setName(result.getString("file_name"));
        photo.setSize(result.getLong("file_size"));
        photo.setType(result.getString("file_type"));
        photo.setPath(result.getString("file_path"));
        photo.setValid(result.getBoolean("is_valid"));
        user.setUsername(result.getString("created_by"));
        photo.setCreatedOn(result.getDate("created_on"));
        room.setName(result.getString("room_name"));
        photo.setRoom(room);
        photo.setCreatedBy(user);

        return  photo;
    }
}
