package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleName {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private String displayRole;

    RoleName(String displayRole){
        this.displayRole = displayRole;
    }

    @JsonValue
    public String getDisplayRole() {
        return displayRole;
    }

    public void setDisplayRole(String displayRole) {
        this.displayRole = displayRole;
    }


}
