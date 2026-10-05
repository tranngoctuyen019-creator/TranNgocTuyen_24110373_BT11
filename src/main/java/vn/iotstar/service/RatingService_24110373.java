package vn.iotstar.service;

import java.util.List;

import vn.iotstar.models.Rating_24110373;

public interface RatingService_24110373 {
    List<Rating_24110373> getReviews(int bookid);
    void addOrUpdateReview(int userid, int bookid, int ratingValue, String reviewText);
}
