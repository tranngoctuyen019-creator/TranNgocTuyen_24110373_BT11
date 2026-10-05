package vn.iotstar.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.config.DBConnection_24110373;
import vn.iotstar.dao.BookDAO_24110373;
import vn.iotstar.models.Author_24110373;
import vn.iotstar.models.Book_24110373;

public class BookDAOImpl_24110373 implements BookDAO_24110373 {

    private Book_24110373 mapRow(ResultSet rs) throws SQLException {
        Book_24110373 b = new Book_24110373();
        b.setBookid(rs.getInt("bookid"));
        int isbn = rs.getInt("isbn");
        b.setIsbn(rs.wasNull() ? null : isbn);
        b.setTitle(rs.getString("title"));
        b.setPublisher(rs.getString("publisher"));
        BigDecimal price = rs.getBigDecimal("price");
        b.setPrice(price);
        b.setDescription(rs.getString("description"));
        java.sql.Date pd = rs.getDate("publish_date");
        if (pd != null) b.setPublishDate(pd.toLocalDate());
        b.setCoverImage(rs.getString("cover_image"));
        int qty = rs.getInt("quantity");
        b.setQuantity(rs.wasNull() ? null : qty);
        return b;
    }

    private void loadAuthorsAndRating(Connection conn, Book_24110373 b) throws SQLException {
        String authorSql = "SELECT a.author_id, a.author_name, a.date_of_birth " +
                "FROM dbo.author a JOIN dbo.book_author ba ON a.author_id = ba.author_id " +
                "WHERE ba.bookid = ? ORDER BY a.author_id";
        try (PreparedStatement ps = conn.prepareStatement(authorSql)) {
            ps.setInt(1, b.getBookid());
            try (ResultSet rs = ps.executeQuery()) {
                List<Author_24110373> author_24110373s = new ArrayList<>();
                while (rs.next()) {
                    Author_24110373 a = new Author_24110373();
                    a.setAuthorId(rs.getInt("author_id"));
                    a.setAuthorName(rs.getString("author_name"));
                    java.sql.Date dob = rs.getDate("date_of_birth");
                    if (dob != null) a.setDateOfBirth(dob.toLocalDate());
                    author_24110373s.add(a);
                }
                b.setAuthors(author_24110373s);
            }
        }

        String ratingSql = "SELECT AVG(CAST(rating AS FLOAT)) AS avgRating, COUNT(*) AS cnt " +
                "FROM dbo.rating WHERE bookid = ?";
        try (PreparedStatement ps = conn.prepareStatement(ratingSql)) {
            ps.setInt(1, b.getBookid());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    b.setAvgRating(rs.getDouble("avgRating"));
                    b.setReviewCount(rs.getInt("cnt"));
                }
            }
        }
    }

    @Override
    public List<Book_24110373> findByAuthorId(int authorId) {
        List<Book_24110373> list = new ArrayList<>();
        String sql = "SELECT b.* FROM dbo.books b " +
                "JOIN dbo.book_author ba ON b.bookid = ba.bookid " +
                "WHERE ba.author_id = ? ORDER BY b.bookid";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book_24110373 b = mapRow(rs);
                    loadAuthorsAndRating(conn, b);
                    list.add(b);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Book_24110373 findById(int bookid) {
        String sql = "SELECT * FROM dbo.books WHERE bookid = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book_24110373 b = mapRow(rs);
                    loadAuthorsAndRating(conn, b);
                    return b;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Book_24110373> findPage(int offset, int limit) {
        List<Book_24110373> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.books ORDER BY bookid " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book_24110373 b = mapRow(rs);
                    loadAuthorsAndRating(conn, b);
                    list.add(b);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM dbo.books";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int insert(Book_24110373 book_24110373) {
        String sql = "INSERT INTO dbo.books (isbn, title, publisher, price, description, publish_date, cover_image, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindBook(ps, book_24110373);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    @Override
    public void update(Book_24110373 book_24110373) {
        String sql = "UPDATE dbo.books SET isbn = ?, title = ?, publisher = ?, price = ?, description = ?, " +
                "publish_date = ?, cover_image = ?, quantity = ? WHERE bookid = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            bindBook(ps, book_24110373);
            ps.setInt(9, book_24110373.getBookid());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void bindBook(PreparedStatement ps, Book_24110373 book_24110373) throws SQLException {
        if (book_24110373.getIsbn() != null) ps.setInt(1, book_24110373.getIsbn()); else ps.setNull(1, java.sql.Types.INTEGER);
        ps.setString(2, book_24110373.getTitle());
        ps.setString(3, book_24110373.getPublisher());
        if (book_24110373.getPrice() != null) ps.setBigDecimal(4, book_24110373.getPrice()); else ps.setNull(4, java.sql.Types.DECIMAL);
        ps.setString(5, book_24110373.getDescription());
        if (book_24110373.getPublishDate() != null) ps.setDate(6, java.sql.Date.valueOf(book_24110373.getPublishDate()));
        else ps.setNull(6, java.sql.Types.DATE);
        ps.setString(7, book_24110373.getCoverImage());
        if (book_24110373.getQuantity() != null) ps.setInt(8, book_24110373.getQuantity()); else ps.setNull(8, java.sql.Types.INTEGER);
    }

    @Override
    public void delete(int bookid) {
        String sql = "DELETE FROM dbo.books WHERE bookid = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookid);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setAuthorsForBook(int bookid, List<Integer> authorIds) {
        String deleteSql = "DELETE FROM dbo.book_author WHERE bookid = ?";
        String insertSql = "INSERT INTO dbo.book_author (bookid, author_id) VALUES (?, ?)";
        try (Connection conn = new DBConnection_24110373().getDBConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                del.setInt(1, bookid);
                del.executeUpdate();
            }
            if (authorIds != null) {
                try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                    for (Integer authorId : authorIds) {
                        ins.setInt(1, bookid);
                        ins.setInt(2, authorId);
                        ins.addBatch();
                    }
                    ins.executeBatch();
                }
            }
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}