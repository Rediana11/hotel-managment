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
    private String newEmail;
    private List<SelectRoom> selectedRooms;
    private SelectRoom selectedRoom;
    private Booking booking;
    private List<Booking> bookings;
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

    @ManagedProperty(value = "#{logsManagedBean}")
    private LogsManagedBean logsManagedBean;

    @ManagedProperty(value = "#{emailService}")
    private EmailService emailService;

    @ManagedProperty(value = "#{loggedUserMangedBean}")
    private LoggedUserMangedBean loggedUserMangedBean;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        booking = new Booking();
        client = new Client();
        checkIn = new Date();
        checkOut = new Date();
        today = new Date();
        bookings = bookingService.getBookings(null, null);
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


    public void bookingPrice() {
        double roomPrice = 0;
        for (SelectRoom selRoom : selectedRooms) {
            roomPrice = roomPrice + selRoom.getRoom().getPrice();
        }
        booking.setPrice(roomPrice);
    }

    public String reserve() {
        bookingPrice();
        booking.setCreatedBy(loggedUserMangedBean.getUser());
        if (finishReservation()) {
            logsManagedBean.addSuccessfulLog("Successful reservation");
            emailService.sendSimpleMessage(client.getEmail(), "Booking confirmed", emailText());
            messages.showInfoMessage("Successful reservation!");
            return "booking";
        } else {
            messages.showErrorMessage("There was a problem reserving the room");
            logsManagedBean.addErrorLog("There was a problem reserving the room");
        }

        return null;
    }

    public void getClientByEmail() {
        if (userService.getClientByEmail(email) != null) {
            client = userService.getClientByEmail(email);
            booking.setClient(client);
            messages.showInfoMessage("Client found!");
        } else
            messages.showWarningMessage("Client not found! Create a new client!");
    }

    private List<Room> mappSelectRoomToRoom(List<SelectRoom> selRooms) {
        List<Room> rooms = new ArrayList<Room>();
        for (SelectRoom el : selRooms) {
            rooms.add(el.getRoom());
        }
        return rooms;
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


    public void checkIfEmailExists() {
        if (userService.getClientByEmail(newEmail) == null) {
            client.setEmail(newEmail);
            userService.insertClient(client);
            booking.setClient(client);
            messages.showInfoMessage("Client added successfully!");

        } else {
            System.out.println(userService.getClientByEmail(newEmail).getEmail());
            messages.showErrorMessage("Can not add client! Client with this email already exists!");
        }
    }

    private boolean datesValidate() {
        if (booking.getCheckOut().getTime() <= booking.getCheckIn().getTime()) {
            messages.showErrorMessage("Input dates are not valid! Try again!");
            return false;
        }
        return true;
    }

    private boolean clientValidate() {
        if (booking.getClient() == null) {
            messages.showErrorMessage("Put client email to finish the reservation!");
            return false;
        }
        return true;
    }

    private boolean checkedRoomsValidate() {
        if (selectedRooms.isEmpty() || selectedRoom.getRoom().equals(null)) {
            messages.showErrorMessage("You must check a room to complete the reservation!");
            return false;
        }
        return true;
    }

    private boolean finishReservation() {
        if (bookingService.reserve(booking, mappSelectRoomToRoom(selectedRooms)) && checkedRoomsValidate() && datesValidate() && clientValidate()) {
            return true;
        }
        messages.showErrorMessage("Yor reservation data are not completed filled!");
        return false;
    }

    public void delete() {
        bookingService.delete(booking);
        bookings = bookingService.getBookings(null, null);
        messages.showInfoMessage("Booking deleted");
    }

    public void filterBookings() {
        bookings = bookingService.getBookings(checkIn, checkOut);
    }

    public void resetBookings() {
        bookings = bookingService.getBookings(null, null);
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

    public LoggedUserMangedBean getLoggedUserMangedBean() {
        return loggedUserMangedBean;
    }

    public void setLoggedUserMangedBean(LoggedUserMangedBean loggedUserMangedBean) {
        this.loggedUserMangedBean = loggedUserMangedBean;
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

    public LogsManagedBean getLogsManagedBean() {
        return logsManagedBean;
    }

    public void setLogsManagedBean(LogsManagedBean logsManagedBean) {
        this.logsManagedBean = logsManagedBean;
    }

    public Booking getBooking() {
        return booking;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BookingService getBookingService() {
        return bookingService;
    }
}
