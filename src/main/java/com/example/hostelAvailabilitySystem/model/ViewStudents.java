package com.example.hostelAvailabilitySystem.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@Setter
public class ViewStudents {

    private String email;
    private String student_gender;
    private String student_name;
    private long student_phone_no;
    private String student_roll_no;
    private String course_name;
    private int course_duration;
    private Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());
    private Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

    public ViewStudents(String email, String student_gender, String student_name, long student_phone_no, String student_roll_no, String course_name, int course_duration) {
        this.email = email;
        this.student_gender = student_gender;
        this.student_name = student_name;
        this.student_phone_no = student_phone_no;
        this.student_roll_no = student_roll_no;
        this.course_name = course_name;
        this.course_duration = course_duration;
    }
}
