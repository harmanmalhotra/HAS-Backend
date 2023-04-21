package com.example.hostelAvailabilitySystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hostelId;

    @NotNull
    @Column(length=20)
    private String hostelName;

    @NotNull
    @Column(length=10)
    private String hostelType;

    @NotNull
    @Column(length=10)
    private String hostelStatus;

    @NotNull
    private int hostelRooms;

//    @OneToOne()
//    @JoinColumn(name = "warden_id", referencedColumnName = "wardenId")
//    private Warden warden;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public Hostel(String hostelName, String hostelType, String hostelStatus, int hostelRooms) {
        this.hostelName = hostelName;
        this.hostelType = hostelType;
        this.hostelStatus = hostelStatus;
        this.hostelRooms = hostelRooms;
//      this.warden = warden;
    }
}
