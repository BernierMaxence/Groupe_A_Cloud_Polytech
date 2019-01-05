package com.polytechcloud.polytechcloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;


import javax.persistence.GeneratedValue;
import java.util.Date;

public class User {

    @Id
    //TODO
    @GeneratedValue(generator = "uuid")
    //@JsonIgnore
    private String id;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthDay;

    private Position position;

    public User() {
        //Default constructor for Spring
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

    public void update(User user) {
        if(user.getFirstName()!=null) {setFirstName(user.getFirstName());}
        if(user.getLastName()!=null) {setLastName(user.getLastName());}
        if(user.getBirthDay()!=null) {setBirthDay(user.getBirthDay());}
        if(user.getPosition()!=null) {setPosition(user.getPosition());}
    }
}
