package vn.iotstar.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class OtpUtil_24110373 {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOtp() {
        int number = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(number);
    }

    public static LocalDateTime newExpiry() {
        return LocalDateTime.now().plusMinutes(Constant_24110373.OTP_EXPIRE_MINUTES);
    }

    public static boolean isExpired(LocalDateTime expiry) {
        return expiry == null || LocalDateTime.now().isAfter(expiry);
    }
}
