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
public class WardenRegistrationRequest {
    private String name;
    private String email;
    private String password;
    private long phoneNo;
    private Timestamp dateCreated;
    private Timestamp dateModified;
//    private long hostelId;
}
