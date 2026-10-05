package vn.iotstar.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order_24110373 {

    public static final String PAYMENT_COD = "COD";
    public static final String STATUS_PENDING = "PENDING";

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int orderId;
    private int userId;
    private String receiverName;
    private String phone;
    private String address;
    private String note;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItem_24110373> items = new ArrayList<>();

    public Order_24110373() {
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<OrderItem_24110373> getItems() { return items; }
    public void setItems(List<OrderItem_24110373> items) { this.items = items; }

    public String getCreatedAtText() {
        return createdAt == null ? "" : createdAt.format(FMT);
    }

    public String getPaymentMethodText() {
        if (PAYMENT_COD.equals(paymentMethod)) return "Thanh toán khi nhận hàng (COD)";
        return paymentMethod;
    }

    public String getStatusText() {
        if (STATUS_PENDING.equals(status)) return "Đã đặt hàng - chờ giao";
        return status;
    }

    public int getTotalQuantity() {
        int n = 0;
        for (OrderItem_24110373 i : items) n += i.getQuantity();
        return n;
    }
}
