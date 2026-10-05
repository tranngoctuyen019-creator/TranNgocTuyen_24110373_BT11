package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.RatingDAO_24110373;
import vn.iotstar.dao.impl.RatingDAOImpl_24110373;
import vn.iotstar.models.Rating_24110373;
import vn.iotstar.service.RatingService_24110373;

public class RatingServiceImpl_24110373 implements RatingService_24110373 {

    private final RatingDAO_24110373 ratingDAO_24110373 = new RatingDAOImpl_24110373();

    @Override
    public List<Rating_24110373> getReviews(int bookid) {
        return ratingDAO_24110373.findByBookId(bookid);
    }

    @Override
    public void addOrUpdateReview(int userid, int bookid, int ratingValue, String reviewText) {
        Rating_24110373 r = new Rating_24110373();
        r.setUserid(userid);
        r.setBookid(bookid);
        r.setRating(ratingValue);
        r.setReviewText(reviewText);
        ratingDAO_24110373.upsert(r);
    }
}
