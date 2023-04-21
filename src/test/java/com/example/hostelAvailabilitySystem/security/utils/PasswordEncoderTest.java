package com.example.hostelAvailabilitySystem.security.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Calendar;

public class PasswordEncoderTest {

    @Test
    public void encode_password() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("admin"));
        System.out.println(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    }
}
