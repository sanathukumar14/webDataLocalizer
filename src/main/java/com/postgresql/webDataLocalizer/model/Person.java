package com.postgresql.webDataLocalizer.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
//This annotation marks the class as a JPA entity, indicating that instances of this class will be mapped to rows in a database table.
@Table(name = "person")  //Specifies the name of the database table to which this entity is mapped.
public class Person {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String address;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id != null) {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
