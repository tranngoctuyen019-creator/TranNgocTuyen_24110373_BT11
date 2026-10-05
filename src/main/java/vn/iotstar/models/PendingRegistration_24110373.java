package vn.iotstar.models;

import java.time.LocalDateTime;

public class PendingRegistration_24110373 {

    private String email;
    private String fullname;
    private Integer phone;
    private String hashedPasswd;
    private String otp;
    private LocalDateTime otpExpiry;
    private boolean admin;

    public PendingRegistration_24110373() {
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public Integer getPhone() { return phone; }
    public void setPhone(Integer phone) { this.phone = phone; }

    public String getHashedPasswd() { return hashedPasswd; }
    public void setHashedPasswd(String hashedPasswd) { this.hashedPasswd = hashedPasswd; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public LocalDateTime getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(LocalDateTime otpExpiry) { this.otpExpiry = otpExpiry; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}
