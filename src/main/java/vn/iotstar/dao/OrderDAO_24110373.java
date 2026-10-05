package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.Order_24110373;

public interface OrderDAO_24110373 {

    int createFromCart(int userId, List<Integer> bookIds, String receiverName, String phone, String address, String note);

    List<Order_24110373> findByUser(int userId);

    Order_24110373 findByIdAndUser(int orderId, int userId);
}
