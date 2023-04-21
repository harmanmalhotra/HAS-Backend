package com.example.hostelAvailabilitySystem.model;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration

public class TwilioInitializer {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
    private  TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioInitializer(){
        System.out.println("TwilioInitializer");
    }
    public TwilioInitializer(TwilioConfiguration twilioConfiguration) {

        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken()
        );
//        Twilio.init("ACc2ce336c2a9272395eb05f7c86b47280","321dfe6ae9707d4862ed70217ae4d983");

        LOGGER.info("Twilio initialized ... with account sid {} ", twilioConfiguration.getAccountSid());
    }
}
