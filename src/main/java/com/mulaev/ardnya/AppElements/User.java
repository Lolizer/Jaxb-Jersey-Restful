package com.mulaev.ardnya.AppElements;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    private int id = -1;
    private String firstName;
    private String surName;
    private String birthday;
    private String address;

    public User() {}

    public User(String firstName, String surName, String birthday, String address) {
        this.firstName = firstName;
        this.surName = surName;
        this.birthday = birthday;
        this.address = address;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getSurName() {return surName;}
    public void setSurName(String surName) {this.surName = surName;}
    public String getBirthday() {return birthday;}
    public void setBirthday(String birthday) {this.birthday = birthday;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    @Override
    public String toString() {
        return "Item [FirstName=" + firstName + ", SurName=" + surName + ", Birthday="
                + birthday + ", Address=" + address + "]";
    }
}
