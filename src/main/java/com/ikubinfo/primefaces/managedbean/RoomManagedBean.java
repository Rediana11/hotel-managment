package com.ikubinfo.primefaces.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomFacility;
import com.ikubinfo.primefaces.model.User;
import com.ikubinfo.primefaces.service.EmailService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class RoomManagedBean implements Serializable {
	private static final long serialVersionUID = 3800933422824282320L;
	private Room room;

	private List<Room>  reservedRoomsForBooking;
	private List<Room>  vacantRooms;
	private List<Room> rooms;
	private List<RoomFacility> facilities;
	private RoomFacility roomFacility;
	private String name;
	private User user = new User();

	@ManagedProperty(value = "#{roomService}")
	private RoomService roomService;

	@ManagedProperty(value = "#{messages}")
	private Messages messages;


	@ManagedProperty(value="#{bookingManagedBean}")
	private BookingManagedBean bookingManagedBean;



	@PostConstruct
	public void init() {


		rooms = roomService.getAll(null);
		vacantRooms= roomService.getAllVacantRooms(bookingManagedBean.getBooking());
		room = new Room();

	}

	public void loadReservedRooms(){
		reservedRoomsForBooking=roomService.getReservedRoomsForBooking(bookingManagedBean.getBooking().getId());

	}

	public void save() {
		if (roomService.save(room)) {
			getAll();
			messages.showInfoMessage("Room updated successfully");
		}
		room = new Room();

	}

	public void filter() {
		rooms = roomService.getAll(name);
	}

	public void delete() {
		try {
			roomService.delete(room);
			rooms = roomService.getAll(null);
			messages.showInfoMessage("Deleted");

		} catch (CategoryInUseException e) {
			messages.showWarningMessage(e.getMessage());
		}

	}

	public void getAll() {
		rooms = roomService.getAll(null);
	}

	public void reset() {
		name = null;
		filter();
	}

	public List<Room> getVacantRooms() {
		return vacantRooms;
	}

	public void setVacantRooms(List<Room> vacantRooms) {
		this.vacantRooms = vacantRooms;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public List<Room> getReservedRoomsForBooking() {
		return reservedRoomsForBooking;
	}

	public void setReservedRoomsForBooking(List<Room> reservedRoomsForBooking) {
		this.reservedRoomsForBooking = reservedRoomsForBooking;
	}

	public BookingManagedBean getBookingManagedBean() {
		return bookingManagedBean;
	}

	public void setBookingManagedBean(BookingManagedBean bookingManagedBean) {
		this.bookingManagedBean = bookingManagedBean;
	}

	public List<RoomFacility> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<RoomFacility> facilities) {
		this.facilities = facilities;
	}

	public RoomFacility getRoomFacility() {
		return roomFacility;
	}

	public void setRoomFacility(RoomFacility roomFacility) {
		this.roomFacility = roomFacility;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
