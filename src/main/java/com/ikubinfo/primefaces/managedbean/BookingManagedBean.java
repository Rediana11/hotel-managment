package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Booking;
import com.ikubinfo.primefaces.model.Room;
import com.ikubinfo.primefaces.service.BookingService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.util.Messages;
import org.primefaces.event.FlowEvent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class BookingManagedBean implements Serializable {

    private Booking booking;

    private List<Booking> bookings;
    private Date checkIn;
    private Date checkOut;
    private List<Date> multi;
    private List<Date> range;
    private List<Date> invalidDates;
    private List<Integer> invalidDays;
    private Date minDate;
    private Date today;
    private Date maxDate;
    private Date minDateTime;
    private Date maxDateTime;
    private boolean quietRoom;

    @ManagedProperty(value = "#{bookingService}")
    private BookingService bookingService;

    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {

        bookings = bookingService.getAll();
        booking = new Booking();

        invalidDates = new ArrayList<>();
        today = new Date();
        invalidDates.add(today);
        long oneDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < 5; i++) {
            invalidDates.add(new Date(invalidDates.get(i).getTime() + oneDay));
        }
        invalidDays = new ArrayList<>();
        invalidDays.add(0); /* the first day of week is disabled */
        invalidDays.add(3);

        minDate =new Date(today.getTime() + ( oneDay));
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

        public void changeStatus(){
            bookingService.updateBookingStatus(booking);
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

    public List<Date> getMulti() {
        return multi;
    }

    public void setMulti(List<Date> multi) {
        this.multi = multi;
    }

    public List<Date> getRange() {
        return range;
    }

    public void setRange(List<Date> range) {
        this.range = range;
    }

    public List<Date> getInvalidDates() {
        return invalidDates;
    }

    public void setInvalidDates(List<Date> invalidDates) {
        this.invalidDates = invalidDates;
    }

    public List<Integer> getInvalidDays() {
        return invalidDays;
    }

    public void setInvalidDays(List<Integer> invalidDays) {
        this.invalidDays = invalidDays;
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

    public Date getMinDateTime() {
        return minDateTime;
    }

    public void setMinDateTime(Date minDateTime) {
        this.minDateTime = minDateTime;
    }

    public Date getMaxDateTime() {
        return maxDateTime;
    }

    public boolean isQuietRoom() {
        return quietRoom;
    }

    public void setQuietRoom(boolean quietRoom) {
        this.quietRoom = quietRoom;
    }

    public void setMaxDateTime(Date maxDateTime) {
        this.maxDateTime = maxDateTime;
    }
}
