package vn.iotstar.service;

import java.util.List;

import vn.iotstar.models.AuthorSection_24110373;
import vn.iotstar.models.Book_24110373;

public interface BookService_24110373 {

    AuthorSection_24110373 buildAuthorSection(int authorId, int page);

    Book_24110373 getBookDetail(int bookid);

    List<Book_24110373> getAdminPage(int page, int pageSize);

    int countAll();

    int createBook(Book_24110373 book_24110373, List<Integer> authorIds);

    void updateBook(Book_24110373 book_24110373, List<Integer> authorIds);

    void deleteBook(int bookid);
}
