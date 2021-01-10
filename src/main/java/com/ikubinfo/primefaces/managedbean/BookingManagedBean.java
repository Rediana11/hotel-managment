package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.service.*;
import com.ikubinfo.primefaces.service.helpers.SelectRoom;
import com.ikubinfo.primefaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "bookingManagedBean")
@ViewScoped
public class BookingManagedBean implements Serializable {

    private Client client;
    private String email;
    private List<SelectRoom> selectedRooms;
    private SelectRoom selectedRoom;
    private Booking booking;
    private List<Room> rooms;
    private Date checkIn;
    private Date checkOut;
    private Date minDate;
    private Date today;
    private Date maxDate;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{bookingService}")
    private BookingService bookingService;

    @ManagedProperty(value = "#{logsService}")
    private LogsService logs;

    @ManagedProperty(value = "#{emailService}")
    private EmailService emailService;


    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        booking = new Booking();
        client = new Client();
        checkIn = new Date();
        checkOut = new Date();
        today = new Date();
        long oneDay = 24 * 60 * 60 * 1000;
        minDate = new Date(today.getTime() + (oneDay));
        selectedRooms = new ArrayList<SelectRoom>();
        selectedRoom = new SelectRoom();
    }
    public void loadBooking() {

        booking = bookingService.getBooking(booking.getId());
        if (booking == null) {
            messages.showErrorMessage("Booking with this id does not exist");
        }
    }

    public boolean datesValidate() {
        if (booking.getCheckOut().getTime() <= booking.getCheckIn().getTime()) {
            messages.showErrorMessage("Input dates are not valid! Try again!");
            return false;
        }
        return true;
    }

    public boolean checkedRoomsValidate() {
        if (selectedRooms.isEmpty() || selectedRoom.getRoom().equals(null)) {
            messages.showErrorMessage("You must check a room to complete the reservation!");
            return false;
        }
        return true;
    }


    private boolean getClientByEmail() {
        for (Client client1 : userService.getClients()) {
            if (email.equals(client1.getEmail())) {
                client = userService.getClientByEmail(email);
                booking.setClient(client);
                return true;
            }
        }
        return false;
    }

    public void isClientRegistred() {
        if (getClientByEmail()) {
            messages.showInfoMessage("Client found!");
        } else
            messages.showWarningMessage("Client not found! Create a new client!");
    }


    public void selectRoom(Room room) {
        selectedRoom.setRoom(room);
        SelectRoom tempRoom = new SelectRoom();
        tempRoom.setRoom(room);
        if (selectedRoom.isSelected()) {
            tempRoom.setSelected(selectedRoom.isSelected());
            selectedRooms.add(tempRoom);
        } else {
            tempRoom.setSelected(!selectedRoom.isSelected());
            selectedRooms.remove(tempRoom);
        }
    }


    private List<Room> mappSelectRoomToRoom(List<SelectRoom> selRooms) {
        List<Room> rooms = new ArrayList<Room>();
        for (SelectRoom el : selRooms) {
            rooms.add(el.getRoom());
        }
        return rooms;
    }

    public void bookingPrice() {
        double roomPrice = 0;
        for (SelectRoom selRoom : selectedRooms) {
            roomPrice = roomPrice + selRoom.getRoom().getPrice();
        }
        booking.setPrice(roomPrice);
    }

    public String reserve() {
        bookingPrice();
        if (bookingService.reserve(booking, mappSelectRoomToRoom(selectedRooms)) && checkedRoomsValidate() && datesValidate()) {
            logs.addSuccessfulLog("Successful reservation");
            emailService.sendSimpleMessage(client.getEmail(), "Reservation Confirmation", emailText());
            messages.showInfoMessage("Successful reservation!");
            return "booking";
        }
        else messages.showErrorMessage("There was a problem reserving the room");


        return null;
    }

    private String emailText() {
        String roomText = null;
        for (Room room : mappSelectRoomToRoom(selectedRooms)) {
            roomText = room.getName() + "  " + room.getDescription() + "\n";
        }
        String text = "Hello " + client.getFirstName() + " " + client.getLastName() + "\n\n\n You have reserved room " + roomText
                + "in dates " + booking.getCheckIn() + " - " + booking.getCheckOut() + "\n\n\n Booking price:" + booking.getPrice()
                + "\n\n You are welcomed! :)";
        return text;
    }


    public void insertClient() {
        if (userService.insertClient(client)) {
            client.setId(userService.getMaxBookingId());
            booking.setClient(client);
            messages.showInfoMessage("Client added successfully!");
        } else
            messages.showErrorMessage("There was a problem adding the client");
    }




    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public List<SelectRoom> getSelectedRooms() {
        return selectedRooms;
    }

    public void setSelectedRooms(List<SelectRoom> selectedRooms) {
        this.selectedRooms = selectedRooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public SelectRoom getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(SelectRoom selectedRoom) {
        this.selectedRoom = selectedRoom;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public LogsService getLogs() {
        return logs;
    }

    public void setLogs(LogsService logs) {
        this.logs = logs;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BookingService getBookingService() {
        return bookingService;
    }
}
