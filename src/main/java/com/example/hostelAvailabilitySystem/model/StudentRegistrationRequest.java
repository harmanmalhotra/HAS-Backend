package com.example.hostelAvailabilitySystem.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StudentRegistrationRequest {

    private String name;
    private String email;
    private String password;
    private String rollNo;
    private long phoneNo;
    private String gender;
    private String courseName;
    private Timestamp dateCreated;
    private Timestamp dateModified;
}
