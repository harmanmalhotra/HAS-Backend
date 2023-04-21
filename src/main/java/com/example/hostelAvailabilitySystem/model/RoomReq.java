package com.example.hostelAvailabilitySystem.model;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class RoomReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reqId;

    @NotNull
    @Column(length = 10)
    private String roomStatus;

    @OneToOne()
    @JoinColumn(name = "student_id", referencedColumnName = "studentId")
    private Student student;

    @ManyToOne()
    @JoinColumn(name = "hostel_id", referencedColumnName = "hostelId")
    private Hostel hostel;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "room_type_id", referencedColumnName = "roomTypeId")
    private RoomType roomType;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public RoomReq(long reqId, RoomType roomType, String roomStatus, Student student, Hostel hostel) {
        this.reqId = reqId;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.student = student;
        this.hostel = hostel;
    }
}
