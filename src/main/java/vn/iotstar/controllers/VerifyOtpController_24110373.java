package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.PendingRegistration_24110373;
import vn.iotstar.models.User_24110373;
import vn.iotstar.service.UserService_24110373;
import vn.iotstar.service.impl.UserServiceImpl_24110373;

@WebServlet(urlPatterns = { "/verify-otp" })
public class VerifyOtpController_24110373 extends HttpServlet {

    private final UserService_24110373 userService_24110373 = new UserServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        PendingRegistration_24110373 pending = (session != null) ? (PendingRegistration_24110373) session.getAttribute("pendingRegistration") : null;

        if (pending == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        req.setAttribute("email", pending.getEmail());
        req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        PendingRegistration_24110373 pending = (session != null) ? (PendingRegistration_24110373) session.getAttribute("pendingRegistration") : null;

        String otpInput = req.getParameter("otp");

        try {
            User_24110373 user_24110373 = userService_24110373.verifyOtpAndCreateUser(pending, otpInput);
            session.removeAttribute("pendingRegistration");
            session.setAttribute("user", user_24110373);
            resp.sendRedirect(req.getContextPath() + "/home");

        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", pending != null ? pending.getEmail() : "");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/auth/verify-otp.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
