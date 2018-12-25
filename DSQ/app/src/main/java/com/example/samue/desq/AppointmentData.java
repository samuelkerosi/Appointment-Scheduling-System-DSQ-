package com.example.samue.desq;

public class AppointmentData {
    String id;
    String title;
    String description;
    String date;
    String startTime;
    String visitorId;

    public AppointmentData(String id, String title, String description, String date, String startTime, String visitorId) {
        this.id = id;
        this.title= title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.visitorId = visitorId;
    }
    public AppointmentData (){

    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getVisitorId() { return visitorId; }

    public void setVisitorId(String visitorId) { this.visitorId = visitorId; }
}
