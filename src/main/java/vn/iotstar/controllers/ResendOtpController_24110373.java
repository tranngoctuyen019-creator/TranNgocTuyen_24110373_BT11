package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.PendingRegistration_24110373;
import vn.iotstar.service.UserService_24110373;
import vn.iotstar.service.impl.UserServiceImpl_24110373;

@WebServlet(urlPatterns = { "/resend-otp" })
public class ResendOtpController_24110373 extends HttpServlet {

    private final UserService_24110373 userService_24110373 = new UserServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        PendingRegistration_24110373 pending = (session != null) ? (PendingRegistration_24110373) session.getAttribute("pendingRegistration") : null;

        try {
            userService_24110373.resendOtp(pending);
            req.setAttribute("message", "Đã gửi lại mã OTP tới email của bạn.");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
        }

        req.setAttribute("email", pending != null ? pending.getEmail() : "");
        req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
    }
}
