package com.example.hostelAvailabilitySystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "Warden", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "wardenPhoneNo"})})
public class Warden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wardenId;

    @NotNull
    @Column(length = 20)
    private String wardenName;

    @NotNull
    @Column(length = 20)
    private String email;

    @NotNull
    private long wardenPhoneNo;


//    @JsonManagedReference
    @OneToOne()
    @JoinColumn(name = "hostel_id", referencedColumnName = "hostelId")
    private Hostel hostel;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public Warden(String wardenName, String email, long wardenPhoneNo ) {
        this.wardenName = wardenName;
        this.email = email;
        this.wardenPhoneNo = wardenPhoneNo;
//        this.hostel=hostel;
    }
}

