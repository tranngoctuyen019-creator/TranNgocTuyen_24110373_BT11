package vn.iotstar.models;

import java.util.List;

public class AuthorSection_24110373 {

    private int authorId;
    private String authorName;
    private int totalCount;
    private int currentPage;
    private int totalPages;
    private List<Book_24110373> book_24110373s;

    public AuthorSection_24110373(int authorId, String authorName, int totalCount,
                          int currentPage, int totalPages, List<Book_24110373> book_24110373s) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.book_24110373s = book_24110373s;
    }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public List<Book_24110373> getBooks() { return book_24110373s; }
    public void setBooks(List<Book_24110373> book_24110373s) { this.book_24110373s = book_24110373s; }
}
