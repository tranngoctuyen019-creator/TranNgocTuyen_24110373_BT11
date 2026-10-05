package vn.iotstar.models;

public class Rating_24110373 {

    private int userid;
    private int bookid;
    private Integer rating;
    private String reviewText;

    private String userFullname;

    public Rating_24110373() {
    }

    public int getUserid() { return userid; }
    public void setUserid(int userid) { this.userid = userid; }

    public int getBookid() { return bookid; }
    public void setBookid(int bookid) { this.bookid = bookid; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public String getUserFullname() { return userFullname; }
    public void setUserFullname(String userFullname) { this.userFullname = userFullname; }
}
