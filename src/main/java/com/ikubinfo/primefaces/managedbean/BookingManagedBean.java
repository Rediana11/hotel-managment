package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.service.BookingService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class BookingManagedBean implements Serializable {

    private Booking booking;

    private List<Booking> bookings;

    @ManagedProperty(value = "#{bookingService}")
    private BookingService bookingService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {

        bookings = bookingService.getAll();
        booking = new Booking();

    }

    public void delete() {
            bookingService.delete(booking);
            bookings = bookingService.getAll();
            messages.showInfoMessage("Deleted");

    }

    public void save(){
        if (bookingService.save(booking)) {
            getAll();
            messages.showInfoMessage("Booking updated successfully");

        }
        booking = new Booking();    }


    public void getAll() {
        bookings = bookingService.getAll();
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


    public BookingService getBookingService() {
        return bookingService;
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
}
