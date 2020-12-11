package com.ikubinfo.primefaces.model;

public enum RoomCategoryName {

    SINGLE("Single room"),
    DOUBLE("Double room"),
    DELUXE_DOUBLE("Deluxe double room"),
    FAMILY("Family room");

    private String value;

    RoomCategoryName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
