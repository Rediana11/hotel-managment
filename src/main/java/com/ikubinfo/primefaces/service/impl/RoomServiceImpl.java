package com.ikubinfo.primefaces.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.service.helpers.FileHelper;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
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
    public Room getRoom(int id) {
        return roomRepository.getRoom(id);
    }

    @Override
    public void upload(FileUploadEvent event, List<RoomPhoto> photos) {
        if (event.getFile() != null) {
            UploadedFile file = event.getFile();
            try {
                RoomPhoto photo = new RoomPhoto();
                photo.setPath(FileHelper._PATH);
                FileHelper.createDir(photo.getPath());
                photo.setName(file.getFileName());
                photo.setSize(file.getSize() / 1024);
                photo.setType(file.getContentType());
                File fileImage = new File(photo.getPath() + File.separator + file.getFileName());
                photos.add(photo);

                try (InputStream inputStream = file.getInputStream();) {
                    FileHelper.saveImage(inputStream, fileImage, file.getContentType());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateRoom(Room room) throws CategoryInUseException {
        if (roomRepository.isRoomInUse(room)) {
            throw new CategoryInUseException(" Cannot update this room because it is already in use. ");
        } else {
            return roomRepository.updateRoom(room);

        }

    }

    @Override
    @Transactional
    public boolean create(List<RoomPhoto> photos, Room room) {

        return roomRepository.create(photos, room);

    }

    @Override
    public void delete(Room room) throws CategoryInUseException {

        if (roomRepository.isRoomInUse(room)) {
            throw new CategoryInUseException(" Can not delete this room because it is already in use. ");
        } else {
            roomRepository.delete(room);

        }
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
