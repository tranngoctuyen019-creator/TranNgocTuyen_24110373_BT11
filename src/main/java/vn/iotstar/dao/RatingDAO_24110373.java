package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.Rating_24110373;

public interface RatingDAO_24110373 {
    List<Rating_24110373> findByBookId(int bookid);
    Rating_24110373 findByUserAndBook(int userid, int bookid);
    void upsert(Rating_24110373 rating_24110373);
}
