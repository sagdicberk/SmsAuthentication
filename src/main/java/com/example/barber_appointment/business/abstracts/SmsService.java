package com.example.barber_appointment.business.abstracts;

public interface SmsService {
    void sendSms(String toPhoneNumber, String message);

    void sendVerificationSms(String toPhoneNumber);

    boolean checkVerificationCode(String toPhoneNumber, String code);
}
