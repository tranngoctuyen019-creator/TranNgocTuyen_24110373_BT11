package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.OrderDAO_24110373;
import vn.iotstar.dao.impl.OrderDAOImpl_24110373;
import vn.iotstar.models.Order_24110373;
import vn.iotstar.service.OrderService_24110373;
import vn.iotstar.utils.BusinessException_24110373;

public class OrderServiceImpl_24110373 implements OrderService_24110373 {

    private final OrderDAO_24110373 orderDAO = new OrderDAOImpl_24110373();

    @Override
    public int checkoutCOD(int userId, List<Integer> bookIds, String receiverName, String phone, String address, String note) {
        String name = receiverName == null ? "" : receiverName.trim();
        String ph = phone == null ? "" : phone.replaceAll("[\\s.\\-]", "");
        String addr = address == null ? "" : address.trim();
        String nt = note == null ? "" : note.trim();

        if (name.isEmpty()) {
            throw new BusinessException_24110373("Vui lòng nhập họ tên người nhận.");
        }
        if (name.length() > 100) {
            throw new BusinessException_24110373("Họ tên người nhận tối đa 100 ký tự.");
        }
        if (!ph.matches("^(0|\\+84)\\d{9}$")) {
            throw new BusinessException_24110373("Số điện thoại không hợp lệ (ví dụ: 0901234567).");
        }
        if (addr.length() < 5) {
            throw new BusinessException_24110373("Vui lòng nhập địa chỉ giao hàng đầy đủ.");
        }
        if (addr.length() > 255) {
            throw new BusinessException_24110373("Địa chỉ giao hàng tối đa 255 ký tự.");
        }
        if (nt.length() > 500) {
            throw new BusinessException_24110373("Ghi chú tối đa 500 ký tự.");
        }

        return orderDAO.createFromCart(userId, bookIds, name, ph, addr, nt.isEmpty() ? null : nt);
    }

    @Override
    public List<Order_24110373> getOrders(int userId) {
        return orderDAO.findByUser(userId);
    }

    @Override
    public Order_24110373 getOrder(int orderId, int userId) {
        return orderDAO.findByIdAndUser(orderId, userId);
    }
}
