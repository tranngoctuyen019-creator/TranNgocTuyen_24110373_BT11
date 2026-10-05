package vn.iotstar.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.CartItem_24110373;
import vn.iotstar.models.User_24110373;
import vn.iotstar.service.CartService_24110373;
import vn.iotstar.service.impl.CartServiceImpl_24110373;
import vn.iotstar.utils.BusinessException_24110373;
import vn.iotstar.utils.RequestUtil_24110373;

@WebServlet(urlPatterns = { "/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/remove-selected", "/cart/clear" })
public class CartController_24110373 extends HttpServlet {

    private final CartService_24110373 cartService = new CartServiceImpl_24110373();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!"/cart".equals(req.getServletPath())) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        User_24110373 user = currentUser(req);
        if (user == null) {
            redirectToLogin(req, resp, "/cart");
            return;
        }
        if (user.isAdmin()) {
            denyAdmin(req, resp);
            return;
        }

        List<CartItem_24110373> items = cartService.getCart(user.getId());
        BigDecimal total = cartService.getTotal(items);

        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", total);
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();
        String returnUrl = safeReturnUrl(req.getParameter("returnUrl"));

        User_24110373 user = currentUser(req);
        if (user == null) {
            req.getSession().setAttribute("flashError", "Vui lòng đăng nhập để sử dụng giỏ hàng.");
            redirectToLogin(req, resp, returnUrl);
            return;
        }
        if (user.isAdmin()) {
            denyAdmin(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        try {
            switch (path) {
            case "/cart/add": {
                int bookid = parseInt(req.getParameter("bookid"), "Sách không hợp lệ.");
                int quantity = parseInt(req.getParameter("quantity"), "Số lượng không hợp lệ.");
                cartService.addToCart(user.getId(), bookid, quantity);
                session.setAttribute("flashSuccess", "Đã thêm vào giỏ hàng.");
                break;
            }
            case "/cart/update": {
                int bookid = parseInt(req.getParameter("bookid"), "Sách không hợp lệ.");
                int quantity = parseInt(req.getParameter("quantity"), "Số lượng không hợp lệ.");
                cartService.updateQuantity(user.getId(), bookid, quantity);
                session.setAttribute("flashSuccess", "Đã cập nhật số lượng.");
                break;
            }
            case "/cart/remove": {
                int bookid = parseInt(req.getParameter("bookid"), "Sách không hợp lệ.");
                cartService.remove(user.getId(), bookid);
                session.setAttribute("flashSuccess", "Đã xóa sách khỏi giỏ hàng.");
                break;
            }
            case "/cart/remove-selected": {
                cartService.removeSelected(user.getId(),
                        RequestUtil_24110373.parseIds(req.getParameterValues("ids")));
                session.setAttribute("flashSuccess", "Đã xóa các sản phẩm đã chọn.");
                break;
            }
            case "/cart/clear": {
                cartService.clear(user.getId());
                session.setAttribute("flashSuccess", "Đã xóa tất cả sản phẩm trong giỏ hàng.");
                break;
            }
            default:
                break;
            }
        } catch (BusinessException_24110373 e) {
            session.setAttribute("flashError", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + returnUrl);
    }

    private User_24110373 currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (User_24110373) session.getAttribute("user");
    }

    private void denyAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("flashError", "Tài khoản quản trị không có giỏ hàng.");
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    private int parseInt(String value, String errorMessage) {
        try {
            return Integer.parseInt(value == null ? "" : value.trim());
        } catch (NumberFormatException e) {
            throw new BusinessException_24110373(errorMessage);
        }
    }

    private String safeReturnUrl(String url) {
        if (url == null || url.length() < 2 || url.charAt(0) != '/'
                || url.charAt(1) == '/' || url.charAt(1) == '\\' || url.contains("\r") || url.contains("\n")) {
            return "/cart";
        }
        return url;
    }

    private void redirectToLogin(HttpServletRequest req, HttpServletResponse resp, String returnUrl)
            throws IOException {
        String target = req.getContextPath() + returnUrl;
        resp.sendRedirect(req.getContextPath() + "/login?redirect="
                + URLEncoder.encode(target, StandardCharsets.UTF_8));
    }
}
