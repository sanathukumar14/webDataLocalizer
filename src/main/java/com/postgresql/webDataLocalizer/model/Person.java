package com.postgresql.webDataLocalizer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@Entity
@Table(name = "person")
public class Person {
    private static final Logger LOGGER = LoggerFactory.getLogger(Person.class);

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String address;

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        LOGGER.debug("Setting age to {}", age);
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        LOGGER.debug("Setting address to {}", address);
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id != null) {
            LOGGER.debug("Setting ID to {}", id);
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Setting name to {}", name);
        this.name = name;
    }
}