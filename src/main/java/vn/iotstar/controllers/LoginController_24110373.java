package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.User_24110373;
import vn.iotstar.service.UserService_24110373;
import vn.iotstar.service.impl.UserServiceImpl_24110373;

@WebServlet(urlPatterns = { "/login" })
public class LoginController_24110373 extends HttpServlet {

    private final UserService_24110373 userService_24110373 = new UserServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String redirect = req.getParameter("redirect");

        User_24110373 user_24110373 = userService_24110373.login(email, password);

        if (user_24110373 != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user_24110373);

            if (user_24110373.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else if (redirect != null && !redirect.isBlank()) {
                resp.sendRedirect(redirect);
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            req.setAttribute("error", "Email hoặc mật khẩu không đúng.");
            req.setAttribute("email", email);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/auth/login.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
