package vn.iotstar.dao;

import vn.iotstar.models.User_24110373;

public interface UserDAO_24110373 {
    User_24110373 findByEmail(String email);
    User_24110373 findById(int id);
    int insert(User_24110373 user_24110373);
    void updateLastLogin(int userId);
    void update(User_24110373 user_24110373);
    boolean existsByEmail(String email);
}
