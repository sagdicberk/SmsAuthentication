package com.example.barber_appointment.util;

public class PhoneNumberUtil {
    // Kullanıcıdan alınan telefon numarasını uluslararası formata dönüştürür.
    public static String formatPhoneNumber(String phoneNumber) {
        // Eğer telefon numarası 10 haneli ise (örneğin 5075701735), başına +90 ekler.
        if (phoneNumber.length() == 10) {
            return "+90" + phoneNumber;
        }
        return phoneNumber; // Eğer numara zaten uluslararası formatta ise, olduğu gibi döner.
    }

}
