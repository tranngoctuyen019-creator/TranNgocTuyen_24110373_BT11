	package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.BookService_24110373;
import vn.iotstar.service.impl.BookServiceImpl_24110373;

@WebServlet(urlPatterns = { "/admin/book/delete" })
public class AdminBookDeleteController_24110373 extends HttpServlet {

    private final BookService_24110373 bookService_24110373 = new BookServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int bookid = Integer.parseInt(req.getParameter("id"));
            bookService_24110373.deleteBook(bookid);
        } catch (Exception ignored) {
        }
        resp.sendRedirect(req.getContextPath() + "/admin/book/list");
    }
}
