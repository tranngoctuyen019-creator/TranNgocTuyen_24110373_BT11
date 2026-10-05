package vn.iotstar.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.config.DBConnection_24110373;
import vn.iotstar.dao.OrderDAO_24110373;
import vn.iotstar.models.OrderItem_24110373;
import vn.iotstar.models.Order_24110373;
import vn.iotstar.utils.BusinessException_24110373;

public class OrderDAOImpl_24110373 implements OrderDAO_24110373 {

    private Order_24110373 mapOrder(ResultSet rs) throws SQLException {
        Order_24110373 o = new Order_24110373();
        o.setOrderId(rs.getInt("order_id"));
        o.setUserId(rs.getInt("user_id"));
        o.setReceiverName(rs.getString("receiver_name"));
        o.setPhone(rs.getString("phone"));
        o.setAddress(rs.getString("address"));
        o.setNote(rs.getString("note"));
        o.setTotalAmount(rs.getBigDecimal("total_amount"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setStatus(rs.getString("status"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) o.setCreatedAt(ts.toLocalDateTime());
        return o;
    }

    @Override
    public int createFromCart(int userId, List<Integer> bookIds, String receiverName, String phone, String address, String note) {

        if (bookIds == null || bookIds.isEmpty()) {
            throw new BusinessException_24110373("Bạn chưa chọn sản phẩm nào để thanh toán.");
        }
        String marks = placeholders(bookIds.size());
        String selectSql = "SELECT ci.book_id, ci.quantity, b.title, b.price " +
                "FROM dbo.cart_items ci JOIN dbo.books b ON b.bookid = ci.book_id " +
                "WHERE ci.user_id = ? AND ci.book_id IN (" + marks + ") ORDER BY ci.book_id";
        String insertOrderSql = "INSERT INTO dbo.orders " +
                "(user_id, receiver_name, phone, address, note, total_amount, payment_method, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String insertItemSql = "INSERT INTO dbo.order_items (order_id, book_id, title, unit_price, quantity) " +
                "VALUES (?, ?, ?, ?, ?)";

        String deductSql = "UPDATE dbo.books SET quantity = quantity - ? WHERE bookid = ? AND quantity >= ?";
        String stockSql = "SELECT quantity FROM dbo.books WHERE bookid = ?";
        String clearCartSql = "DELETE FROM dbo.cart_items WHERE user_id = ? AND book_id IN (" + marks + ")";

        Connection conn = null;
        try {
            conn = new DBConnection_24110373().getDBConnection();
            conn.setAutoCommit(false);

            List<OrderItem_24110373> items = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setInt(1, userId);
                for (int i = 0; i < bookIds.size(); i++) ps.setInt(i + 2, bookIds.get(i));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String title = rs.getString("title");
                        BigDecimal price = rs.getBigDecimal("price");
                        if (price == null) {
                            throw new BusinessException_24110373(
                                    "Sách \"" + title + "\" chưa có giá bán nên không thể đặt hàng.");
                        }
                        OrderItem_24110373 it = new OrderItem_24110373();
                        it.setBookid(rs.getInt("book_id"));
                        it.setQuantity(rs.getInt("quantity"));
                        it.setTitle(title);
                        it.setUnitPrice(price);
                        items.add(it);
                        total = total.add(it.getSubtotal());
                    }
                }
            }
            if (items.isEmpty()) {
                throw new BusinessException_24110373("Giỏ hàng của bạn đang trống.");
            }

            try (PreparedStatement deduct = conn.prepareStatement(deductSql)) {
                for (OrderItem_24110373 it : items) {
                    deduct.setInt(1, it.getQuantity());
                    deduct.setInt(2, it.getBookid());
                    deduct.setInt(3, it.getQuantity());
                    if (deduct.executeUpdate() == 0) {
                        int stock = 0;
                        try (PreparedStatement ps = conn.prepareStatement(stockSql)) {
                            ps.setInt(1, it.getBookid());
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) stock = rs.getInt(1);
                            }
                        }
                        throw new BusinessException_24110373("Sách \"" + it.getTitle() + "\" không đủ hàng (bạn đặt "
                                + it.getQuantity() + ", tồn kho còn " + stock + "). Vui lòng điều chỉnh giỏ hàng.");
                    }
                }
            }

            int orderId;
            try (PreparedStatement ps = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setString(2, receiverName);
                ps.setString(3, phone);
                ps.setString(4, address);
                ps.setString(5, note);
                ps.setBigDecimal(6, total);
                ps.setString(7, Order_24110373.PAYMENT_COD);
                ps.setString(8, Order_24110373.STATUS_PENDING);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) throw new SQLException("Không lấy được mã đơn hàng vừa tạo.");
                    orderId = keys.getInt(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(insertItemSql)) {
                for (OrderItem_24110373 it : items) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, it.getBookid());
                    ps.setString(3, it.getTitle());
                    ps.setBigDecimal(4, it.getUnitPrice());
                    ps.setInt(5, it.getQuantity());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            try (PreparedStatement ps = conn.prepareStatement(clearCartSql)) {
                ps.setInt(1, userId);
                for (int i = 0; i < bookIds.size(); i++) ps.setInt(i + 2, bookIds.get(i));
                ps.executeUpdate();
            }

            conn.commit();
            return orderId;

        } catch (BusinessException_24110373 e) {
            rollbackQuietly(conn);
            throw e;
        } catch (Exception e) {
            rollbackQuietly(conn);
            throw new RuntimeException(e);
        } finally {
            closeQuietly(conn);
        }
    }

    private String placeholders(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(",");
            sb.append("?");
        }
        return sb.toString();
    }

    private void rollbackQuietly(Connection conn) {
        if (conn == null) return;
        try {
            conn.rollback();
        } catch (SQLException ignored) {
        }
    }

    private void closeQuietly(Connection conn) {
        if (conn == null) return;
        try {
            conn.setAutoCommit(true);
        } catch (SQLException ignored) {
        }
        try {
            conn.close();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public List<Order_24110373> findByUser(int userId) {
        List<Order_24110373> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.orders WHERE user_id = ? ORDER BY order_id DESC";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapOrder(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Order_24110373 findByIdAndUser(int orderId, int userId) {
        String sql = "SELECT * FROM dbo.orders WHERE order_id = ? AND user_id = ?";
        String itemSql = "SELECT id, order_id, book_id, title, unit_price, quantity " +
                "FROM dbo.order_items WHERE order_id = ? ORDER BY id";
        try (Connection conn = new DBConnection_24110373().getDBConnection()) {
            Order_24110373 order = null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ps.setInt(2, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) order = mapOrder(rs);
                }
            }
            if (order == null) return null;

            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                ps.setInt(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<OrderItem_24110373> items = new ArrayList<>();
                    while (rs.next()) {
                        OrderItem_24110373 it = new OrderItem_24110373();
                        it.setId(rs.getInt("id"));
                        it.setOrderId(rs.getInt("order_id"));
                        int bid = rs.getInt("book_id");
                        it.setBookid(rs.wasNull() ? null : bid);
                        it.setTitle(rs.getString("title"));
                        it.setUnitPrice(rs.getBigDecimal("unit_price"));
                        it.setQuantity(rs.getInt("quantity"));
                        items.add(it);
                    }
                    order.setItems(items);
                }
            }
            return order;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
