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
import vn.iotstar.service.UserService_24110373;
import vn.iotstar.service.impl.UserServiceImpl_24110373;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController_24110373 extends HttpServlet {

    private final UserService_24110373 userService_24110373 = new UserServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        boolean isAdmin = "admin".equals(req.getParameter("role"));

        try {
            PendingRegistration_24110373 pending = userService_24110373.startRegistration(email, fullname, phone, password, confirmPassword, isAdmin);

            HttpSession session = req.getSession(true);
            session.setAttribute("pendingRegistration", pending);

            resp.sendRedirect(req.getContextPath() + "/verify-otp");

        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phone);
            req.setAttribute("role", isAdmin ? "admin" : "user");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/auth/register.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
