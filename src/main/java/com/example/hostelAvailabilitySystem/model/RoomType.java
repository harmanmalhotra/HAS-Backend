package com.example.hostelAvailabilitySystem.model;

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
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomTypeId;
    private String roomName;
    private int roomFee;
    private int roomCapacity;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public RoomType(String roomName, int roomFee, int roomCapacity) {
        this.roomName = roomName;
        this.roomFee = roomFee;
        this.roomCapacity = roomCapacity;
    }
}
