package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.config.DBConnection_24110373;
import vn.iotstar.dao.CartDAO_24110373;
import vn.iotstar.models.CartItem_24110373;

public class CartDAOImpl_24110373 implements CartDAO_24110373 {

    @Override
    public List<CartItem_24110373> findByUser(int userId) {
        List<CartItem_24110373> list = new ArrayList<>();
        String sql = "SELECT ci.id, ci.user_id, ci.book_id, ci.quantity, " +
                "b.title, b.cover_image, b.isbn, b.price, b.quantity AS stock " +
                "FROM dbo.cart_items ci JOIN dbo.books b ON b.bookid = ci.book_id " +
                "WHERE ci.user_id = ? ORDER BY ci.id";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem_24110373 item = new CartItem_24110373();
                    item.setId(rs.getInt("id"));
                    item.setUserId(rs.getInt("user_id"));
                    item.setBookid(rs.getInt("book_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setTitle(rs.getString("title"));
                    item.setCoverImage(rs.getString("cover_image"));
                    int isbn = rs.getInt("isbn");
                    item.setIsbn(rs.wasNull() ? null : isbn);
                    item.setPrice(rs.getBigDecimal("price"));
                    int stock = rs.getInt("stock");
                    item.setStock(rs.wasNull() ? 0 : stock);
                    list.add(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int findQuantity(int userId, int bookid) {
        String sql = "SELECT quantity FROM dbo.cart_items WHERE user_id = ? AND book_id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void insert(int userId, int bookid, int quantity) {
        String sql = "INSERT INTO dbo.cart_items (user_id, book_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookid);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateQuantity(int userId, int bookid, int quantity) {
        String sql = "UPDATE dbo.cart_items SET quantity = ? WHERE user_id = ? AND book_id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, bookid);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int userId, int bookid) {
        String sql = "DELETE FROM dbo.cart_items WHERE user_id = ? AND book_id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookid);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear(int userId) {
        String sql = "DELETE FROM dbo.cart_items WHERE user_id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countItems(int userId) {
        String sql = "SELECT ISNULL(SUM(quantity), 0) FROM dbo.cart_items WHERE user_id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
