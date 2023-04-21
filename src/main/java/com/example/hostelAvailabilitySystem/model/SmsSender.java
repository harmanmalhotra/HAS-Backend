package com.example.hostelAvailabilitySystem.model;

import com.twilio.Twilio;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

//    void sendSms();

}
