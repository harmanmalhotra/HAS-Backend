package com.example.hostelAvailabilitySystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.twilio.Twilio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Entity;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Configuration
//@Entity
public class SmsRequest {

//    private final String phoneNumber; // destination
//    private final String message;
//    private  String phoneNumber =  "+917013745538"; // destination
//    private String message = "from doctor_app5";

    private String phoneNumber; // destination
    private  String message;

//    public  SmsRequest(){
//        this.phoneNumber =  "+917013745538";
//        this.message = "from doctor_app5";
//    }
//    public SmsRequest(){
//
//    }

//    public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber,
//                      @JsonProperty("message") String message) {
//        System.out.println("SmsRequest");
//        this.phoneNumber = phoneNumber;
//        this.message = message;
//    }

    public SmsRequest(String phoneNumber,String message){
        System.out.println("SmsRequest");
        this.phoneNumber =  phoneNumber;
        this.message = message;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber= ..." + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


}
