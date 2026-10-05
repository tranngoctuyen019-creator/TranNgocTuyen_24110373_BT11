package vn.iotstar.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book_24110373 {

    private int bookid;
    private Integer isbn;
    private String title;
    private String publisher;
    private BigDecimal price;
    private String description;
    private LocalDate publishDate;
    private String coverImage;
    private Integer quantity;

    private List<Author_24110373> author_24110373s = new ArrayList<>();
    private double avgRating;
    private int reviewCount;

    public Book_24110373() {
    }

    public int getBookid() { return bookid; }
    public void setBookid(int bookid) { this.bookid = bookid; }

    public Integer getIsbn() { return isbn; }
    public void setIsbn(Integer isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public List<Author_24110373> getAuthors() { return author_24110373s; }
    public void setAuthors(List<Author_24110373> author_24110373s) { this.author_24110373s = author_24110373s; }

    public String getAuthorNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < author_24110373s.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(author_24110373s.get(i).getAuthorName());
        }
        return sb.toString();
    }

    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
}
