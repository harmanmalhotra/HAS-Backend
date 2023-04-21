package com.example.hostelAvailabilitySystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "room_type_id",
            referencedColumnName = "roomTypeId"
    )
    private RoomType roomType;

    @NotNull
    private int roomNo;

    @NotNull
    private String roomStatus = "UnBlocked";

    @NotNull
    private int bedsOccupied = 0;

    @NotNull
    @ManyToOne()
    @JoinColumn(
            name = "hostel_id",
            referencedColumnName = "hostelId"
    )
    private Hostel hostel;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());


    public Room(int roomNo, RoomType roomType, Hostel hostel) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.hostel = hostel;
    }
}
