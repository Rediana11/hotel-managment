package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.service.BookingService;
import com.ikubinfo.primefaces.service.EmailService;
import com.ikubinfo.primefaces.service.RoomService;
import com.ikubinfo.primefaces.service.UserService;
import com.ikubinfo.primefaces.service.exceptions.CategoryInUseException;
import com.ikubinfo.primefaces.service.helpers.SelectRoom;
import com.ikubinfo.primefaces.util.Messages;
import org.primefaces.event.FlowEvent;
import sun.security.validator.ValidatorException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class BookingManagedBean implements Serializable {

    private Booking booking;
    private Client client;
    private String email;
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

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{bookingService}")
    private BookingService bookingService;


    @ManagedProperty(value="#{emailService}")
    private EmailService emailService;


    @ManagedProperty(value = "#{messages}")
    private Messages messages;

    @PostConstruct
    public void init() {
        booking = new Booking();
        client = new Client();
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
        bookingPrice();


    }

    public void bookingPrice(){
        System.out.println("Cmimi 0");
        double roomPrice = 0;
        for (SelectRoom selRoom: selectedRooms) {

            roomPrice= roomPrice+selRoom.getRoom().getPrice();
        }
        booking.setPrice(roomPrice);
        System.out.println("Cmimi final");

    }

   public boolean datesValidate(){
        if (booking.getCheckOut().getTime() <= booking.getCheckIn().getTime()){
            messages.showErrorMessage("Input dates are not valid! Try again!");
            return false;
        }
        return true;
   }

   public boolean checkedRoomsValidate(){
        if(selectedRooms.isEmpty()|| selectedRoom.getRoom().equals(null)){
            messages.showErrorMessage("You must check a room to complete the reservation!");
            return false;
        }
        return true;
   }

    private void addStatusItems(){
        statusItems = new ArrayList<SelectItem>();
        bookingStatuses = bookingService.getBookingStatuses();
        for (BookingStatus status: bookingStatuses){
            statusItems.add(new SelectItem(status.getId(),status.getName()));
        }
    }

    public void getClientByEmail(){
        System.out.println(email);
        for(Client client1: userService.getClients()){
            if (email.equals(client1.getEmail()))
            {
                messages.showInfoMessage("Client found!");
                client= userService.getClientByEmail(email);

            }
        }
        messages.showWarningMessage("Client not found! Create a new client");
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
    public String reserve (){
        if(bookingService.reserve(booking,mappSelectRoomToRoom(selectedRooms))&&checkedRoomsValidate()&&datesValidate()){
            emailService.sendSimpleMessage(client.getEmail(),"Reservation Confirmation","Hello " + client.getFirstName() + client.getLastName());
            insertClient();
            messages.showInfoMessage("Successful reservation!");
            return "booking";
        }

        return null;
    }

    public void insertClient(){
        userService.insertClient(client);
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
}
