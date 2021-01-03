package com.ikubinfo.primefaces.service.impl;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.RoomPhoto;
import com.ikubinfo.primefaces.repository.BookingRepository;
import com.ikubinfo.primefaces.repository.PhotoRepository;
import com.ikubinfo.primefaces.service.PhotoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("photoService")
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        super();
        this.photoRepository = photoRepository;
    }

    @Override
    public List<RoomPhoto> getAll() {

        return photoRepository.getAll();

    }

    @Override
    public RoomPhoto getRoomPhoto(int id) {
        return photoRepository.getRoomPhoto(id);
    }

    @Override
    public void delete(RoomPhoto roomPhoto) {

        photoRepository.delete(roomPhoto);
    }

    @Override
    public boolean create(RoomPhoto photo) {
        return photoRepository.create(photo);
    }
}
