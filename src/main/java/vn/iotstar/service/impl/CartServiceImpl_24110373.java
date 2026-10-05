package vn.iotstar.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.dao.BookDAO_24110373;
import vn.iotstar.dao.CartDAO_24110373;
import vn.iotstar.dao.impl.BookDAOImpl_24110373;
import vn.iotstar.dao.impl.CartDAOImpl_24110373;
import vn.iotstar.models.Book_24110373;
import vn.iotstar.models.CartItem_24110373;
import vn.iotstar.service.CartService_24110373;
import vn.iotstar.utils.BusinessException_24110373;

public class CartServiceImpl_24110373 implements CartService_24110373 {

    private final CartDAO_24110373 cartDAO = new CartDAOImpl_24110373();
    private final BookDAO_24110373 bookDAO = new BookDAOImpl_24110373();

    @Override
    public List<CartItem_24110373> getCart(int userId) {
        return cartDAO.findByUser(userId);
    }

    @Override
    public List<CartItem_24110373> getSelected(int userId, List<Integer> bookIds) {
        List<CartItem_24110373> result = new ArrayList<>();
        if (bookIds == null || bookIds.isEmpty()) return result;
        for (CartItem_24110373 i : cartDAO.findByUser(userId)) {
            if (bookIds.contains(i.getBookid())) result.add(i);
        }
        return result;
    }

    @Override
    public void removeSelected(int userId, List<Integer> bookIds) {
        if (bookIds == null || bookIds.isEmpty()) {
            throw new BusinessException_24110373("Bạn chưa chọn sản phẩm nào.");
        }
        for (Integer id : bookIds) cartDAO.delete(userId, id);
    }

    @Override
    public BigDecimal getTotal(List<CartItem_24110373> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem_24110373 i : items) total = total.add(i.getSubtotal());
        return total;
    }

    @Override
    public boolean hasStockProblem(List<CartItem_24110373> items) {
        for (CartItem_24110373 i : items) {
            if (i.isOverStock()) return true;
        }
        return false;
    }

    @Override
    public void addToCart(int userId, int bookid, int quantity) {
        if (quantity < 1) {
            throw new BusinessException_24110373("Số lượng phải lớn hơn 0.");
        }
        Book_24110373 book = bookDAO.findById(bookid);
        if (book == null) {
            throw new BusinessException_24110373("Sách không tồn tại hoặc đã bị xóa.");
        }
        if (book.getPrice() == null) {
            throw new BusinessException_24110373("Sách này chưa có giá bán nên chưa thể thêm vào giỏ.");
        }
        int stock = book.getQuantity() == null ? 0 : book.getQuantity();
        if (stock <= 0) {
            throw new BusinessException_24110373("Sách \"" + book.getTitle() + "\" đã hết hàng.");
        }

        int inCart = cartDAO.findQuantity(userId, bookid);
        long wanted = (long) inCart + quantity;
        if (wanted > stock) {
            throw new BusinessException_24110373("Không thể thêm: tồn kho chỉ còn " + stock + " cuốn \""
                    + book.getTitle() + "\" (trong giỏ của bạn đã có " + inCart + ").");
        }

        if (inCart == 0) {
            cartDAO.insert(userId, bookid, quantity);
        } else {
            cartDAO.updateQuantity(userId, bookid, (int) wanted);
        }
    }

    @Override
    public void updateQuantity(int userId, int bookid, int quantity) {
        if (quantity < 1) {
            throw new BusinessException_24110373("Số lượng tối thiểu là 1. Muốn bỏ sách khỏi giỏ hãy bấm \"Xóa\".");
        }
        if (cartDAO.findQuantity(userId, bookid) == 0) {
            throw new BusinessException_24110373("Sách này không còn trong giỏ hàng của bạn.");
        }
        Book_24110373 book = bookDAO.findById(bookid);
        if (book == null) {
            cartDAO.delete(userId, bookid);
            throw new BusinessException_24110373("Sách không tồn tại hoặc đã bị xóa, đã gỡ khỏi giỏ hàng.");
        }
        int stock = book.getQuantity() == null ? 0 : book.getQuantity();
        if (quantity > stock) {
            throw new BusinessException_24110373("Số lượng vượt quá tồn kho: \"" + book.getTitle()
                    + "\" chỉ còn " + stock + " cuốn.");
        }
        cartDAO.updateQuantity(userId, bookid, quantity);
    }

    @Override
    public void remove(int userId, int bookid) {
        cartDAO.delete(userId, bookid);
    }

    @Override
    public void clear(int userId) {
        cartDAO.clear(userId);
    }

    @Override
    public int countItems(int userId) {
        return cartDAO.countItems(userId);
    }
}
