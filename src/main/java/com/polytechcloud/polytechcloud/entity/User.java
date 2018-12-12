package com.polytechcloud.polytechcloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;


import javax.persistence.GeneratedValue;
import java.util.Date;

public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    //@JsonIgnore
    String id;

    String firstName;

    String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    Date birthDay;

    Position position;

    public User() {
    }

    public User(String id, String firstName, String lastName, Date birthDay, double latitude, double longiude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.position = new Position(latitude, longiude);
    }

    public User(String firstName, String lastName, Date birthDay, double latitude, double longiude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.position = new Position(latitude, longiude);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
