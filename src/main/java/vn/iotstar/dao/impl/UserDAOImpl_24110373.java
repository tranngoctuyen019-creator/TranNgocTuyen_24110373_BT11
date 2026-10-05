package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import vn.iotstar.config.DBConnection_24110373;
import vn.iotstar.dao.UserDAO_24110373;
import vn.iotstar.models.User_24110373;

public class UserDAOImpl_24110373 implements UserDAO_24110373 {

	private User_24110373 mapRow(ResultSet rs) throws SQLException {
        User_24110373 u = new User_24110373();
        u.setId(rs.getInt("id"));
        u.setEmail(rs.getString("email"));
        u.setFullname(rs.getString("fullname"));
        int phone = rs.getInt("phone");
        u.setPhone(rs.wasNull() ? null : phone);
        u.setPasswd(rs.getString("passwd"));
        Timestamp signup = rs.getTimestamp("signup_date");
        if (signup != null) u.setSignupDate(signup.toLocalDateTime());
        Timestamp lastLogin = rs.getTimestamp("last_login");
        if (lastLogin != null) u.setLastLogin(lastLogin.toLocalDateTime());
        u.setAdmin(rs.getBoolean("is_admin"));
        return u;
    }

    @Override
    public User_24110373 findByEmail(String email) {
        String sql = "SELECT * FROM dbo.users WHERE email = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User_24110373 findById(int id) {
        String sql = "SELECT * FROM dbo.users WHERE id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int insert(User_24110373 user_24110373) {
        String sql = "INSERT INTO dbo.users (email, fullname, phone, passwd, signup_date, is_admin) " +
                "VALUES (?, ?, ?, ?, GETDATE(), ?)";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user_24110373.getEmail());
            ps.setString(2, user_24110373.getFullname());
            if (user_24110373.getPhone() != null) ps.setInt(3, user_24110373.getPhone()); else ps.setNull(3, java.sql.Types.INTEGER);
            ps.setString(4, user_24110373.getPasswd());
            ps.setBoolean(5, user_24110373.isAdmin());
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
    public void updateLastLogin(int userId) {
        String sql = "UPDATE dbo.users SET last_login = GETDATE() WHERE id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User_24110373 user_24110373) {
        String sql = "UPDATE dbo.users SET fullname = ?, phone = ? WHERE id = ?";
        try (Connection conn = new DBConnection_24110373().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user_24110373.getFullname());
            if (user_24110373.getPhone() != null) ps.setInt(2, user_24110373.getPhone()); else ps.setNull(2, java.sql.Types.INTEGER);
            ps.setInt(3, user_24110373.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }
}