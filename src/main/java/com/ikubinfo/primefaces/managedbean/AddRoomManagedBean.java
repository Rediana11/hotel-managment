package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.repository.RoomRepository;
import com.ikubinfo.primefaces.service.EmailService;
import com.ikubinfo.primefaces.service.PhotoService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ManagedBean
@ViewScoped
public class AddRoomManagedBean {

    private static final Logger LOG = LoggerFactory.getLogger(RoomRepository.class);

    private Room room;
    private List<RoomCategory> categories;
    private List<RoomAbility> roomAbilities;
    private List<RoomFacility> roomFacilities;
    private RoomFacility facility;
    private Integer[] selectedFacilities;
    private RoomCategory roomCategory;
    private RoomAbility roomAbility;
    private List<RoomPhoto> roomPhotos;
    private RoomPhoto roomPhoto;
    private List facilitiesList;


    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @ManagedProperty(value = "#{roomService}")
    private RoomService roomService;

    @ManagedProperty(value = "#{emailService}")
    private EmailService emailService;


    @ManagedProperty(value = "#{loggedUserMangedBean}")
    private LoggedUserMangedBean loggedUserMangedBean;


    @ManagedProperty(value = "#{photoService}")
    private PhotoService photoService;

    @PostConstruct
    public void init() {
        room = new Room();
        categories = roomService.getCategories();
        roomAbilities = roomService.getRoomAbilities();
        roomCategory = new RoomCategory();
        roomAbility = new RoomAbility();
        roomFacilities = roomService.getRoomFacilities();
        int facilitiesNr = roomFacilities.size();
        selectedFacilities = new Integer[facilitiesNr];
        facility = new RoomFacility();
        facilitiesList = Arrays.asList(selectedFacilities);
        roomPhotos = new ArrayList<>();
        roomPhoto = new RoomPhoto();

    }

    private void addFacilitiesToRoom(Room room) {
        for (Integer roomFacilityId : selectedFacilities) {
            room.getRoomFacilities().add(new RoomFacility(roomFacilityId));
        }
    }


    public String save() {
        room.setRoomCategory(roomCategory);
        room.setRoomAbility(roomAbility);
        addFacilitiesToRoom(room);
        room.setCreatedBy(loggedUserMangedBean.getUser());
        room.setUpdatedBy(loggedUserMangedBean.getUser());
        if (room.getId() == null) {
            if (roomService.create(roomPhotos, room)) {
                messages.showInfoMessage("Room added Successfully!");

            } else
                messages.showErrorMessage("There was a problem adding the room");


        }   try {
            if (roomService.updateRoom(room)) {
                messages.showInfoMessage("Room updated successfully");

            }
        }
        catch (CategoryInUseException e) {
            messages.showWarningMessage(e.getMessage());
        }
        return "room";

    }


    public void loadRoom() {
        if (room.getId() != null) {
            room = roomService.getRoom(room.getId());
            roomCategory = room.getRoomCategory();
            roomAbility = room.getRoomAbility();
            facilitiesList = Collections.singletonList(room.getFacilities());
            roomPhotos = photoService.getAll(room.getId());
        }
    }


    public void upload(FileUploadEvent event) {
        roomService.upload(event, roomPhotos);
    }

    public void deleteRoomPhoto() {
        Path path
                = Paths.get("/photos/" + roomPhoto.getName());
        try {
            Files.deleteIfExists(path);
            roomPhotos.remove(roomPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<RoomCategory> getCategories() {
        return categories;
    }

    public PhotoService getPhotoService() {
        return photoService;
    }

    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public void setCategories(List<RoomCategory> categories) {
        this.categories = categories;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public RoomAbility getRoomAbility() {
        return roomAbility;
    }

    public void setRoomAbility(RoomAbility roomAbility) {
        this.roomAbility = roomAbility;
    }

    public RoomPhoto getRoomPhoto() {
        return roomPhoto;
    }

    public void setRoomPhoto(RoomPhoto roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    public List<RoomAbility> getRoomAbilities() {
        return roomAbilities;
    }

    public void setRoomAbilities(List<RoomAbility> roomAbilities) {
        this.roomAbilities = roomAbilities;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public List<RoomFacility> getRoomFacilities() {
        return roomFacilities;
    }

    public void setRoomFacilities(List<RoomFacility> roomFacilities) {
        this.roomFacilities = roomFacilities;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public Integer[] getSelectedFacilities() {
        return selectedFacilities;
    }


    public void setSelectedFacilities(Integer[] selectedFacilities) {
        this.selectedFacilities = selectedFacilities;
    }

    public RoomFacility getFacility() {
        return facility;
    }

    public void setFacility(RoomFacility facility) {
        this.facility = facility;
    }

    public List<RoomPhoto> getRoomPhotos() {
        return roomPhotos;
    }


    public void setRoomPhotos(List<RoomPhoto> roomPhotos) {
        this.roomPhotos = roomPhotos;
    }

    public List getFacilitiesList() {
        return facilitiesList;
    }

    public void setFacilitiesList(List facilitiesList) {
        this.facilitiesList = facilitiesList;
    }

    public LoggedUserMangedBean getLoggedUserMangedBean() {
        return loggedUserMangedBean;
    }

    public void setLoggedUserMangedBean(LoggedUserMangedBean loggedUserMangedBean) {
        this.loggedUserMangedBean = loggedUserMangedBean;
    }
}