package com.example.samue.desq;

//model class
public class userInfo {
    String name;
    String date;
    String id;
    String time;
    String title;


    //empty construtor
    public userInfo() {
        //
    }

    public userInfo(String name, String date, String id, String title) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.title= title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

