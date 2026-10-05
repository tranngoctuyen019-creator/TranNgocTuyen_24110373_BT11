package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.Book_24110373;

public interface BookDAO_24110373 {
    List<Book_24110373> findByAuthorId(int authorId);
    Book_24110373 findById(int bookid);
    List<Book_24110373> findPage(int offset, int limit);
    int countAll();
    int insert(Book_24110373 book_24110373);
    void update(Book_24110373 book_24110373);
    void delete(int bookid);
    void setAuthorsForBook(int bookid, List<Integer> authorIds);
}
