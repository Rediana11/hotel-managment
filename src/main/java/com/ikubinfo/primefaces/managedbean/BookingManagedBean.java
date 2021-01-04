package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.service.BookingService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.UserService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.service.helpers.SelectRoom;
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
    private List<SelectRoom> selectedRooms;
    private SelectRoom selectedRoom;
    private BookingStatus bookingStatus;
    private List<Booking> bookings;
    private List<Booking> activeBookings;
    private List<Booking> canceledBookings;

    private List<BookingStatus> bookingStatuses;
    private List<Room> rooms;

    private List<SelectItem> statusItems;
    private Date checkIn;
    private Date checkOut;
    private Date minDate;
    private Date today;
    private Date maxDate;



    @ManagedProperty(value = "#{bookingService}")
    private BookingService bookingService;


    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        // TODO bej kontrollin per rolin per personal information
        // beji fushat readonly if client otherwise joreadonlu fushat+ nqs nuk ekziston klient me ate email
        // shtoje ne db si Client ne shto tabele Client 1 me shume
        booking = new Booking();
        bookingStatus = new BookingStatus();
        bookings = bookingService.getReservedBookings();
        activeBookings= bookingService.getActiveBookings();
        canceledBookings=bookingService.getCanceledBookings();
        rooms=new ArrayList<Room>();
        today = new Date();
        long oneDay = 24 * 60 * 60 * 1000;
        minDate =new Date(today.getTime() + ( oneDay));
        selectedRooms = new ArrayList<SelectRoom>();
        selectedRoom=new SelectRoom();
        addStatusItems();
    }

    private void addStatusItems(){
        statusItems = new ArrayList<SelectItem>();
        bookingStatuses = bookingService.getBookingStatuses();
        for (BookingStatus status: bookingStatuses){
            statusItems.add(new SelectItem(status.getId(),status.getName()));
        }
    }


    public void delete() {
            bookingService.delete(booking);
            bookings = bookingService.getReservedBookings();
            messages.showInfoMessage("Deleted");

    }

    public void selectRoom(Room room){
        selectedRoom.setRoom(room);
        SelectRoom tempRoom=new SelectRoom();
        tempRoom.setRoom(room);
        if(selectedRoom.isSelected()) {
            tempRoom.setSelected(selectedRoom.isSelected());
            selectedRooms.add(tempRoom);
        }else{
            tempRoom.setSelected(!selectedRoom.isSelected());
              selectedRooms.remove(tempRoom);
       }
    }

    public Booking loadBooking(){
        return booking=bookingService.getBooking(booking.getId());
    }


    private List<Room>  mappSelectRoomToRoom(List<SelectRoom> selRooms){
        List<Room> rooms=new ArrayList<Room>();
        for (SelectRoom el: selRooms){
            rooms.add(el.getRoom());
        }
        return rooms;
    }
    public void reserve (){
        if(bookingService.reserve(booking,mappSelectRoomToRoom(selectedRooms))){
            messages.showInfoMessage("Successful reservation!");
        }
    }

    public void loadBookingStatus(){
        booking=bookingService.getBooking(booking.getId());
    }

    public void changeStatusToCheckedIn(){

            if (bookingService.updateBookingStatusToCheckedIn(booking))
            messages.showInfoMessage("Booking status changed successfully");
        }
    public void changeStatusToCheckedOut(){

        if (bookingService.updateBookingStatusToCheckedOut(booking))
            messages.showInfoMessage("Booking status changed successfully");
    }

    public void getAll() {
        bookings = bookingService.getReservedBookings();
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

    public List<SelectItem> getStatusItems() {
        return statusItems;
    }

    public void setStatusItems(List<SelectItem> statusItems) {
        this.statusItems = statusItems;
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
}
