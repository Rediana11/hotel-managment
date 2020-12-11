package com.ikubinfo.primefaces.model;

public enum BookingStatusName {

    RESERVED("Reserved"),
    CHECKED_IN("Checked in"),
    CANCELED("Canceled"),
    DONE("Done");

    private String value;

    BookingStatusName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
