package vn.iotstar.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.models.Book_24110373;
import vn.iotstar.service.AuthorService_24110373;
import vn.iotstar.service.BookService_24110373;
import vn.iotstar.service.impl.AuthorServiceImpl_24110373;
import vn.iotstar.service.impl.BookServiceImpl_24110373;
import vn.iotstar.utils.ImageUploadUtil_24110373;

@WebServlet(urlPatterns = { "/admin/book/add" })
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class AdminBookAddController_24110373 extends HttpServlet {

    private final BookService_24110373 bookService_24110373 = new BookServiceImpl_24110373();
    private final AuthorService_24110373 authorService_24110373 = new AuthorServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("authors", authorService_24110373.getAll());
        req.getRequestDispatcher("/views/admin/add-book.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            Book_24110373 book_24110373 = new Book_24110373();
            String isbn = req.getParameter("isbn");
            if (isbn != null && !isbn.isBlank()) book_24110373.setIsbn(Integer.parseInt(isbn.trim()));
            book_24110373.setTitle(req.getParameter("title"));
            book_24110373.setPublisher(req.getParameter("publisher"));
            String price = req.getParameter("price");
            if (price != null && !price.isBlank()) book_24110373.setPrice(new BigDecimal(price.trim()));
            book_24110373.setDescription(req.getParameter("description"));
            String publishDate = req.getParameter("publishDate");
            if (publishDate != null && !publishDate.isBlank()) book_24110373.setPublishDate(LocalDate.parse(publishDate));

            Part filePart = req.getPart("coverImageFile");
            book_24110373.setCoverImage(ImageUploadUtil_24110373.saveCoverImage(filePart, getServletContext()));

            String quantity = req.getParameter("quantity");
            if (quantity != null && !quantity.isBlank()) book_24110373.setQuantity(Integer.parseInt(quantity.trim()));

            String[] authorIdsParam = req.getParameterValues("authorIds");
            List<Integer> authorIds = new ArrayList<>();
            if (authorIdsParam != null) {
                for (String id : authorIdsParam) authorIds.add(Integer.parseInt(id));
            }

            bookService_24110373.createBook(book_24110373, authorIds);
            resp.sendRedirect(req.getContextPath() + "/admin/book/list");

        } catch (Exception e) {
            req.setAttribute("error", "Dữ liệu không hợp lệ: " + e.getMessage());
            req.setAttribute("authors", authorService_24110373.getAll());
            req.getRequestDispatcher("/views/admin/add-book.jsp").forward(req, resp);
        }
    }
}
