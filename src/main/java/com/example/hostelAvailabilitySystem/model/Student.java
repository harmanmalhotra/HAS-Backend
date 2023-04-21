package com.example.hostelAvailabilitySystem.model;

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
@Table(name = "Student", uniqueConstraints = {@UniqueConstraint(columnNames = {"email","studentRollNo","studentPhoneNo"})})
public class Student {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;

    @NotNull
    @Column(length=20)
    private String studentName;

    @NotNull
    @Column(length=20)
    private String email;

    @NotNull
    @Column(length=15)
    private String studentRollNo;

    @NotNull
    private long studentPhoneNo;

    @NotNull
    @Column(length=6)
    private String studentGender;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "course_id", referencedColumnName = "courseID")
    private Course course;

    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public Student(String studentName, String email, String studentRollNo, long studentPhoneNo, String studentGender, Course course) {
        this.studentName = studentName;
        this.email = email;
        this.studentRollNo = studentRollNo;
        this.studentPhoneNo = studentPhoneNo;
        this.studentGender = studentGender;
        this.course = course;
    }

}
