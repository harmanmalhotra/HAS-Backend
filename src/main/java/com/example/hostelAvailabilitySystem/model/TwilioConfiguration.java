package com.example.hostelAvailabilitySystem.model;

//import com.example.demo.model.TwilioClass;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
public class TwilioConfiguration {

    private String accountSid="ACfe481c08489c76f3a8d757e004ea8814";

    private String authToken="ab134e076a69ee6ac92e8d0080bff524";
    private String trialNumber="+19707075960";

    public TwilioConfiguration() {
        System.out.println("TwilioConfiguration");
    }


    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(String trialNumber) {
        this.trialNumber = trialNumber;
    }

}
