package com.ikubinfo.primefaces.service.helpers;

import com.ikubinfo.primefaces.model.Room;

import java.util.Objects;

public class SelectRoom {
    private Room room;
    private boolean selected;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "SelectRoom{" +
                "room=" + room +
                ", isSelected=" + selected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectRoom)) return false;
        SelectRoom that = (SelectRoom) o;
        return isSelected() == that.isSelected() &&
                Objects.equals(getRoom(), that.getRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoom(), isSelected());
    }
}
