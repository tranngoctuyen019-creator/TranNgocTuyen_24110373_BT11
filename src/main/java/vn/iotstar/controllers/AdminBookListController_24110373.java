package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.Book_24110373;
import vn.iotstar.service.BookService_24110373;
import vn.iotstar.service.impl.BookServiceImpl_24110373;
import vn.iotstar.utils.Constant_24110373;

@WebServlet(urlPatterns = { "/admin/book/list" })
public class AdminBookListController_24110373 extends HttpServlet {

    private final BookService_24110373 bookService_24110373 = new BookServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = 1;
        try {
            String p = req.getParameter("page");
            if (p != null) page = Integer.parseInt(p);
        } catch (NumberFormatException ignored) {
        }
        if (page < 1) page = 1;

        int total = bookService_24110373.countAll();
        int pageSize = Constant_24110373.ADMIN_PAGE_SIZE;
        int totalPages = Math.max(1, (int) Math.ceil(total / (double) pageSize));
        if (page > totalPages) page = totalPages;

        List<Book_24110373> book_24110373s = bookService_24110373.getAdminPage(page, pageSize);

        req.setAttribute("books", book_24110373s);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/views/admin/list-book.jsp").forward(req, resp);
    }
}
