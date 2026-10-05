package vn.iotstar.service;

import vn.iotstar.models.PendingRegistration_24110373;
import vn.iotstar.models.User_24110373;

public interface UserService_24110373 {
    PendingRegistration_24110373 startRegistration(String email, String fullname, String phone, String password, String confirmPassword, boolean isAdmin);

    User_24110373 verifyOtpAndCreateUser(PendingRegistration_24110373 pending, String otpInput);

    void resendOtp(PendingRegistration_24110373 pending);
    User_24110373 login(String email, String password);
}
