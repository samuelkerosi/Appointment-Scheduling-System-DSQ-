package com.example.samue.desq;

public class UserData {
    String id;
    String name;
    String phoneNumber;
    String notificationToken;

    public UserData(String id, String name, String phoneNumber, String notificationToken) {
        this.id = id;
        this.name = name;
        this.phoneNumber= phoneNumber;
        this.notificationToken = notificationToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getNotificationToken() { return notificationToken; }

    public void setNotificationToken(String notificationToken) { this.notificationToken = notificationToken; }
}
