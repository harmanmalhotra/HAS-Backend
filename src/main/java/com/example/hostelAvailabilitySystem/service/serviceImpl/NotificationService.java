package com.example.hostelAvailabilitySystem.service.serviceImpl;

//import com.example.hostelAvailabilitySystem.model.SmsRequest;
import com.example.hostelAvailabilitySystem.model.SmsRequest;
import com.example.hostelAvailabilitySystem.model.SmsSender;
import com.example.hostelAvailabilitySystem.model.TwilioSmsSender;
import com.twilio.twiml.voice.Sms;


import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.hostelAvailabilitySystem.model.TwilioConfiguration;

@Service
@Component
public class NotificationService {

//    @Value("${accountSid}")
//    private String accountSid;
//    @Value("${authToken}")
//    private String authToken;
//    @Value("${trialNumber}")
//    private String trialNumber;

    @Autowired
    private TwilioConfiguration twilioConfiguration;

    @Autowired
    private SmsSender smsSender;

//    @Autowired
//    private SmsRequest smsRequest;

    @Autowired
    public NotificationService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        System.out.println("NotificationService");

        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        Twilio.init(twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken());
        smsSender.sendSms(smsRequest);
    }

//    public void sendSms() {
//        Twilio.init(accountSid,authToken);
//        smsSender.sendSms();
//    }


}
