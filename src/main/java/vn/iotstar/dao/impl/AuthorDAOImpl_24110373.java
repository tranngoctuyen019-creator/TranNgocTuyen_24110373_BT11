package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.config.DBConnection_24110373;
import vn.iotstar.dao.AuthorDAO_24110373;
import vn.iotstar.models.Author_24110373;

public class AuthorDAOImpl_24110373 implements AuthorDAO_24110373 {

	private Author_24110373 mapRow(ResultSet rs) throws SQLException {
        Author_24110373 a = new Author_24110373();
        a.setAuthorId(rs.getInt("author_id"));
        a.setAuthorName(rs.getString("author_name"));
        java.sql.Date dob = rs.getDate("date_of_birth");
        if (dob != null) a.setDateOfBirth(dob.toLocalDate());
        return a;
    }

	@Override
	public List<Author_24110373> findAll() {
	    List<Author_24110373> list = new ArrayList<>();
	    String sql = "SELECT * FROM dbo.author ORDER BY author_id";
	    try (Connection conn = new DBConnection_24110373().getDBConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) list.add(mapRow(rs));
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	    return list;
	}

	@Override
	public Author_24110373 findById(int id) {
	    String sql = "SELECT * FROM dbo.author WHERE author_id = ?";
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
	public int insert(Author_24110373 author_24110373) {
	    String sql = "INSERT INTO dbo.author (author_name, date_of_birth) VALUES (?, ?)";
	    try (Connection conn = new DBConnection_24110373().getDBConnection();
	         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        ps.setString(1, author_24110373.getAuthorName());
	        if (author_24110373.getDateOfBirth() != null) ps.setDate(2, java.sql.Date.valueOf(author_24110373.getDateOfBirth()));
	        else ps.setNull(2, java.sql.Types.DATE);
	        ps.executeUpdate();
	        try (ResultSet keys = ps.getGeneratedKeys()) {
	            if (keys.next()) return keys.getInt(1);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	    return -1;
	}
}
