package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.User_24110373;
import vn.iotstar.service.RatingService_24110373;
import vn.iotstar.service.impl.RatingServiceImpl_24110373;

@WebServlet(urlPatterns = { "/review/add" })
public class AddReviewController_24110373 extends HttpServlet {

    private final RatingService_24110373 ratingService_24110373 = new RatingServiceImpl_24110373();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User_24110373 user_24110373 = (session != null) ? (User_24110373) session.getAttribute("user") : null;
        if (user_24110373 == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int bookid = Integer.parseInt(req.getParameter("bookid"));
        int rating = 5;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch (Exception ignored) {
        }
        String reviewText = req.getParameter("reviewText");

        ratingService_24110373.addOrUpdateReview(user_24110373.getId(), bookid, rating, reviewText);

        resp.sendRedirect(req.getContextPath() + "/book-detail?id=" + bookid);
    }
}
