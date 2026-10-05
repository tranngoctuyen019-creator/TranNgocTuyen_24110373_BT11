package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.Book_24110373;
import vn.iotstar.models.Rating_24110373;
import vn.iotstar.service.BookService_24110373;
import vn.iotstar.service.RatingService_24110373;
import vn.iotstar.service.impl.BookServiceImpl_24110373;
import vn.iotstar.service.impl.RatingServiceImpl_24110373;

@WebServlet(urlPatterns = { "/book-detail" })
public class BookDetailController_24110373 extends HttpServlet {

    private final BookService_24110373 bookService_24110373 = new BookServiceImpl_24110373();
    private final RatingService_24110373 ratingService_24110373 = new RatingServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int bookid;
        try {
            bookid = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Book_24110373 book_24110373 = bookService_24110373.getBookDetail(bookid);
        if (book_24110373 == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        List<Rating_24110373> reviews = ratingService_24110373.getReviews(bookid);

        req.setAttribute("book", book_24110373);
        req.setAttribute("reviews", reviews);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/book-detail.jsp");
        dispatcher.forward(req, resp);
    }
}
