package vn.iotstar.service.impl;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.dao.AuthorDAO_24110373;
import vn.iotstar.dao.BookDAO_24110373;
import vn.iotstar.dao.impl.AuthorDAOImpl_24110373;
import vn.iotstar.dao.impl.BookDAOImpl_24110373;
import vn.iotstar.models.Author_24110373;
import vn.iotstar.models.AuthorSection_24110373;
import vn.iotstar.models.Book_24110373;
import vn.iotstar.service.BookService_24110373;
import vn.iotstar.utils.Constant_24110373;

public class BookServiceImpl_24110373 implements BookService_24110373 {

    private final BookDAO_24110373 bookDAO_24110373 = new BookDAOImpl_24110373();
    private final AuthorDAO_24110373 authorDAO_24110373 = new AuthorDAOImpl_24110373();

    @Override
    public AuthorSection_24110373 buildAuthorSection(int authorId, int page) {
        Author_24110373 author_24110373 = authorDAO_24110373.findById(authorId);
        String authorName = author_24110373 != null ? author_24110373.getAuthorName() : "";

        List<Book_24110373> allBooks = bookDAO_24110373.findByAuthorId(authorId);
        int totalCount = allBooks.size();
        int totalPages = Math.max(1, (int) Math.ceil(totalCount / (double) Constant_24110373.BOOKS_PER_AUTHOR_PAGE));

        int p = page;
        if (p < 1) p = 1;
        if (p > totalPages) p = totalPages;

        int start = (p - 1) * Constant_24110373.BOOKS_PER_AUTHOR_PAGE;
        int end = Math.min(start + Constant_24110373.BOOKS_PER_AUTHOR_PAGE, totalCount);
        List<Book_24110373> pageBooks = start < end ? new ArrayList<>(allBooks.subList(start, end)) : new ArrayList<>();

        return new AuthorSection_24110373(authorId, authorName, totalCount, p, totalPages, pageBooks);
    }

    @Override
    public Book_24110373 getBookDetail(int bookid) {
        return bookDAO_24110373.findById(bookid);
    }

    @Override
    public List<Book_24110373> getAdminPage(int page, int pageSize) {
        int offset = Math.max(0, (page - 1) * pageSize);
        return bookDAO_24110373.findPage(offset, pageSize);
    }

    @Override
    public int countAll() {
        return bookDAO_24110373.countAll();
    }

    @Override
    public int createBook(Book_24110373 book_24110373, List<Integer> authorIds) {
        int id = bookDAO_24110373.insert(book_24110373);
        if (authorIds != null && !authorIds.isEmpty()) {
            bookDAO_24110373.setAuthorsForBook(id, authorIds);
        }
        return id;
    }

    @Override
    public void updateBook(Book_24110373 book_24110373, List<Integer> authorIds) {
        bookDAO_24110373.update(book_24110373);
        bookDAO_24110373.setAuthorsForBook(book_24110373.getBookid(), authorIds);
    }

    @Override
    public void deleteBook(int bookid) {
        bookDAO_24110373.delete(bookid);
    }
}
