package com.omer.user.calendarapp;

public class Event {

    private String event_title;
    private String description;
    private String date;

    public Event(String event_title, String description, String date) {
        this.event_title = event_title;
        this.description = description;
        this.date = date;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
