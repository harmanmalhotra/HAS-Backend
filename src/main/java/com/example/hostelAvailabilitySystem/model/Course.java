package com.example.hostelAvailabilitySystem.model;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseID;
    @NotNull
    @Column(length=20)
    private String courseName;

    @NotNull
    private int courseDuration;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public Course(String courseName, int courseDuration) {
        this.courseName = courseName;
        this.courseDuration = courseDuration;
    }
}
