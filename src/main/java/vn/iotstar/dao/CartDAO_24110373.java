package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.CartItem_24110373;

public interface CartDAO_24110373 {

    List<CartItem_24110373> findByUser(int userId);

    int findQuantity(int userId, int bookid);

    void insert(int userId, int bookid, int quantity);

    void updateQuantity(int userId, int bookid, int quantity);

    void delete(int userId, int bookid);

    void clear(int userId);

    int countItems(int userId);
}
