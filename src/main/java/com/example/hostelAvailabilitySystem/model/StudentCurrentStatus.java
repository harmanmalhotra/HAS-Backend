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
public class StudentCurrentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long currentStatusId;


    @NotNull
    @OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "student_id", referencedColumnName = "studentId")
    private Student student;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "hostel_id", referencedColumnName = "hostelId")
    private Hostel hostel;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    @NotFound(action= NotFoundAction.IGNORE)
    private Room room;

    @NotNull
    private String payment_status;

    @NotNull
    private String vacate_status;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public StudentCurrentStatus(Student student, Hostel hostel, Room room, String payment_status, String vacate_status) {
        this.student = student;
        this.hostel = hostel;
        this.room = room;
        this.payment_status = payment_status;
        this.vacate_status = vacate_status;
    }
}
