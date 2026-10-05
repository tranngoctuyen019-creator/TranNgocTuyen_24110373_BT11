package vn.iotstar.utils;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailUtil_24110373 {

    private static Session buildSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Constant_24110373.MAIL_HOST);
        props.put("mail.smtp.port", String.valueOf(Constant_24110373.MAIL_PORT));

        return Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constant_24110373.MAIL_USERNAME, Constant_24110373.MAIL_PASSWORD);
            }
        });
    }

    public static void sendMail(String toEmail, String subject, String htmlContent) throws MessagingException {
        Session session = buildSession();
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(Constant_24110373.MAIL_USERNAME, Constant_24110373.MAIL_FROM_NAME));
        } catch (java.io.UnsupportedEncodingException e) {
            throw new MessagingException("Khong the dat ten nguoi gui", e);
        }

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject, "UTF-8");
        message.setContent(htmlContent, "text/html; charset=UTF-8");

        Transport.send(message);
    }

    public static void sendOtpMail(String toEmail, String otp, String purpose) throws MessagingException {
        String subject = "Ma OTP xac thuc - " + purpose;
        String content =
                "<div style='font-family:sans-serif;'>"
                        + "<h2>Xac thuc OTP</h2>"
                        + "<p>Ma OTP cua ban cho yeu cau <b>" + purpose + "</b> la:</p>"
                        + "<h1 style='letter-spacing:6px;'>" + otp + "</h1>"
                        + "<p>Ma co hieu luc trong " + Constant_24110373.OTP_EXPIRE_MINUTES + " phut. "
                        + "Vui long khong chia se ma nay cho bat ky ai.</p>"
                        + "</div>";
        sendMail(toEmail, subject, content);
    }
}
