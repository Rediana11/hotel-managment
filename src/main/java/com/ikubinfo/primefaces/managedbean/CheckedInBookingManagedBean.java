package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.Client;
import com.ikubinfo.primefaces.model.Logs;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.service.*;
import com.ikubinfo.primefaces.service.helpers.SelectRoom;
import com.ikubinfo.primefaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class CheckedInBookingManagedBean implements Serializable {

    private Booking booking;
    private List<Booking> bookings;
    private List<Booking> activeBookings;
    private List<Booking> canceledBookings;
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

    @ManagedProperty(value = "#{roomService}")
    private RoomService roomService;


    @ManagedProperty(value = "#{emailService}")
    private EmailService emailService;


    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        booking = new Booking();
        checkIn = new Date();
        checkOut = new Date();
        bookings = bookingService.getCheckedInBookings(null, null);
        today = new Date();
        long oneDay = 24 * 60 * 60 * 1000;
        minDate = new Date(today.getTime() + (oneDay));


    }

    public void delete() {
        bookingService.delete(booking);
        bookings = bookingService.getCheckedInBookings(checkIn, checkOut);
        messages.showInfoMessage("Deleted");

    }

    public void loadBooking() {

        booking = bookingService.getBooking(booking.getId());
        if (booking == null) {
            messages.showErrorMessage("Booking with this id does not exist");
        }
    }

    public void changeStatusToCheckedOut() {
        booking.setRooms(roomService.getReservedRoomsForBooking(booking.getId()));
        if (bookingService.updateBookingStatusToCheckedOut(booking))
        {
            messages.showInfoMessage("Booking status changed successfully");
            bookings = bookingService.getCheckedInBookings(null, null);

        }
        else messages.showErrorMessage("There was a problem changing the booking status");
    }

    public void filter() {
        bookings = bookingService.getCheckedInBookings(checkIn, checkOut);
    }

    public void reset() {
        bookings = bookingService.getCheckedInBookings(null, null);
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Booking> getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(List<Booking> activeBookings) {
        this.activeBookings = activeBookings;
    }

    public List<Booking> getCanceledBookings() {
        return canceledBookings;
    }

    public void setCanceledBookings(List<Booking> canceledBookings) {
        this.canceledBookings = canceledBookings;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
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

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }
}
