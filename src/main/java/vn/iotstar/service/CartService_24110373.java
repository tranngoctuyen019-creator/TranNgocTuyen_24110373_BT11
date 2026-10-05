package vn.iotstar.service;

import java.math.BigDecimal;
import java.util.List;

import vn.iotstar.models.CartItem_24110373;

public interface CartService_24110373 {

    List<CartItem_24110373> getCart(int userId);

    List<CartItem_24110373> getSelected(int userId, List<Integer> bookIds);

    void removeSelected(int userId, List<Integer> bookIds);

    BigDecimal getTotal(List<CartItem_24110373> items);

    boolean hasStockProblem(List<CartItem_24110373> items);

    void addToCart(int userId, int bookid, int quantity);

    void updateQuantity(int userId, int bookid, int quantity);

    void remove(int userId, int bookid);

    void clear(int userId);

    int countItems(int userId);
}
