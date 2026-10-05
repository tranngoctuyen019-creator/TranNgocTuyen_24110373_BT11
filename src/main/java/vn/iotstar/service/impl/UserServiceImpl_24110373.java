package vn.iotstar.service.impl;

import jakarta.mail.MessagingException;
import vn.iotstar.dao.UserDAO_24110373;
import vn.iotstar.dao.impl.UserDAOImpl_24110373;
import vn.iotstar.models.PendingRegistration_24110373;
import vn.iotstar.models.User_24110373;
import vn.iotstar.service.UserService_24110373;
import vn.iotstar.utils.MailUtil_24110373;
import vn.iotstar.utils.OtpUtil_24110373;
import vn.iotstar.utils.PasswordUtil_24110373;

public class UserServiceImpl_24110373 implements UserService_24110373 {

    private final UserDAO_24110373 userDAO_24110373 = new UserDAOImpl_24110373();

    @Override
    public PendingRegistration_24110373 startRegistration(String email, String fullname, String phone,
                                                   String password, String confirmPassword, boolean isAdmin) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Vui lòng nhập email.");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự.");
        if (!password.equals(confirmPassword)) throw new IllegalArgumentException("Mật khẩu xác nhận không khớp.");
        if (userDAO_24110373.existsByEmail(email)) throw new IllegalArgumentException("Email này đã được đăng ký.");

        PendingRegistration_24110373 pending = new PendingRegistration_24110373();
        pending.setEmail(email);
        pending.setFullname(fullname);
        try {
            if (phone != null && !phone.isBlank()) pending.setPhone(Integer.parseInt(phone.trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ.");
        }
        pending.setHashedPasswd(PasswordUtil_24110373.md5(password));
        pending.setAdmin(isAdmin);

        String otp = OtpUtil_24110373.generateOtp();
        pending.setOtp(otp);
        pending.setOtpExpiry(OtpUtil_24110373.newExpiry());

        try {
            MailUtil_24110373.sendOtpMail(email, otp, "Đăng ký tài khoản BookStore");
        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi email OTP. Vui lòng kiểm tra cấu hình mail hoặc thử lại sau.", e);
        }

        return pending;
    }

    @Override
    public User_24110373 verifyOtpAndCreateUser(PendingRegistration_24110373 pending, String otpInput) {
        if (pending == null) throw new IllegalStateException("Phiên đăng ký đã hết hạn, vui lòng đăng ký lại.");
        if (OtpUtil_24110373.isExpired(pending.getOtpExpiry())) throw new IllegalStateException("Mã OTP đã hết hạn, vui lòng gửi lại.");
        if (!pending.getOtp().equals(otpInput)) throw new IllegalStateException("Mã OTP không đúng.");

        User_24110373 user_24110373 = new User_24110373();
        user_24110373.setEmail(pending.getEmail());
        user_24110373.setFullname(pending.getFullname());
        user_24110373.setPhone(pending.getPhone());
        user_24110373.setPasswd(pending.getHashedPasswd());
        user_24110373.setAdmin(pending.isAdmin());

        int id = userDAO_24110373.insert(user_24110373);
        user_24110373.setId(id);
        return user_24110373;
    }

    @Override
    public void resendOtp(PendingRegistration_24110373 pending) {
        if (pending == null) throw new IllegalStateException("Phiên đăng ký đã hết hạn, vui lòng đăng ký lại.");
        String otp = OtpUtil_24110373.generateOtp();
        pending.setOtp(otp);
        pending.setOtpExpiry(OtpUtil_24110373.newExpiry());
        try {
            MailUtil_24110373.sendOtpMail(pending.getEmail(), otp, "Đăng ký tài khoản BookStore");
        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi lại email OTP.", e);
        }
    }

    @Override
    public User_24110373 login(String email, String password) {
        User_24110373 user_24110373 = userDAO_24110373.findByEmail(email);
        if (user_24110373 == null) return null;
        if (!PasswordUtil_24110373.matches(password, user_24110373.getPasswd())) return null;
        userDAO_24110373.updateLastLogin(user_24110373.getId());
        return user_24110373;
    }
}
