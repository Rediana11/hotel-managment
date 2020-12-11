package com.ikubinfo.primefaces.model;

public enum RoomAbilityName {

    OCCURED("Occured"),
    VACANT("Vacant");


    private String value;

    RoomAbilityName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
