package vn.iotstar.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.Author_24110373;
import vn.iotstar.models.AuthorSection_24110373;
import vn.iotstar.service.AuthorService_24110373;
import vn.iotstar.service.BookService_24110373;
import vn.iotstar.service.impl.AuthorServiceImpl_24110373;
import vn.iotstar.service.impl.BookServiceImpl_24110373;

@WebServlet(urlPatterns = { "/products" })
public class ProductController_24110373 extends HttpServlet {

    private final BookService_24110373 bookService_24110373 = new BookServiceImpl_24110373();
    private final AuthorService_24110373 authorService_24110373 = new AuthorServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer selectedAuthorId = null;
        String authorIdParam = req.getParameter("authorId");
        if (authorIdParam != null && !authorIdParam.trim().isEmpty()) {
            try {
                selectedAuthorId = Integer.parseInt(authorIdParam.trim());
            } catch (NumberFormatException ignored) {
            }
        }

        List<Author_24110373> author_24110373s = authorService_24110373.getAll();
        List<AuthorSection_24110373> sections = new ArrayList<>();

        if (selectedAuthorId != null) {
            for (Author_24110373 author_24110373 : author_24110373s) {
                if (author_24110373.getAuthorId() == selectedAuthorId) {
                    int page = readPage(req, "page_" + author_24110373.getAuthorId());
                    AuthorSection_24110373 section = bookService_24110373.buildAuthorSection(author_24110373.getAuthorId(), page);
                    if (section.getTotalCount() > 0) sections.add(section);
                    break;
                }
            }
        } else {
            for (Author_24110373 author_24110373 : author_24110373s) {
                int page = readPage(req, "page_" + author_24110373.getAuthorId());
                AuthorSection_24110373 section = bookService_24110373.buildAuthorSection(author_24110373.getAuthorId(), page);
                if (section.getTotalCount() > 0) sections.add(section);
            }
        }

        req.setAttribute("sections", sections);
        req.setAttribute("authors", author_24110373s);
        req.setAttribute("selectedAuthorId", selectedAuthorId);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/products.jsp");
        dispatcher.forward(req, resp);
    }

    private int readPage(HttpServletRequest req, String paramName) {
        int page = 1;
        try {
            String p = req.getParameter(paramName);
            if (p != null) page = Integer.parseInt(p);
        } catch (NumberFormatException ignored) {
        }
        if (page < 1) page = 1;
        return page;
    }
}
