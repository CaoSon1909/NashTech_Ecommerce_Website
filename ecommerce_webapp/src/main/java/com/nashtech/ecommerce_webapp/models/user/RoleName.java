package com.nashtech.ecommerce_webapp.models.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nashtech.ecommerce_webapp.models.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//@Entity
//@Table(name = "RoleName")
//@Data
//@NoArgsConstructor


public enum RoleName {

    ADMIN("ADMIN"), USER("USER");


    public String label = "";

    private RoleName(String label) {
        this.label = label;
    }

    private static final Map<String, RoleName> BY_LABEL = new HashMap<>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static Map<String, RoleName> getByLabel() {
        return BY_LABEL;
    }

    static {
        for (RoleName e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    @Override
    public String toString() {
        return "RoleName{" +
                "label='" + label + '\'' +
                '}';
    }
}
