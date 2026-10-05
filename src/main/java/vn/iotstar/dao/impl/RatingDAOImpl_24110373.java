package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.config.DBConnection_24110373;
import vn.iotstar.dao.RatingDAO_24110373;
import vn.iotstar.models.Rating_24110373;

public class RatingDAOImpl_24110373 implements RatingDAO_24110373 {

	private Rating_24110373 mapRow(ResultSet rs) throws SQLException {
        Rating_24110373 r = new Rating_24110373();
        r.setUserid(rs.getInt("userid"));
        r.setBookid(rs.getInt("bookid"));
        int rating = rs.getInt("rating");
        r.setRating(rs.wasNull() ? null : rating);
        r.setReviewText(rs.getString("review_text"));
        try {
            r.setUserFullname(rs.getString("fullname"));
        } catch (SQLException ignored) {
        }
        return r;
    }

    @Override
    public List<Rating_24110373> findByBookId(int bookid) {
        List<Rating_24110373> list = new ArrayList<>();
        String sql = "SELECT r.*, u.fullname FROM dbo.rating r " +
                "JOIN dbo.users u ON r.userid = u.id " +
                "WHERE r.bookid = ? ORDER BY r.userid DESC";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Rating_24110373 findByUserAndBook(int userid, int bookid) {
        String sql = "SELECT * FROM dbo.rating WHERE userid = ? AND bookid = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userid);
            ps.setInt(2, bookid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void upsert(Rating_24110373 rating_24110373) {
        Rating_24110373 existing = findByUserAndBook(rating_24110373.getUserid(), rating_24110373.getBookid());
        String sql;
        if (existing != null) {
            sql = "UPDATE dbo.rating SET rating = ?, review_text = ? WHERE userid = ? AND bookid = ?";
            try (Connection conn = new DBConnection_24110373().getDBConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                if (rating_24110373.getRating() != null) ps.setInt(1, rating_24110373.getRating()); else ps.setNull(1, java.sql.Types.TINYINT);
                ps.setString(2, rating_24110373.getReviewText());
                ps.setInt(3, rating_24110373.getUserid());
                ps.setInt(4, rating_24110373.getBookid());
                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            sql = "INSERT INTO dbo.rating (userid, bookid, rating, review_text) VALUES (?, ?, ?, ?)";
            try (Connection conn = new DBConnection_24110373().getDBConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, rating_24110373.getUserid());
                ps.setInt(2, rating_24110373.getBookid());
                if (rating_24110373.getRating() != null) ps.setInt(3, rating_24110373.getRating()); else ps.setNull(3, java.sql.Types.TINYINT);
                ps.setString(4, rating_24110373.getReviewText());
                ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}