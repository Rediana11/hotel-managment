package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.BookingStatus;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.model.RoomAbility;
import com.ikubinfo.primefaces.service.BookingService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.util.Messages;
import org.primefaces.event.FlowEvent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class BookingManagedBean implements Serializable {

    private Booking booking;

    private BookingStatus bookingStatus;
    private List<Booking> bookings;
    private List<BookingStatus> bookingStatuses;
    private List<SelectItem> statusItems;

    private Date checkIn;
    private Date checkOut;
    private Date minDate;
    private Date today;
    private Date maxDate;

    private boolean quietRoom;

    @ManagedProperty(value = "#{bookingService}")
    private BookingService bookingService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        booking = new Booking();
        bookingStatus = new BookingStatus();
        bookings = bookingService.getAll();
        statusItems = new ArrayList<SelectItem>();
        bookingStatuses = bookingService.getBookingStatuses();
        for (BookingStatus status: bookingStatuses){
            statusItems.add(new SelectItem(status.getId(),status.getName()));
        }

        today = new Date();
        long oneDay = 24 * 60 * 60 * 1000;

        minDate =new Date(today.getTime() + ( oneDay));
    }

    public void delete() {
            bookingService.delete(booking);
            bookings = bookingService.getAll();
            messages.showInfoMessage("Deleted");

    }

    public Booking loadBooking(){
        return booking=bookingService.getBooking(booking.getId());
    }

    public void save(){
        if (bookingService.save(booking)) {
            getAll();
            messages.showInfoMessage("Booking updated successfully");

        }
        booking = new Booking();
    }


        public void changeStatus(){
            if (bookingService.updateBookingStatus(booking))
            messages.showInfoMessage("Booking status changed successfully");

        }

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

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public List<BookingStatus> getBookingStatuses() {
        return bookingStatuses;
    }

    public void setBookingStatuses(List<BookingStatus> bookingStatuses) {
        this.bookingStatuses = bookingStatuses;
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


    public boolean isQuietRoom() {
        return quietRoom;
    }

    public void setQuietRoom(boolean quietRoom) {
        this.quietRoom = quietRoom;
    }


    public List<SelectItem> getStatusItems() {
        return statusItems;
    }

    public void setStatusItems(List<SelectItem> statusItems) {
        this.statusItems = statusItems;
    }
}
