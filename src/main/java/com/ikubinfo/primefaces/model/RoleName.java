package com.ikubinfo.primefaces.model;

public enum RoleName {

    ADMIN("Admin"),
    MANAGER("Manager"),
    RECEPSIONIST("Recepsionist"),
    CLIENT("Client");


    private String value;

    RoleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
