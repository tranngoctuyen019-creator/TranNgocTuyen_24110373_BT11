package vn.iotstar.models;

import java.time.LocalDateTime;

public class User_24110373 {

    private int id;
    private String email;
    private String fullname;
    private Integer phone;
    private String passwd;
    private LocalDateTime signupDate;
    private LocalDateTime lastLogin;
    private boolean admin;

    public User_24110373() {
    }

    public User_24110373(int id, String email, String fullname, Integer phone, String passwd,
                LocalDateTime signupDate, LocalDateTime lastLogin, boolean admin) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.passwd = passwd;
        this.signupDate = signupDate;
        this.lastLogin = lastLogin;
        this.admin = admin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public Integer getPhone() { return phone; }
    public void setPhone(Integer phone) { this.phone = phone; }

    public String getPasswd() { return passwd; }
    public void setPasswd(String passwd) { this.passwd = passwd; }

    public LocalDateTime getSignupDate() { return signupDate; }
    public void setSignupDate(LocalDateTime signupDate) { this.signupDate = signupDate; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}
