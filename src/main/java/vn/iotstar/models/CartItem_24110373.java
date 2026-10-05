package vn.iotstar.models;

import java.math.BigDecimal;

public class CartItem_24110373 {

    private int id;
    private int userId;
    private int bookid;
    private int quantity;

    private String title;
    private String coverImage;
    private Integer isbn;
    private BigDecimal price;
    private int stock;

    public CartItem_24110373() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookid() { return bookid; }
    public void setBookid(int bookid) { this.bookid = bookid; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public Integer getIsbn() { return isbn; }
    public void setIsbn(Integer isbn) { this.isbn = isbn; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public BigDecimal getSubtotal() {
        if (price == null) return BigDecimal.ZERO;
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public boolean isOverStock() {
        return quantity > stock;
    }
}
