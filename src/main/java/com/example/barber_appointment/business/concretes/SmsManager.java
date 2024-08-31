package com.example.barber_appointment.business.concretes;

import com.example.barber_appointment.business.abstracts.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsManager implements SmsService {
    private final String ACCOUNT_SID = "";
    private final String AUTH_TOKEN = "";
    private final String TWILIO_PHONE_NUMBER = "";
    private final String VERIFY_SERVICE_SID = "";

    public SmsManager() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER), message)
                .create();
    }

    @Override
    public void sendVerificationSms(String toPhoneNumber) {
        Verification verification = Verification.creator(
                        VERIFY_SERVICE_SID,
                        toPhoneNumber,
                        "sms")
                .create();

        System.out.println("Verification SID: " + verification.getSid());
    }

    @Override
    public boolean checkVerificationCode(String toPhoneNumber, String code) {
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        VERIFY_SERVICE_SID,
                        code)
                .setTo(toPhoneNumber)
                .create();

        return "approved".equals(verificationCheck.getStatus());
    }
}
