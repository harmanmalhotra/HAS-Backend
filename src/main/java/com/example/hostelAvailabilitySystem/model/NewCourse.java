package com.example.hostelAvailabilitySystem.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@Setter
public class NewCourse {
    private String courseName;
    private String newCourseName;
    private int duration;
    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());
    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());


    public NewCourse(String courseName, String newCourseName, int duration) {
        this.courseName = courseName;
        this.newCourseName = newCourseName;
        this.duration = duration;
    }



}
