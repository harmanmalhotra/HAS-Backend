package com.example.hostelAvailabilitySystem.controller;

import com.example.hostelAvailabilitySystem.model.SmsSender;
import com.example.hostelAvailabilitySystem.service.serviceImpl.NotificationService;
import com.example.hostelAvailabilitySystem.model.SmsRequest;
import com.example.hostelAvailabilitySystem.service.serviceImpl.NotificationService;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import javax.validation.Valid;
import static com.twilio.example.ValidationExample.ACCOUNT_SID;
import static com.twilio.example.ValidationExample.AUTH_TOKEN;


@RestController
//@RequestMapping(value = "api/v1/sms", method = RequestMethod.PUT)
@RequestMapping("api/v1/sms")
//@GetMapping("api/v1/sms")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SmsRequest smsRequest;
    @Autowired
    private SmsSender smsSender;

    public NotificationController(NotificationService notificationService) {
        System.out.println("NotificationController");
        this.notificationService= notificationService;
    }

    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        notificationService.sendSms(smsRequest);
    }

    public void sendSms1(@Valid @RequestBody SmsRequest smsRequest) {
        notificationService.sendSms(smsRequest);
    }

//    public void sendSms() {
//        service.sendSms();
//    }

}


