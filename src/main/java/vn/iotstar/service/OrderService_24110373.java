package vn.iotstar.service;

import java.util.List;

import vn.iotstar.models.Order_24110373;

public interface OrderService_24110373 {

    int checkoutCOD(int userId, List<Integer> bookIds, String receiverName, String phone, String address, String note);

    List<Order_24110373> getOrders(int userId);

    Order_24110373 getOrder(int orderId, int userId);
}
